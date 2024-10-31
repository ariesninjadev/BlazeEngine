package com.ariesninja.BlazeEngine.gui;

import com.ariesninja.BlazeEngine.structs.Camera;
import com.ariesninja.BlazeEngine.utils3d.Coordinate3D;

import java.awt.*;

public class Crosshair {
    public void drawCrosshair(Graphics g, Camera camera, int width, int height) {
        double[][] rotationMatrix = camera.getRotationMatrix();
        double crosshairSize = 0.5;

        // Define crosshair lines in 3D space
        Coordinate3D[] crosshairLines = {
                new Coordinate3D(0, 0, 0), new Coordinate3D(crosshairSize, 0, 0),  // X axis
                new Coordinate3D(0, 0, 0), new Coordinate3D(0, -crosshairSize, 0),  // Y axis
                new Coordinate3D(0, 0, 0), new Coordinate3D(0, 0, crosshairSize)   // Z axis
        };

        // Transform and project crosshair lines
        int centerX = width / 2;
        int centerY = height / 2;
        int size = (int) (Math.min(width, height) * 0.04);

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
}