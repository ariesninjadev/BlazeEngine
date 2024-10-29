package com.ariesninja.BlazeEngine.gui;

import com.ariesninja.BlazeEngine.Camera;
import com.ariesninja.BlazeEngine.Client;
import com.ariesninja.BlazeEngine.Light;
import com.ariesninja.BlazeEngine.utils3d.Coordinate3D;
import com.ariesninja.BlazeEngine.utils3d.Computation;
import com.ariesninja.BlazeEngine.utils3d.PolygonWithDepth;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class GraphicalDisplay extends Frame implements KeyListener {

    private Image offscreenImage;
    private Graphics offscreenGraphics;
    private Client c;
    private int frameCount = 0;
    private String renderingOrderText = "";
    private long lastTime = System.currentTimeMillis();
    private int frames = 0;
    private int framerate = 0;

    public GraphicalDisplay(Client c, String t, int w, int h, Color col) {
        this.c = c;
        setTitle(t);
        setSize(w, h);
        setBackground(col);

        // Set the application icon
        try {
            Image icon = ImageIO.read(new File("src/main/resources/icon.png"));
            setIconImage(icon);
        } catch (IOException e) {
            System.err.println("Icon image not found: " + e.getMessage());
        }

        setLocationRelativeTo(null);  // Center the window

        // Make the window non-resizable
        setResizable(false);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        setVisible(true);

        Timer timer = new Timer(10, e -> {
            if (!c.isRunning()) return;
            ClientControl.controlCamera(this.c);  // Update camera movement
            repaint();  // Redraw the frame
        });
        timer.start();

        // Add KeyListener
        addKeyListener(this);
        setFocusable(true);
        requestFocusInWindow();
    }

    @Override
    public void update(Graphics g) {
        if (offscreenImage == null) {
            offscreenImage = createImage(getWidth(), getHeight());
            offscreenGraphics = offscreenImage.getGraphics();
        }

        offscreenGraphics.clearRect(0, 0, getWidth(), getHeight());
        paint(offscreenGraphics);
        g.drawImage(offscreenImage, 0, 0, this);
    }

    @Override
    public void paint(Graphics g) {
        frameCount++;
        frames++;
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastTime >= 1000) {
            framerate = frames;
            frames = 0;
            lastTime = currentTime;
        }

        Color[] colors = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.MAGENTA};

        // Draw faces
        List<PolygonWithDepth> faces = c.generateSurfaces();
        int cz = 0;
        for (PolygonWithDepth entry : faces) {
//            // Calculate final color based on lighting
//            for (Light light : c.getGlobalLights()) {
//                entry.setColor(Computation.calculateLighting(entry, light));
//            }
            g.setColor(entry.getColor());
            g.setColor(colors[cz++ % colors.length]);
            g.fillPolygon(entry.getPolygon());
        }

        // Update rendering order text every 30 frames
        if (frameCount % 30 == 0) {
            StringBuilder sb = new StringBuilder("Rendering Order:\n");
            for (PolygonWithDepth entry : faces) {
                sb.append(entry.getColor().toString()).append(" - ").append(entry.getDepth()).append("\n");
            }
            renderingOrderText = sb.toString();
        }

        // Always display rendering order text
        g.setColor(Color.WHITE);
        int yOffset = 40;
        for (String line : renderingOrderText.split("\n")) {
            g.drawString(line, 10, yOffset);
            yOffset += 20;
        }

        // Display framerate in the top right corner
        g.drawString("FPS: " + framerate, 10, getHeight()-20);

        // Get camera orientation
        Camera camera = c.getCamera();
        double[][] rotationMatrix = camera.getRotationMatrix();

        double crosshairSize = 0.5;

        // Define crosshair lines in 3D space
        Coordinate3D[] crosshairLines = {
                new Coordinate3D(0, 0, 0), new Coordinate3D(crosshairSize, 0, 0),  // X axis
                new Coordinate3D(0, 0, 0), new Coordinate3D(0, -crosshairSize, 0),  // Y axis
                new Coordinate3D(0, 0, 0), new Coordinate3D(0, 0, crosshairSize)   // Z axis
        };

        // Transform and project crosshair lines
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int size = (int) (Math.min(getWidth(), getHeight()) * 0.04);

        for (int i = 0; i < crosshairLines.length; i += 2) {
            Coordinate3D start = transformAndProject(crosshairLines[i], rotationMatrix, size, centerX, centerY);
            Coordinate3D end = transformAndProject(crosshairLines[i + 1], rotationMatrix, size, centerX, centerY);

            // Set color based on axis
            if (i == 0) g.setColor(Color.RED);       // X axis
            else if (i == 2) g.setColor(Color.GREEN); // Y axis
            else g.setColor(Color.BLUE);             // Z axis

            g.drawLine((int) start.x, (int) start.y, (int) end.x, (int) end.y);
        }
    }

    private Coordinate3D transformAndProject(Coordinate3D point, double[][] rotationMatrix, int size, int centerX, int centerY) {
        // Apply rotation
        double x = point.x * rotationMatrix[0][0] + point.y * rotationMatrix[0][1] + point.z * rotationMatrix[0][2];
        double y = point.x * rotationMatrix[1][0] + point.y * rotationMatrix[1][1] + point.z * rotationMatrix[1][2];
        double z = point.x * rotationMatrix[2][0] + point.y * rotationMatrix[2][1] + point.z * rotationMatrix[2][2];

        // Scale and translate to screen coordinates
        x = x * size + centerX;
        y = y * size + centerY;

        return new Coordinate3D(x, y, z);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        ClientControl.handleKeyPress(c, e, true);  // Set pressed to true on key press
    }

    @Override
    public void keyReleased(KeyEvent e) {
        ClientControl.handleKeyPress(c, e, false);  // Set pressed to false on key release
    }

}
