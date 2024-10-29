package com.ariesninja.BlazeEngine.utils3d;

import java.awt.*;
import java.util.ArrayList;

public class PolygonWithDepth {
    private Polygon polygon;
    private double[] depths;

    public PolygonWithDepth(Polygon polygon, double[] depths) {
        this.polygon = polygon;
        this.depths = depths;
    }

    public Polygon getPolygon() {
        return polygon;
    }

    public double[] getDepths() {
        return depths;
    }
}