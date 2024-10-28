package com.ariesninja.BlazeEngine.gui;

import com.ariesninja.BlazeEngine.Client;
import com.ariesninja.BlazeEngine.utils2d.Line;
import com.ariesninja.BlazeEngine.utils3d.Coordinate3D;
import com.ariesninja.BlazeEngine.utils3d.Perspective;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

public class GraphicalDisplay extends Frame implements KeyListener {

    private Image offscreenImage;
    private Graphics offscreenGraphics;
    private Client c;

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

    @Override
    public void paint(Graphics g) {

        // Draw faces
        List<Entry<PolygonWithDepth, Color>> faces = c.generateSurfaces();
        for (Entry<PolygonWithDepth, Color>  entry : faces) {
            g.setColor(entry.getValue());
            for (Polygon polygon : entry.getKey().getPolygons()) {
                g.fillPolygon(polygon);
            }
        }

        // Draw wireframe
//        g.setColor(Color.WHITE);
//        ArrayList<Line> lines = c.generateWireframe();
//        for (Line l : lines) {
//            int x1 = (int) Math.round(l.start.x);
//            int y1 = (int) Math.round(l.start.y);
//            int x2 = (int) Math.round(l.end.x);
//            int y2 = (int) Math.round(l.end.y);
//            g.drawLine(x1, y1, x2, y2);
//        }

        double crosshairSize = 0.04;

        // Draw a crosshair with 3 lines that each represent a different axis (red, green, blue)
        g.setColor(Color.RED);
        // Define the 3D coordinates of the x-axis
        Coordinate3D xAxisStart = new Coordinate3D(0, 0, 0); // Origin
        Coordinate3D xAxisEnd = new Coordinate3D(crosshairSize, 0, 0);   // Point along the x-axis

// Project the 3D coordinates onto the 2D screen
        Point start2D = Perspective.project(xAxisStart, c.getCamera());
        Point end2D = Perspective.project(xAxisEnd, c.getCamera());

// Draw the line using the projected coordinates
        g.drawLine(start2D.x, start2D.y, end2D.x, end2D.y);

        g.setColor(Color.GREEN);

        Coordinate3D yAxisStart = new Coordinate3D(0, 0, 0); // Origin
        Coordinate3D yAxisEnd = new Coordinate3D(0, crosshairSize, 0);   // Point along the y-axis

        Point start2Dy = Perspective.project(yAxisStart, c.getCamera());
        Point end2Dy = Perspective.project(yAxisEnd, c.getCamera());

        g.drawLine(start2Dy.x, start2Dy.y, end2Dy.x, end2Dy.y);

        g.setColor(Color.BLUE);

        Coordinate3D zAxisStart = new Coordinate3D(0, 0, 0); // Origin
        Coordinate3D zAxisEnd = new Coordinate3D(0, 0, crosshairSize);   // Point along the z-axis

        Point start2Dz = Perspective.project(zAxisStart, c.getCamera());
        Point end2Dz = Perspective.project(zAxisEnd, c.getCamera());

        g.drawLine(start2Dz.x, start2Dz.y, end2Dz.x, end2Dz.y);


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