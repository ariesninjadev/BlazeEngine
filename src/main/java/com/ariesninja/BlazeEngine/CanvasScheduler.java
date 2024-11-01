// src/main/java/com/ariesninja/BlazeEngine/gui/GraphicsLib.java
package com.ariesninja.BlazeEngine;

import com.ariesninja.BlazeEngine.gui.Crosshair;
import com.ariesninja.BlazeEngine.gui.Framerate;
import com.ariesninja.BlazeEngine.gui.Window;
import com.ariesninja.BlazeEngine.math.Computation;
import com.ariesninja.BlazeEngine.math.Lighting;
import com.ariesninja.BlazeEngine.utils3d.EnhancedPolygon;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CanvasScheduler extends Window {
    private static final String VERSION = "DEV_TEST";
    private static final boolean DISPLAY_DEBUG = false;
    private static final boolean DISPLAY_TITLE = true;

    private Image offscreenImage;
    private java.awt.Graphics offscreenGraphics;
    private int frameCount = 0;
    private String renderingOrderText = "";
    private Framerate framerateCalculator = new Framerate();
    private Crosshair crosshairDrawer = new Crosshair();
    private Lighting lightingCalculator = new Lighting();

    public CanvasScheduler(Client c, String t, int w, int h, Color col) {
        super(c, t, w, h, col);
    }

    @Override
    public void update(java.awt.Graphics g) {
        if (offscreenImage == null) {
            offscreenImage = createImage(getWidth(), getHeight());
            offscreenGraphics = offscreenImage.getGraphics();
        }

        offscreenGraphics.clearRect(0, 0, getWidth(), getHeight());
        paint(offscreenGraphics);
        g.drawImage(offscreenImage, 0, 0, this);
    }

    @Override
    public void paint(java.awt.Graphics g) {
        frameCount++;
        if (framerateCalculator == null || c.getWorld() == null) {
            return;
        }
        framerateCalculator.update();
        int framerate = framerateCalculator.getFramerate();
        g.setFont(new Font("Monospaced", Font.PLAIN, 9));

        // Draw faces
        List<EnhancedPolygon> allFaces = c.generateSurfaces();
        List<EnhancedPolygon> faces = new ArrayList<>();
        for (EnhancedPolygon polygon : allFaces) {
            if (Computation.isPolygonInFieldOfView(polygon, c.getCamera())) {
                faces.add(polygon);
            }
        }
        lightingCalculator.applyLighting(faces, c.getGlobalLights(), c.getWorld());
        for (EnhancedPolygon polygon : faces) {
            g.setColor(polygon.getColor());
            g.fillPolygon(polygon.getPolygon());
        }

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
            g.setFont(new Font("Monospaced", Font.PLAIN, 14));

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

        // Draw crosshair
        crosshairDrawer.drawCrosshair(g, c.getCamera(), getWidth(), getHeight());
    }

    private void rightAlign(java.awt.Graphics g, String text, int line) {
        FontMetrics fm = g.getFontMetrics();
        int w = fm.stringWidth(text);
        g.drawString(text, getWidth() - w - 20, (18 * line) + 50);
    }
}
