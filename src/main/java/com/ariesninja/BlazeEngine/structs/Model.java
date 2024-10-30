package com.ariesninja.BlazeEngine.structs;

import com.ariesninja.BlazeEngine.utils3d.Coordinate3D;
import com.ariesninja.BlazeEngine.utils3d.Line3D;
import com.ariesninja.BlazeEngine.utils3d.Surface3D;

import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.util.ArrayList;
import java.util.List;

public class Model {

    double poly_width;
    double poly_height;
    double poly_length;

    ArrayList<Line3D> lines;

    ArrayList<Coordinate3D> vertices;

    boolean isDimensional;

    boolean isWireframe = true; // No filling yet

    public List<Surface3D> surfaces = new ArrayList<>();

    public RectangularShape getBoundingBox() {
        if (vertices == null || vertices.isEmpty()) {
            return new Rectangle2D.Double();
        }

        double minX = Double.MAX_VALUE;
        double minY = Double.MAX_VALUE;
        double minZ = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE;
        double maxY = Double.MIN_VALUE;
        double maxZ = Double.MIN_VALUE;

        for (Coordinate3D vertex : vertices) {
            if (vertex.x < minX) minX = vertex.x;
            if (vertex.y < minY) minY = vertex.y;
            if (vertex.z < minZ) minZ = vertex.z;
            if (vertex.x > maxX) maxX = vertex.x;
            if (vertex.y > maxY) maxY = vertex.y;
            if (vertex.z > maxZ) maxZ = vertex.z;
        }

        // Assuming the bounding box is a 2D projection on the XY plane
        return new Rectangle2D.Double(minX, minY, maxX - minX, maxY - minY);
    }

    public static class CUBE extends Model {

        public CUBE(double s) {

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

    }

    public static class RECTANGULAR_PRISM extends Model {

        public RECTANGULAR_PRISM(double w, double h, double l) {

            poly_width = w;
            poly_height = h;
            poly_length = l;
            isDimensional = true;

            // Define surfaces for the RECTANGULAR_PRISM model
            ArrayList<Coordinate3D> vertices = new ArrayList<>();
            vertices.add(new Coordinate3D(0, 0, 0));
            vertices.add(new Coordinate3D(0, 0, l));
            vertices.add(new Coordinate3D(0, h, 0));
            vertices.add(new Coordinate3D(0, h, l));
            vertices.add(new Coordinate3D(w, 0, 0));
            vertices.add(new Coordinate3D(w, 0, l));
            vertices.add(new Coordinate3D(w, h, 0));
            vertices.add(new Coordinate3D(w, h, l));

            this.vertices = vertices;

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
    }

    public static class POLYGON_BUILDER extends Model {

        public POLYGON_BUILDER(ArrayList<Line3D> lines) {
            this.lines = lines;
            isDimensional = false;
        }

        public ArrayList<Line3D> getLines() {
            return lines;
        }

        public void extrude(double x, double y, double z) {
            ArrayList<Line3D> originalLines = new ArrayList<>(lines);
            for (Line3D l : originalLines) {
                Coordinate3D start = l.start;
                Coordinate3D end = l.end;
                lines.add(new Line3D(new Coordinate3D(start.x + x, start.y + y, start.z + z), new Coordinate3D(end.x + x, end.y + y, end.z + z)));
                // Connecting lines
                lines.add(new Line3D(new Coordinate3D(start.x, start.y, start.z), new Coordinate3D(start.x + x, start.y + y, start.z + z)));
                lines.add(new Line3D(new Coordinate3D(end.x, end.y, end.z), new Coordinate3D(end.x + x, end.y + y, end.z + z)));
            }
            // Clone existing surfaces
            ArrayList<Surface3D> originalSurfaces = new ArrayList<>(surfaces);
            for (Surface3D s : originalSurfaces) {
                ArrayList<Coordinate3D> originalVertices = new ArrayList<>(s.getVertices());
                ArrayList<Coordinate3D> newVertices = new ArrayList<>();
                for (Coordinate3D v : originalVertices) {
                    newVertices.add(new Coordinate3D(v.x + x, v.y + y, v.z + z));
                }
                surfaces.add(new Surface3D(newVertices));
            }
            // Generate connecting surfaces between the original and extruded shape
            for (int i = 0; i < originalLines.size(); i++) {
                Coordinate3D start = originalLines.get(i).start;
                Coordinate3D end = originalLines.get(i).end;
                Coordinate3D startExtruded = new Coordinate3D(start.x + x, start.y + y, start.z + z);
                Coordinate3D endExtruded = new Coordinate3D(end.x + x, end.y + y, end.z + z);
                ArrayList<Coordinate3D> surface = new ArrayList<>();
                surface.add(start);
                surface.add(startExtruded);
                surface.add(endExtruded);
                surface.add(end);
                surfaces.add(new Surface3D(surface));
            }

        }

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
