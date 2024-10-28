package com.ariesninja.BlazeEngine.utils3d;

public class Line3D {

    public Coordinate3D start;
    public Coordinate3D end;

    public Line3D(Coordinate3D start, Coordinate3D end) {
        this.start = start;
        this.end = end;
    }

    public Line3D() {
        this.start = new Coordinate3D();
        this.end = new Coordinate3D();
    }

    public void set(Coordinate3D start, Coordinate3D end) {
        this.start = start;
        this.end = end;
    }

    public void set(Line3D l) {
        this.start = l.start;
        this.end = l.end;
    }

    public void add(Coordinate3D start, Coordinate3D end) {
        this.start.add(start);
        this.end.add(end);
    }

    public void add(Line3D l) {
        this.start.add(l.start);
        this.end.add(l.end);
    }

    public void sub(Coordinate3D start, Coordinate3D end) {
        this.start.sub(start);
        this.end.sub(end);
    }

    public void sub(Line3D l) {
        this.start.sub(l.start);
        this.end.sub(l.end);
    }

    public void mult(Coordinate3D start, Coordinate3D end) {
        this.start.mult(start);
        this.end.mult(end);
    }

    public void mult(Line3D l) {
        this.start.mult(l.start);
        this.end.mult(l.end);
    }

    public void div(Coordinate3D start, Coordinate3D end) {
        this.start.div(start);
        this.end.div(end);
    }

    public void div(Line3D l) {
        this.start.div(l.start);
        this.end.div(l.end);
    }

    public double getLength() {
        return Math.sqrt(Math.pow(end.x - start.x, 2) + Math.pow(end.y - start.y, 2) + Math.pow(end.z - start.z, 2));
    }

    public double getLengthSquared() {
        return Math.pow(end.x - start.x, 2) + Math.pow(end.y - start.y, 2) + Math.pow(end.z - start.z, 2);
    }

    public double getAngle(Line3D l) {
        return Math.acos((start.x * end.x + start.y * end.y + start.z) / (Math.sqrt(start.x * start.x + start.y * start.y + start.z * start.z) * Math.sqrt(end.x * end.x + end.y * end.y + end.z * end.z)));
    }

    public void rotate(double angle) {
        double x = end.x - start.x;
        double y = end.y - start.y;
        double z = end.z - start.z;
        end.x = x * Math.cos(angle) - y * Math.sin(angle);
        end.y = x * Math.sin(angle) + y * Math.cos(angle);
        end.z = z;
    }

}
