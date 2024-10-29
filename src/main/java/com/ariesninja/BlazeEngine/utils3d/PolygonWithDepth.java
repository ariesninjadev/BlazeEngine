package com.ariesninja.BlazeEngine.utils3d;

import java.awt.*;
import java.util.ArrayList;

public class PolygonWithDepth {
    private Polygon polygon;
    private double depth;

    public PolygonWithDepth(Polygon polygon, double depth) {
        this.polygon = polygon;
        this.depth = depth;
    }

    public Polygon getPolygon() {
        return polygon;
    }

    public double getDepth() {
        return depth;
    }
}
