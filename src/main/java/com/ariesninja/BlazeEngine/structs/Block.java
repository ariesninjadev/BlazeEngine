package com.ariesninja.BlazeEngine.structs;

import com.ariesninja.BlazeEngine.utils2d.Coordinate;
import com.ariesninja.BlazeEngine.utils3d.Coordinate3D;
import com.ariesninja.BlazeEngine.utils3d.Line3D;
import com.ariesninja.BlazeEngine.utils3d.Surface3D;

import java.util.ArrayList;
import java.util.List;

public class Block {

    double poly_width;
    double poly_height;
    double poly_length;

    ArrayList<Line3D> lines;
    ArrayList<Coordinate3D> vertices;
    ArrayList<Coordinate> textureCoords; // Add texture coordinates

    boolean isDimensional;
    boolean isWireframe = true; // No filling yet

    public List<Surface3D> surfaces = new ArrayList<>();
    private Texture texture; // Add texture reference

    public Block() {

        double s = 1;

        poly_width = s;
        poly_height = s;
        poly_length = s;
        isDimensional = true;

        // Define surfaces for the CUBE model
        ArrayList<Coordinate3D> vertices = new ArrayList<>();
        vertices.add(new Coordinate3D(0, 0, 0));
        vertices.add(new Coordinate3D(0, 0, s));
        vertices.add(new Coordinate3D(0, s, 0));
        vertices.add(new Coordinate3D(0, s, s));
        vertices.add(new Coordinate3D(s, 0, 0));
        vertices.add(new Coordinate3D(s, 0, s));
        vertices.add(new Coordinate3D(s, s, 0));
        vertices.add(new Coordinate3D(s, s, s));

        this.vertices = vertices;

        // Define texture coordinates for the CUBE model
        ArrayList<Coordinate> textureCoords = new ArrayList<>();
        textureCoords.add(new Coordinate(0, 0));
        textureCoords.add(new Coordinate(0, 1));
        textureCoords.add(new Coordinate(1, 0));
        textureCoords.add(new Coordinate(1, 1));
        textureCoords.add(new Coordinate(0, 0));
        textureCoords.add(new Coordinate(0, 1));
        textureCoords.add(new Coordinate(1, 0));
        textureCoords.add(new Coordinate(1, 1));

        this.textureCoords = textureCoords;

        ArrayList<Coordinate3D> surface1 = new ArrayList<>();
        surface1.add(vertices.get(0));
        surface1.add(vertices.get(1));
        surface1.add(vertices.get(3));
        surface1.add(vertices.get(2));
        surfaces.add(new Surface3D(surface1));

        ArrayList<Coordinate3D> surface2 = new ArrayList<>();
        surface2.add(vertices.get(0));
        surface2.add(vertices.get(1));
        surface2.add(vertices.get(5));
        surface2.add(vertices.get(4));
        surfaces.add(new Surface3D(surface2));

        ArrayList<Coordinate3D> surface3 = new ArrayList<>();
        surface3.add(vertices.get(0));
        surface3.add(vertices.get(2));
        surface3.add(vertices.get(6));
        surface3.add(vertices.get(4));
        surfaces.add(new Surface3D(surface3));

        ArrayList<Coordinate3D> surface4 = new ArrayList<>();
        surface4.add(vertices.get(7));
        surface4.add(vertices.get(6));
        surface4.add(vertices.get(4));
        surface4.add(vertices.get(5));
        surfaces.add(new Surface3D(surface4));

        ArrayList<Coordinate3D> surface5 = new ArrayList<>();
        surface5.add(vertices.get(7));
        surface5.add(vertices.get(6));
        surface5.add(vertices.get(2));
        surface5.add(vertices.get(3));
        surfaces.add(new Surface3D(surface5));

        ArrayList<Coordinate3D> surface6 = new ArrayList<>();
        surface6.add(vertices.get(7));
        surface6.add(vertices.get(5));
        surface6.add(vertices.get(1));
        surface6.add(vertices.get(3));
        surfaces.add(new Surface3D(surface6));
    }

    public List<Coordinate> getTextureCoords() {
        return textureCoords;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public double getWidth() {
        return poly_width;
    }

    public double getHeight() {
        return poly_height;
    }

    public double getLength() {
        return poly_length;
    }

    public boolean isDimensional() {
        return isDimensional;
    }

    public List<Surface3D> getSurfaces() {
        return surfaces;
    }

    public ArrayList<Coordinate3D> getVertices() {
        return vertices;
    }
}