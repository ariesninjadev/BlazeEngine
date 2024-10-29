package com.ariesninja.BlazeEngine;

import com.ariesninja.BlazeEngine.utils2d.Line;
import com.ariesninja.BlazeEngine.gui.GraphicalDisplay;
import com.ariesninja.BlazeEngine.utils3d.Perspective;
import com.ariesninja.BlazeEngine.utils3d.PolygonWithDepth;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;

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

    public List<Entry<PolygonWithDepth, Color>> generateSurfaces() {
        HashMap<PolygonWithDepth, Color> polygons = new HashMap<>();
        for (Instance i : world.getModels()) {
            ArrayList<Polygon> instancePolygons = Perspective.filledSurfaces(i, camera);
            double[] depths = Perspective.calculateDepths(i, camera);
            PolygonWithDepth polygonWithDepth = new PolygonWithDepth(instancePolygons, depths);
            polygons.put(polygonWithDepth, i.getColor());
        }

        // Create a list from the map entries
        List<Entry<PolygonWithDepth, Color>> polygonList = new ArrayList<>(polygons.entrySet());

        // Sort the list based on the depth of each polygon
        Collections.sort(polygonList, new Comparator<Entry<PolygonWithDepth, Color>>() {
            @Override
            public int compare(Entry<PolygonWithDepth, Color> o1, Entry<PolygonWithDepth, Color> o2) {
                double[] depths1 = o1.getKey().getDepths();
                double[] depths2 = o2.getKey().getDepths();
                for (int i = 0; i < depths1.length && i < depths2.length; i++) {
                    int comparison = Double.compare(depths2[i], depths1[i]); // Sort in descending order
                    if (comparison != 0) {
                        return comparison;
                    }
                }
                return 0;
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
