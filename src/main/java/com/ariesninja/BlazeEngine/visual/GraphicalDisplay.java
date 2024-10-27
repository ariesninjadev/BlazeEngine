package com.ariesninja.BlazeEngine.visual;

import com.ariesninja.BlazeEngine.Client;
import com.ariesninja.BlazeEngine.utils2d.Line;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class GraphicalDisplay extends Frame implements KeyListener {

    private Image offscreenImage;
    private Graphics offscreenGraphics;
    private Client c;

    public GraphicalDisplay(Client c, String t, int w, int h, Color col) {
        this.c = c;
        setTitle(t);
        setSize(w, h);
        setBackground(col);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        setVisible(true);

        Timer timer = new Timer(100, e -> repaint());
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
        g.setColor(Color.WHITE);
        ArrayList<Line> lines = c.generateFrame();
        for (Line l : lines) {
            int x1 = (int) Math.round(l.start.x);
            int y1 = (int) Math.round(l.start.y);
            int x2 = (int) Math.round(l.end.x);
            int y2 = (int) Math.round(l.end.y);
            g.drawLine(x1, y1, x2, y2);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        ClientControl.handleKeyPress(c, e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}