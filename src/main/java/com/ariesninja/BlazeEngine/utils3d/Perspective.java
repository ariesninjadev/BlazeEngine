package com.ariesninja.BlazeEngine.utils3d;

import com.ariesninja.BlazeEngine.Instance;
import com.ariesninja.BlazeEngine.Camera;
import com.ariesninja.BlazeEngine.Model;
import com.ariesninja.BlazeEngine.Pose3D;
import com.ariesninja.BlazeEngine.utils2d.Coordinate;
import com.ariesninja.BlazeEngine.utils2d.Line;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;

public class Perspective {

    private static Coordinate poly3Dto2D(Coordinate3D p, Camera c, int screenWidth, int screenHeight) {
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
        double aspectRatio = (double) screenWidth / (double) screenHeight;

        // Calculate the scale factor based on FOV and distance
        double scale = Math.tan(fov / 2) * zRotatedFinal;

        // Calculate the projected coordinates in normalized device coordinates (NDC)
        double projectedX = (xRotated / scale) / aspectRatio; // Apply aspect ratio
        double projectedY = (yRotated / scale); // Y remains unchanged

        // Map NDC to screen coordinates
        double screenX = (projectedX + 1) * ((double) screenWidth / 2); // Transform X from [-1, 1] to [0, screenWidth]
        double screenY = (1 - projectedY) * ((double) screenHeight / 2); // Transform Y from [-1, 1] to [0, screenHeight]

        return new Coordinate(screenX, screenY);
    }

    public static ArrayList<Polygon> filledSurfaces(Instance i, Camera c) {
        int sw = c.getScreenWidth();
        int sh = c.getScreenHeight();
        ArrayList<Polygon> polygons = new ArrayList<>();

        Pose3D pose = i.getPose();
        double px = pose.getPosition().getX();
        double py = pose.getPosition().getY();
        double pz = pose.getPosition().getZ();

        for (Surface3D surface : i.getModel().getSurfaces()) {
            Polygon polygon = new Polygon();
            for (Coordinate3D vertex : surface.getVertices()) {
                // Adjust vertex coordinates by the instance's pose
                Coordinate3D adjustedVertex = new Coordinate3D(vertex.x + px, vertex.y + py, vertex.z + pz);
                Coordinate projected = poly3Dto2D(adjustedVertex, c, sw, sh);
                polygon.addPoint((int) projected.x, (int) projected.y);
            }
            polygons.add(polygon);
        }
        return polygons;
    }

    public static ArrayList<Line> calculate(Instance i, Camera c) {

        int sw = c.getScreenWidth();
        int sh = c.getScreenHeight();

        ArrayList<Line> lines = new ArrayList<>();
        if (i.getModel().isDimensional()) {
            // Bottom left front corner of the rectangular prism
            double x1 = i.getPose().getPosition().getX();
            double y1 = i.getPose().getPosition().getY();
            double z1 = i.getPose().getPosition().getZ();

            // Get the dimensions of the rectangular prism
            double width = i.getModel().getWidth();
            double height = i.getModel().getHeight();
            double length = i.getModel().getLength();

            // Calculate the 8 vertices of the rectangular prism
            Coordinate3D[] vertices = new Coordinate3D[8];
            vertices[0] = new Coordinate3D(x1, y1, z1); // Front bottom left
            vertices[1] = new Coordinate3D(x1 + width, y1, z1); // Front bottom right
            vertices[2] = new Coordinate3D(x1 + width, y1 + height, z1); // Back bottom right
            vertices[3] = new Coordinate3D(x1, y1 + height, z1); // Back bottom left
            vertices[4] = new Coordinate3D(x1, y1, z1 + length); // Front top left
            vertices[5] = new Coordinate3D(x1 + width, y1, z1 + length); // Front top right
            vertices[6] = new Coordinate3D(x1 + width, y1 + height, z1 + length); // Back top right
            vertices[7] = new Coordinate3D(x1, y1 + height, z1 + length); // Back top left

            // Connect the vertices to form the rectangular prism
            lines.add(new Line(poly3Dto2D(vertices[0], c, sw, sh), poly3Dto2D(vertices[1], c, sw, sh)));
            lines.add(new Line(poly3Dto2D(vertices[1], c, sw, sh), poly3Dto2D(vertices[2], c, sw, sh)));
            lines.add(new Line(poly3Dto2D(vertices[2], c, sw, sh), poly3Dto2D(vertices[3], c, sw, sh)));
            lines.add(new Line(poly3Dto2D(vertices[3], c, sw, sh), poly3Dto2D(vertices[0], c, sw, sh)));

            lines.add(new Line(poly3Dto2D(vertices[4], c, sw, sh), poly3Dto2D(vertices[5], c, sw, sh)));
            lines.add(new Line(poly3Dto2D(vertices[5], c, sw, sh), poly3Dto2D(vertices[6], c, sw, sh)));
            lines.add(new Line(poly3Dto2D(vertices[6], c, sw, sh), poly3Dto2D(vertices[7], c, sw, sh)));
            lines.add(new Line(poly3Dto2D(vertices[7], c, sw, sh), poly3Dto2D(vertices[4], c, sw, sh)));

            lines.add(new Line(poly3Dto2D(vertices[0], c, sw, sh), poly3Dto2D(vertices[4], c, sw, sh)));
            lines.add(new Line(poly3Dto2D(vertices[1], c, sw, sh), poly3Dto2D(vertices[5], c, sw, sh)));
            lines.add(new Line(poly3Dto2D(vertices[2], c, sw, sh), poly3Dto2D(vertices[6], c, sw, sh)));
            lines.add(new Line(poly3Dto2D(vertices[3], c, sw, sh), poly3Dto2D(vertices[7], c, sw, sh)));
        } else {
            // Polygon Builder shape - arraylist of lines
            ArrayList<Line3D> shape = ((Model.POLYGON_BUILDER) i.getModel()).getLines();
            double px = i.getPose().getPosition().getX();
            double py = i.getPose().getPosition().getY();
            double pz = i.getPose().getPosition().getZ();
            for (Line3D l : shape) {
                Coordinate3D adjustedStart = new Coordinate3D(l.start.x + px, l.start.y + py, l.start.z + pz);
                Coordinate3D adjustedEnd = new Coordinate3D(l.end.x + px, l.end.y + py, l.end.z + pz);
                lines.add(new Line(poly3Dto2D(adjustedStart, c, sw, sh), poly3Dto2D(adjustedEnd, c, sw, sh)));
            }
        }

        return lines;
    }


    public static double[] calculateDepths(Instance i, Camera camera) {
        double[] depths = new double[i.getModel().getSurfaces().size()];
        Pose3D pose = i.getPose();
        double px = pose.getPosition().getX();
        double py = pose.getPosition().getY();
        double pz = pose.getPosition().getZ();

        double cameraX = camera.getPose().getPosition().getX();
        double cameraY = camera.getPose().getPosition().getY();
        double cameraZ = camera.getPose().getPosition().getZ();

        for (int j = 0; j < i.getModel().getSurfaces().size(); j++) {
            Surface3D surface = i.getModel().getSurfaces().get(j);
            double centroidX = 0;
            double centroidY = 0;
            double centroidZ = 0;
            int vertexCount = surface.getVertices().size();

            for (Coordinate3D vertex : surface.getVertices()) {
                // Adjust vertex coordinates by the instance's pose
                Coordinate3D adjustedVertex = new Coordinate3D(vertex.x + px, vertex.y + py, vertex.z + pz);
                centroidX += adjustedVertex.x;
                centroidY += adjustedVertex.y;
                centroidZ += adjustedVertex.z;
            }

            // Calculate the centroid
            centroidX /= vertexCount;
            centroidY /= vertexCount;
            centroidZ /= vertexCount;

            // Calculate the Euclidean distance from the camera to the centroid
            double distance = Math.sqrt(Math.pow(cameraX - centroidX, 2) +
                    Math.pow(cameraY - centroidY, 2) +
                    Math.pow(cameraZ - centroidZ, 2));

            depths[j] = distance;
        }
        return depths;
    }

    public static Line calculateLine(Line3D line, Camera c, int screenWidth, int screenHeight) {
        Pose3D pose = c.getPose();
        double px = pose.getPosition().getX();
        double py = pose.getPosition().getY();
        double pz = pose.getPosition().getZ();

        Coordinate3D adjustedStart = new Coordinate3D(line.start.x + px, line.start.y + py, line.start.z + pz);
        Coordinate3D adjustedEnd = new Coordinate3D(line.end.x + px, line.end.y + py, line.end.z + pz);

        return new Line(poly3Dto2D(adjustedStart, c, screenWidth, screenHeight), poly3Dto2D(adjustedEnd, c, screenWidth, screenHeight));
    }
}