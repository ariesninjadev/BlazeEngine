package com.ariesninja.BlazeEngine;

import com.ariesninja.BlazeEngine.utils2d.Line;
import com.ariesninja.BlazeEngine.gui.GraphicalDisplay;
import com.ariesninja.BlazeEngine.utils3d.Perspective;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;

public class Client {

    int width = 1920;
    int height = 1080;

    private GraphicalDisplay w;

    private World world;
    private Camera camera;

    private boolean isRunning;

    public Client() {
        w = new GraphicalDisplay(this, "Graphical", width, height, Color.BLACK);
    }

    public void load(World wr) {
        this.world = wr;
        camera = new Camera(wr, new Pose3D(0,0,0), 90, 1.25, 0.1, 1000);
        camera.bind(width, height);
        isRunning = true;
    }

    public ArrayList<Line> generateWireframe() {
        ArrayList<Line> lines = new ArrayList<>();
        for (Instance i : world.getModels()) {
            ArrayList<Line> instanceLines = Perspective.calculate(i, camera);
            lines.addAll(instanceLines);
        }
        return lines;
    }

    private double averageDepth(ArrayList<Polygon> polygons) {
        double totalDepth = 0;
        int count = 0;
        for (Polygon p : polygons) {
            for (int i = 0; i < p.npoints; i++) {
                totalDepth += p.ypoints[i]; // Assuming ypoints represents depth
                count++;
            }
        }
        return totalDepth / count;
    }

    public List<Entry<ArrayList<Polygon>, Color>> generateSurfaces() {
        HashMap<ArrayList<Polygon>, Color> polygons = new HashMap<>();
        for (Instance i : world.getModels()) {
            ArrayList<Polygon> instancePolygons = Perspective.filledSurfaces(i, camera);
            polygons.put(instancePolygons, i.getColor());
        }

        // Create a list from the map entries
        List<Entry<ArrayList<Polygon>, Color>> polygonList = new ArrayList<>(polygons.entrySet());

        // Sort the list based on the average depth of the polygons
        Collections.sort(polygonList, new Comparator<Entry<ArrayList<Polygon>, Color>>() {
            @Override
            public int compare(Entry<ArrayList<Polygon>, Color> o1, Entry<ArrayList<Polygon>, Color> o2) {
                double depth1 = averageDepth(o1.getKey());
                double depth2 = averageDepth(o2.getKey());
                return Double.compare(depth2, depth1); // Sort in descending order
            }
        });

        return polygonList;
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
}
