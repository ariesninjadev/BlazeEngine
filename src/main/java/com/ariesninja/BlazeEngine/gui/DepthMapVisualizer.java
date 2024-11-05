package com.ariesninja.BlazeEngine.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class DepthMapVisualizer extends JFrame {
    private BufferedImage depthMapImage;
    private JPanel panel;

    public DepthMapVisualizer(int width, int height) {
        setTitle("Depth Map Visualizer");
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        depthMapImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(depthMapImage, 0, 0, null);
            }
        };
        add(panel);
    }

    public void updateDepthMap(double[][] depthMap) {
        for (int x = 0; x < depthMap.length; x++) {
            for (int y = 0; y < depthMap[0].length; y++) {
                int colorValue = (int) (255 - (depthMap[x][y] / Double.MAX_VALUE) * 255);
                int rgb = new Color(colorValue, colorValue, colorValue).getRGB();
                depthMapImage.setRGB(x, y, rgb);
            }
        }
        panel.repaint();
    }
}