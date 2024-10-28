package com.ariesninja.BlazeEngine.utils3d;

import java.awt.*;
import java.util.List;

public class Surface3D {

    List<Coordinate3D> vertices;

    public Surface3D(List<Coordinate3D> vertices) {

        this.vertices = vertices;

    }

    public List<Coordinate3D> getVertices() {
        return vertices;
    }

    public Coordinate3D getNormal() {
        Coordinate3D v1 = new Coordinate3D(vertices.get(1).x - vertices.get(0).x, vertices.get(1).y - vertices.get(0).y, vertices.get(1).z - vertices.get(0).z);
        Coordinate3D v2 = new Coordinate3D(vertices.get(2).x - vertices.get(0).x, vertices.get(2).y - vertices.get(0).y, vertices.get(2).z - vertices.get(0).z);
        return new Coordinate3D(v1.y * v2.z - v1.z * v2.y, v1.z * v2.x - v1.x * v2.z, v1.x * v2.y - v1.y * v2.x);
    }

}