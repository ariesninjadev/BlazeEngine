package com.ariesninja.BlazeEngine.gui;

import com.ariesninja.BlazeEngine.Client;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

public class Window extends Frame implements KeyListener {
    public Client c;

    public Window(Client c, String t, int w, int h, Color col) {
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

        Timer timer = new Timer(12, e -> {
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