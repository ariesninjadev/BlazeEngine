package com.ariesninja.BlazeEngine;

import com.ariesninja.BlazeEngine.structs.Light;
import com.ariesninja.BlazeEngine.utils2d.Line;
import com.ariesninja.BlazeEngine.gui.GraphicalDisplay;
import com.ariesninja.BlazeEngine.utils3d.Computation;
import com.ariesninja.BlazeEngine.utils3d.Coordinate3D;
import com.ariesninja.BlazeEngine.utils3d.EnhancedPolygon;
import com.ariesninja.BlazeEngine.utils3d.Pose3D;

import java.awt.*;
import java.util.ArrayList;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Client {

    int width;
    int height;

    private GraphicalDisplay w;

    private World world;
    private Camera camera;

    private boolean isRunning;

    public Client(int width, int height) {
        w = new GraphicalDisplay(this, "Graphical", width, height, Color.BLACK);
        this.width = width;
        this.height = height;
    }

    public void load(World wr) {
        this.world = wr;
        camera = new Camera(wr, new Pose3D(0,0,0), 90, 1.25, 0.1, 10);
        camera.bind(width, height);
        isRunning = true;
    }

    public ArrayList<Line> generateWireframe() {
        ArrayList<Line> lines = new ArrayList<>();
        for (Instance i : world.getModels()) {
            ArrayList<Line> instanceLines = Computation.calculate(i, camera);
            lines.addAll(instanceLines);
        }
        return lines;
    }

    public List<EnhancedPolygon> generateSurfaces() {
        ArrayList<EnhancedPolygon> polygons = new ArrayList<>();
        for (Instance i : world.getModels()) { // for each cube
            ArrayList<EnhancedPolygon> instancePolygons = Computation.filledSurfaces(i, camera); // generate list of polygons
            for (EnhancedPolygon polygon : instancePolygons) { // for each polygon
                double totalDepth = 0;
                int vertexCount = polygon.getVertices().size();
                for (Coordinate3D vertex : polygon.getVertices()) {
                    double vertexDepth = Computation.calculateVertexDepth(i, camera, vertex.x, vertex.y, vertex.z);
                    totalDepth += vertexDepth;
                }
                double averageDepth = totalDepth / vertexCount;
                polygon.setDepth(averageDepth);
                polygon.setColor(i.getColor());
                polygons.add(polygon);
            }
        }

        // Sort the list based on the depth of each polygon
        Collections.sort(polygons, new Comparator<EnhancedPolygon>() {
            @Override
            public int compare(EnhancedPolygon o1, EnhancedPolygon o2) {
                double depths1 = o1.getDepth();
                double depths2 = o2.getDepth();
                // Sort in descending order
                return Double.compare(depths2, depths1);
            }
        });

        return polygons;
    }

//    public ArrayList<Line> generateUnobstructedOutlines() {
//        ArrayList<Line> outlines = new ArrayList<>();
//        for (Instance i : world.getModels()) {
//            ArrayList<Line> instanceOutlines = Perspective.unobstructedOutlines(i, camera);
//            outlines.addAll(instanceOutlines);
//        }
//        return outlines;
//    }

    public Camera getCamera() {
        return camera;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public GraphicalDisplay getDisplay() {
        return w;
    }

    public ArrayList<Light> getGlobalLights() {
        return world.getLights();
    }

    public Pose3D getNearestObjectPosition() {
        return Computation.getNearestObjectPosition(world, camera);
    }

    public World getWorld() {
        return world;
    }
}
