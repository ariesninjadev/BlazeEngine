package com.ariesninja.BlazeEngine.utils3d;

import java.awt.*;
import java.util.ArrayList;

public class PolygonWithDepth {
    private Polygon polygon;
    private double depth;
    private Coordinate3D positionIn3dSpace;
    private Color color;

    public PolygonWithDepth(Polygon polygon, double depth, Coordinate3D positionIn3dSpace, Color color) {
        this.polygon = polygon;
        this.depth = depth;
        this.positionIn3dSpace = positionIn3dSpace;
        this.color = color;
    }

    public Polygon getPolygon() {
        return polygon;
    }

    public double getDepth() {
        return depth;
    }

    public Coordinate3D getPositionIn3dSpace() {
        return positionIn3dSpace;
    }

    public Color getColor() {
        return color;
    }

    public void setPolygon(Polygon polygon) {
        this.polygon = polygon;
    }

    public void setDepth(double depth) {
        this.depth = depth;
    }

    public void setPositionIn3dSpace(Coordinate3D positionIn3dSpace) {
        this.positionIn3dSpace = positionIn3dSpace;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
