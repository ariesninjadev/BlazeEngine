package com.ariesninja.BlazeEngine.utils3d;

import com.ariesninja.BlazeEngine.utils2d.Coordinate;

import java.awt.*;
import java.util.ArrayList;

public class EnhancedPolygon {
    private Polygon polygon;
    private double depth;
    private Coordinate3D positionIn3dSpace;
    private ArrayList<Coordinate3D> vertices;
    private Color color;

    public EnhancedPolygon(Polygon polygon, double depth, Coordinate3D positionIn3dSpace, ArrayList<Coordinate3D> vertices, Color color) {
        this.polygon = polygon;
        this.depth = depth;
        this.positionIn3dSpace = positionIn3dSpace;
        this.vertices = vertices;
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

    public ArrayList<Coordinate3D> getVertices() {
        return vertices;
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

    public void setVertices(ArrayList<Coordinate3D> vertices) {
        this.vertices = vertices;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
