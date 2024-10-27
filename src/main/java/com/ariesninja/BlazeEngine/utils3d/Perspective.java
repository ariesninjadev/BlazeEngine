package com.ariesninja.BlazeEngine.utils3d;

import com.ariesninja.BlazeEngine.Instance;
import com.ariesninja.BlazeEngine.Camera;
import com.ariesninja.BlazeEngine.Model;
import com.ariesninja.BlazeEngine.utils2d.Coordinate;
import com.ariesninja.BlazeEngine.utils2d.Line;

import java.util.ArrayList;

public class Perspective {

    private static Coordinate poly3Dto2D(Coordinate3D p, Camera c) {
        // Translate the point based on the camera position
        double translatedX = p.x - c.getPose().getPosition().getX();
        double translatedY = p.y - c.getPose().getPosition().getY();
        double translatedZ = p.z - c.getPose().getPosition().getZ();

        // Convert degrees to radians
        double yaw = Math.toRadians(c.getPose().getRotation().getRY());
        double pitch = Math.toRadians(c.getPose().getRotation().getRX());

        // Apply rotation based on the camera orientation
        double xRotated = Math.cos(yaw) * translatedX + Math.sin(yaw) * translatedZ;
        double zRotated = -Math.sin(yaw) * translatedX + Math.cos(yaw) * translatedZ;

        double yRotated = -Math.sin(pitch) * zRotated + Math.cos(pitch) * translatedY;
        double zRotatedFinal = Math.cos(pitch) * zRotated + Math.sin(pitch) * translatedY;

        // Ensure zRotatedFinal is not zero to avoid divide by zero
        if (zRotatedFinal <= 0) zRotatedFinal = 0.01; // small offset to avoid division by zero

        // Project the 3D point to 2D using perspective projection
        double fov = Math.toRadians(c.getFov());
        double aspectRatio = (double) 500 / (double) 400;

        // Calculate the scale factor based on FOV and distance
        double scale = Math.tan(fov / 2) * zRotatedFinal;

        // Calculate the projected coordinates in normalized device coordinates (NDC)
        double projectedX = (xRotated / scale) * aspectRatio; // Apply aspect ratio
        double projectedY = (yRotated / scale); // Y remains unchanged

        // Map NDC to screen coordinates
        double screenX = (projectedX + 1) * (500 / 2); // Transform X from [-1, 1] to [0, screenWidth]
        double screenY = (1 - projectedY) * (400 / 2); // Transform Y from [-1, 1] to [0, screenHeight]

        return new Coordinate(screenX, screenY);
    }


    public static ArrayList<Line> calculate(Instance i, Camera c) {
        ArrayList<Line> lines = new ArrayList<>();
        if (i.getModel().getClass() == Model.CUBE.class) {
            // Use the cube's vertices to calculate the lines

            // Center of the cube
            double x1 = i.getPose().getPosition().getX();
            double y1 = i.getPose().getPosition().getY();
            double z1 = i.getPose().getPosition().getZ();
            double size = ((Model.CUBE) i.getModel()).getSize();

            // Half the size to calculate offsets from the center
            double halfSize = size / 2.0;

            // Calculate the 8 vertices of the cube
            Coordinate3D[] vertices = new Coordinate3D[8];
            vertices[0] = new Coordinate3D(x1 - halfSize, y1 - halfSize, z1 - halfSize); // Front bottom left
            vertices[1] = new Coordinate3D(x1 + halfSize, y1 - halfSize, z1 - halfSize); // Front bottom right
            vertices[2] = new Coordinate3D(x1 + halfSize, y1 + halfSize, z1 - halfSize); // Back bottom right
            vertices[3] = new Coordinate3D(x1 - halfSize, y1 + halfSize, z1 - halfSize); // Back bottom left
            vertices[4] = new Coordinate3D(x1 - halfSize, y1 - halfSize, z1 + halfSize); // Front top left
            vertices[5] = new Coordinate3D(x1 + halfSize, y1 - halfSize, z1 + halfSize); // Front top right
            vertices[6] = new Coordinate3D(x1 + halfSize, y1 + halfSize, z1 + halfSize); // Back top right
            vertices[7] = new Coordinate3D(x1 - halfSize, y1 + halfSize, z1 + halfSize); // Back top left

            // Calculate the lines of the cube
            lines.add(new Line(poly3Dto2D(vertices[0], c), poly3Dto2D(vertices[1], c))); // Front bottom
            System.out.println(poly3Dto2D(vertices[0], c).x + " " + poly3Dto2D(vertices[0], c).y);
            lines.add(new Line(poly3Dto2D(vertices[1], c), poly3Dto2D(vertices[2], c)));
            lines.add(new Line(poly3Dto2D(vertices[2], c), poly3Dto2D(vertices[3], c)));
            lines.add(new Line(poly3Dto2D(vertices[3], c), poly3Dto2D(vertices[0], c)));

            lines.add(new Line(poly3Dto2D(vertices[4], c), poly3Dto2D(vertices[5], c))); // Back bottom
            lines.add(new Line(poly3Dto2D(vertices[5], c), poly3Dto2D(vertices[6], c)));
            lines.add(new Line(poly3Dto2D(vertices[6], c), poly3Dto2D(vertices[7], c)));
            lines.add(new Line(poly3Dto2D(vertices[7], c), poly3Dto2D(vertices[4], c)));

            lines.add(new Line(poly3Dto2D(vertices[0], c), poly3Dto2D(vertices[4], c))); // Front to back
            lines.add(new Line(poly3Dto2D(vertices[1], c), poly3Dto2D(vertices[5], c)));
            lines.add(new Line(poly3Dto2D(vertices[2], c), poly3Dto2D(vertices[6], c)));
            lines.add(new Line(poly3Dto2D(vertices[3], c), poly3Dto2D(vertices[7], c)));
        }
        return lines;
    }


}