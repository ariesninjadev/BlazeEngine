package com.ariesninja.BlazeEngine.utils3d;

import java.awt.*;
import java.util.ArrayList;

public class MeshWithDepth {
    private ArrayList<Polygon> polygons;
    private double[] depths;

    public MeshWithDepth(ArrayList<Polygon> polygons, double[] depths) {
        this.polygons = polygons;
        this.depths = depths;
    }

    public ArrayList<Polygon> getPolygons() {
        return polygons;
    }

    public double[] getDepths() {
        return depths;
    }
}