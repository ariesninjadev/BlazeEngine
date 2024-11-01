package com.ariesninja.BlazeEngine.math;

import com.ariesninja.BlazeEngine.structs.*;
import com.ariesninja.BlazeEngine.utils2d.Coordinate;
import com.ariesninja.BlazeEngine.utils2d.Line;
import com.ariesninja.BlazeEngine.utils3d.*;

import java.awt.*;
import java.util.ArrayList;

public class Computation {

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

    public static ArrayList<EnhancedPolygon> filledSurfaces(Instance i, Camera c) {
        int sw = c.getScreenWidth();
        int sh = c.getScreenHeight();
        ArrayList<Polygon> polygons = new ArrayList<>();
        ArrayList<EnhancedPolygon> result = new ArrayList<>();

        Pose3D pose = i.getPose();
        double px = pose.getPosition().getX();
        double py = pose.getPosition().getY();
        double pz = pose.getPosition().getZ();

        for (Surface3D surface : i.getModel().getSurfaces()) {
            Polygon polygon = new Polygon();
            double centroidX = 0;
            double centroidY = 0;
            double centroidZ = 0;
            int vertexCount = surface.getVertices().size();
            ArrayList<Coordinate3D> vertices3D = new ArrayList<>();

            for (Coordinate3D vertex : surface.getVertices()) {
                // Adjust vertex coordinates by the instance's pose
                Coordinate3D adjustedVertex = new Coordinate3D(vertex.x + px, vertex.y + py, vertex.z + pz);
                vertices3D.add(adjustedVertex);
                Coordinate projected = poly3Dto2D(adjustedVertex, c, sw, sh);
                polygon.addPoint((int) projected.x, (int) projected.y);

                // Sum up the coordinates for centroid calculation
                centroidX += adjustedVertex.x;
                centroidY += adjustedVertex.y;
                centroidZ += adjustedVertex.z;
            }

            // Calculate the centroid
            centroidX /= vertexCount;
            centroidY /= vertexCount;
            centroidZ /= vertexCount;

            //polygons.add(polygon);
            result.add(new EnhancedPolygon(polygon, 0, new Coordinate3D(centroidX, centroidY, centroidZ), vertices3D, i.getColor()));
        }
        return result;
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

    public static double calculateVertexDepth(Instance instance, Camera camera, double x, double y, double z) {
        Pose3D pose = instance.getPose();
        double px = pose.getPosition().getX();
        double py = pose.getPosition().getY();
        double pz = pose.getPosition().getZ();

        double cameraX = camera.getPose().getPosition().getX();
        double cameraY = camera.getPose().getPosition().getY();
        double cameraZ = camera.getPose().getPosition().getZ();

        // Adjust vertex coordinates by the instance's pose
        double adjustedX = x;
        double adjustedY = y;
        double adjustedZ = z;

        // Calculate the Euclidean distance from the camera to the vertex
        double distance = Math.sqrt(Math.pow(adjustedX - cameraX, 2) +
                Math.pow(adjustedY - cameraY, 2) +
                Math.pow(adjustedZ - cameraZ, 2));

        return distance;
    }

    public static Color calculateLighting(EnhancedPolygon polygon, Light light, World world) {
        // Calculate the centroid of the polygon
        double centroidX = 0;
        double centroidY = 0;
        double centroidZ = 0;
        int vertexCount = polygon.getPolygon().npoints;

        for (int i = 0; i < vertexCount; i++) {
            centroidX += polygon.getPositionIn3dSpace().x;
            centroidY += polygon.getPositionIn3dSpace().y;
            centroidZ += polygon.getPositionIn3dSpace().z;
        }

        centroidX /= vertexCount;
        centroidY /= vertexCount;
        centroidZ /= vertexCount;

        // Calculate the distance from the light to the centroid
        double distance = Math.sqrt(
                Math.pow(light.getPosition().x - centroidX, 2) +
                        Math.pow(light.getPosition().y - centroidY, 2) +
                        Math.pow(light.getPosition().z - centroidZ, 2)
        );

        double attenuation = 1 / (1 + light.getConstant_attenuation() + light.getLinear_attenuation() * distance + light.getQuadratic_attenuation() * Math.pow(distance, 2));

        // Get the object's base color
        double r = polygon.getColor().getRed();
        double g = polygon.getColor().getGreen();
        double b = polygon.getColor().getBlue();

        // Ambient lighting factor (controls the base light level when not directly illuminated)
        double ambientFactor = light.getAmbientFactor(); // Adjust as necessary to achieve the desired darkness in shadowed areas

        // Calculate the light's contribution with attenuation and scale by intensity
        double rLight = light.getColor().getRed() * attenuation * light.getIntensity();
        double gLight = light.getColor().getGreen() * attenuation * light.getIntensity();
        double bLight = light.getColor().getBlue() * attenuation * light.getIntensity();

        // Combine the base color with the light's contribution, scaled to balance, with ambient lighting
        double scalingFactor = light.getScalingFactor();
        double rFinal = Math.max(0, Math.min(255, r * ambientFactor + r * (1 - scalingFactor) + (rLight * scalingFactor)));
        double gFinal = Math.max(0, Math.min(255, g * ambientFactor + g * (1 - scalingFactor) + (gLight * scalingFactor)));
        double bFinal = Math.max(0, Math.min(255, b * ambientFactor + b * (1 - scalingFactor) + (bLight * scalingFactor)));

        return new Color((int) rFinal, (int) gFinal, (int) bFinal);
    }



    public static Pose3D getNearestObjectPosition(World world, Camera camera) {
        ArrayList<Instance> instances = world.getModels();
        double minDistance = Double.MAX_VALUE;
        Pose3D nearestPose = null;

        for (Instance i : instances) {
            double[] depths = calculateDepths(i, camera);
            for (double depth : depths) {
                if (depth < minDistance) {
                    minDistance = depth;
                    nearestPose = i.getPose();
                }
            }
        }

        return nearestPose;
    }

    private static boolean isPointInFieldOfView(Coordinate3D point, Camera camera) {
        double horizFOV = camera.getFov();
        double aspectRatio = (double) camera.getScreenWidth() / (double) camera.getScreenHeight();
        double vertFOV = 2 * Math.atan(Math.tan(Math.toRadians(horizFOV) / 2) / aspectRatio);

        double cameraRX = camera.getPose().getRotation().getRX();
        double cameraRY = camera.getPose().getRotation().getRY();

        double cameraX = camera.getPose().getPosition().getX();
        double cameraY = camera.getPose().getPosition().getY();
        double cameraZ = camera.getPose().getPosition().getZ();

        double pointX = point.x;
        double pointY = point.y;
        double pointZ = point.z;

        double dx = pointX - cameraX;
        double dy = pointY - cameraY;
        double dz = pointZ - cameraZ;

        // Horizontal check
        double horizontalAngle = Math.atan2(dz, dx);
        double horizontalAngleDiff = Math.abs(cameraRY - horizontalAngle);
        if (horizontalAngleDiff > Math.PI) {
            horizontalAngleDiff = 2 * Math.PI - horizontalAngleDiff;
        }
        if (horizontalAngleDiff > Math.toRadians(horizFOV) / 2) {
            return false;
        }

        // Vertical check
        double distance = Math.sqrt(dx * dx + dz * dz);
        double verticalAngle = Math.atan2(dy, distance);
        double verticalAngleDiff = Math.abs(cameraRX - verticalAngle);
        if (verticalAngleDiff > Math.PI) {
            verticalAngleDiff = 2 * Math.PI - verticalAngleDiff;
        }
        if (verticalAngleDiff > Math.toRadians(vertFOV) / 2) {
            return false;
        }

        return true;
    }

    public static boolean isPolygonInFieldOfView(EnhancedPolygon polygon, Camera camera) {
        for (int i = 0; i < polygon.getVertices().size(); i++) {
            if (isPointInFieldOfView(polygon.getVertices().get(i), camera)) {
                return true;
            }
        }
        return true;
    }


}
