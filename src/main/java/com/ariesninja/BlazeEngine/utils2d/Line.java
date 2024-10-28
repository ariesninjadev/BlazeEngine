package com.ariesninja.BlazeEngine.utils2d;

public class Line {

    public Coordinate start;
    public Coordinate end;

    public Line(Coordinate start, Coordinate end) {
        this.start = start;
        this.end = end;
    }

    public Line() {
        this.start = new Coordinate();
        this.end = new Coordinate();
    }

    public void set(Coordinate start, Coordinate end) {
        this.start = start;
        this.end = end;
    }

    public void set(Line l) {
        this.start = l.start;
        this.end = l.end;
    }

    public void add(Coordinate start, Coordinate end) {
        this.start.add(start);
        this.end.add(end);
    }

    public void add(Line l) {
        this.start.add(l.start);
        this.end.add(l.end);
    }

    public void sub(Coordinate start, Coordinate end) {
        this.start.sub(start);
        this.end.sub(end);
    }

    public void sub(Line l) {
        this.start.sub(l.start);
        this.end.sub(l.end);
    }

    public void mult(Coordinate start, Coordinate end) {
        this.start.mult(start);
        this.end.mult(end);
    }

    public void mult(Line l) {
        this.start.mult(l.start);
        this.end.mult(l.end);
    }

    public void div(Coordinate start, Coordinate end) {
        this.start.div(start);
        this.end.div(end);
    }

    public void div(Line l) {
        this.start.div(l.start);
        this.end.div(l.end);
    }

    public double length() {
        return Math.sqrt(Math.pow(end.x - start.x, 2) + Math.pow(end.y - start.y, 2));
    }

    public double slope() {
        return (end.y - start.y) / (end.x - start.x);
    }

    public double angle() {
        return Math.atan(slope());
    }

    public double angle(Line l) {
        return Math.atan((l.end.y - l.start.y) / (l.end.x - l.start.x));
    }

    public double distance(Coordinate c) {
        return Math.abs((end.y - start.y) * c.x - (end.x - start.x) * c.y + end.x * start.y - end.y * start.x) / length();
    }

    public double distance(Line l) {
        return Math.abs((end.y - start.y) * l.start.x - (end.x - start.x) * l.start.y + end.x * start.y - end.y * start.x) / length();
    }

    public boolean isParallel(Line l) {
        return slope() == l.slope();
    }

    public boolean isPerpendicular(Line l) {
        return slope() == -1 / l.slope();
    }

    public boolean isIntersecting(Line l) {
        return !isParallel(l);
    }

    public Coordinate intersection(Line l) {
        double x = ((start.x * end.y - start.y * end.x) * (l.start.x - l.end.x) - (start.x - end.x) * (l.start.x * l.end.y - l.start.y * l.end.x)) / ((start.x - end.x) * (l.start.y - l.end.y) - (start.y - end.y) * (l.start.x - l.end.x));
        double y = ((start.x * end.y - start.y * end.x) * (l.start.y - l.end.y) - (start.y - end.y) * (l.start.x * l.end.y - l.start.y * l.end.x)) / ((start.x - end.x) * (l.start.y - l.end.y) - (start.y - end.y) * (l.start.x - l.end.x));
        return new Coordinate(x, y);
    }

    public boolean isPointOnLine(Coordinate c) {
        return distance(c) == 0;
    }

    public boolean isPointOnLine(Coordinate c, double tolerance) {
        return distance(c) < tolerance;
    }

    public boolean isPointOnLine(Line l) {
        return distance(l) == 0;
    }

}
