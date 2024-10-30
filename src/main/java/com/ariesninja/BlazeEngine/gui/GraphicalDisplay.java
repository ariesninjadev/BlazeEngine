package com.ariesninja.BlazeEngine.gui;

import com.ariesninja.BlazeEngine.Camera;
import com.ariesninja.BlazeEngine.Client;
import com.ariesninja.BlazeEngine.structs.Light;
import com.ariesninja.BlazeEngine.utils3d.Computation;
import com.ariesninja.BlazeEngine.utils3d.Coordinate3D;
import com.ariesninja.BlazeEngine.utils3d.EnhancedPolygon;

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

    private static final String VERSION = "DEV_TEST";

    private static final boolean DISPLAY_DEBUG = false;
    private static final boolean DISPLAY_TITLE = true;

    private Image offscreenImage;
    private Graphics offscreenGraphics;
    private Client c;
    private int frameCount = 0;
    private String renderingOrderText = "";
    private long lastTime = System.currentTimeMillis();
    private int frames = 0;
    private int framerate = 0;
    private static final int UPDATE_INTERVAL = 100; // Update every 100 milliseconds
    private static final int AVERAGE_INTERVAL = 1000; // Average over 1 second
    private long[] frameTimes = new long[AVERAGE_INTERVAL / UPDATE_INTERVAL];
    private int frameTimeIndex = 0;

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

        Timer timer = new Timer(16, e -> {
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

    private void rightAlign(Graphics g, String text, int line) {
        FontMetrics fm = g.getFontMetrics();
        int w = fm.stringWidth(text);
        g.drawString(text, getWidth() - w - 20, (18*line)+50);
    }

    @Override
    public void paint(Graphics g) {

        // Framerate Calculation
        frameCount++;
        frames++;
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastTime >= UPDATE_INTERVAL) {
            frameTimes[frameTimeIndex] = frames * (1000 / UPDATE_INTERVAL);
            frameTimeIndex = (frameTimeIndex + 1) % frameTimes.length;
            frames = 0;
            lastTime = currentTime;

            // Calculate average FPS
            int totalFrames = 0;
            for (long frameTime : frameTimes) {
                totalFrames += frameTime;
            }
            framerate = totalFrames / frameTimes.length;
        }
        g.setFont(new Font("Monospaced", Font.PLAIN, 9));

        //////////////////////////////////

        // Draw faces
        List<EnhancedPolygon> faces = c.generateSurfaces();
        for (EnhancedPolygon polygon : faces) {
            Color baseColor = polygon.getColor();
            int r = 0, gr = 0, b = 0;

            for (Light light : c.getGlobalLights()) {
                Color lightColor = Computation.calculateLighting(polygon, light, c.getWorld());
                r += lightColor.getRed();
                gr += lightColor.getGreen();
                b += lightColor.getBlue();
            }

            // Clamp the color values to be within the valid range
            r = Math.min(255, r);
            gr = Math.min(255, gr);
            b = Math.min(255, b);

            polygon.setColor(new Color(r, gr, b));
            g.setColor(polygon.getColor());
            g.fillPolygon(polygon.getPolygon());
        }
        //////////////////////////////////

        // Update rendering order text every 30 frames
        if (frameCount % 30 == 0) {
            StringBuilder sb = new StringBuilder("Rendering Order:\n");
            for (EnhancedPolygon polygon : faces) {
                sb.append(polygon.getColor().toString()).append(" - ").append(polygon.getDepth()).append("\n");
            }
            renderingOrderText = sb.toString();
        }

        if (DISPLAY_DEBUG) {


            // Always display rendering order text
            g.setColor(Color.WHITE);
            int yOffset = 30;
            for (String line : renderingOrderText.split("\n")) {
                g.drawString(line, 10, yOffset);
                yOffset += 9;
            }

            g.setFont(new Font("Monospaced", Font.PLAIN, 14));

            // Right-align framerate in the top right corner
            String framerateText = "FPS: " + framerate;
            FontMetrics fm = g.getFontMetrics();
            int framerateWidth = fm.stringWidth(framerateText);
            g.drawString(framerateText, getWidth() - framerateWidth - 20, getHeight() - 20);

            // Display ALL DEBUG INFO.
            g.setColor(new Color(241, 124, 124));
            rightAlign(g, "Blaze Engine", 0);
            g.setColor(Color.WHITE);
            rightAlign(g, "Created by Aries Powvalla", 1);
            rightAlign(g, "Version " + VERSION, 2);
            rightAlign(g, "Commit e9687ba7ff0", 3);
            rightAlign(g, "2024 Edition", 4);
            rightAlign(g, "Window: " + getWidth() + "x" + getHeight(), 5);

            rightAlign(g, "CPU Name: " + System.getenv("PROCESSOR_IDENTIFIER"), 7);
            rightAlign(g, "CPU Cores: " + Runtime.getRuntime().availableProcessors(), 8);
            rightAlign(g, "GPU Name: " + System.getenv("DISPLAY"), 9);
            rightAlign(g, "GPU Vendor: " + System.getenv("VENDOR"), 10);

            rightAlign(g, "Camera: " + c.getCamera().getPose().toString(), 12);
            rightAlign(g, "Nearest object: " + c.getNearestObjectPosition(), 13);

            rightAlign(g, "Global Lights: " + c.getGlobalLights().size(), 15);
            rightAlign(g, "Models: " + c.getWorld().getModels().size(), 16);
            rightAlign(g, "Instance Polygons: " + faces.size(), 17);

            g.setFont(new Font("Monospaced", Font.BOLD, 14));
            g.setColor(Color.GREEN);

            rightAlign(g, "Current Memory Usage: " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 / 1024 + "KB", 19);

            g.setFont(new Font("Monospaced", Font.PLAIN, 14));
            g.setColor(Color.WHITE);

        } else if (DISPLAY_TITLE) {

            g.setColor(new Color(241, 124, 124));
            rightAlign(g, "Blaze Engine", 0);
            g.setColor(Color.WHITE);
            rightAlign(g, "Created by Aries Powvalla", 1);
            rightAlign(g, "Version " + VERSION, 2);

            rightAlign(g, "Window: " + getWidth() + "x" + getHeight(), 4);
            rightAlign(g, "FPS: " + framerate, 5);
            g.setColor(Color.GREEN);
            rightAlign(g, "Current Memory Usage: " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 / 1024 + "KB", 6);
            g.setColor(Color.WHITE);
            rightAlign(g, "Camera Pose: " + c.getCamera().getPose().toString(), 8);

        }

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
