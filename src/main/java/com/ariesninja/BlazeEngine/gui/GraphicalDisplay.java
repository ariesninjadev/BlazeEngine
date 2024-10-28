package com.ariesninja.BlazeEngine.gui;

import com.ariesninja.BlazeEngine.Client;
import com.ariesninja.BlazeEngine.utils2d.Line;
import com.ariesninja.BlazeEngine.utils3d.Perspective;

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
        List<Entry<ArrayList<Polygon>, Color>> faces = c.generateSurfaces();
        for (Entry<ArrayList<Polygon>, Color> entry : faces) {
            g.setColor(entry.getValue());
            for (Polygon polygon : entry.getKey()) {
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