package com.ariesninja.BlazeEngine.utils2d;

public class Coordinate {

    public double x;
    public double y;

    public Coordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Coordinate() {
        this.x = 0;
        this.y = 0;
    }

    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void set(Coordinate c) {
        this.x = c.x;
        this.y = c.y;
    }

    public void add(double x, double y) {
        this.x += x;
        this.y += y;
    }

    public void add(Coordinate c) {
        this.x += c.x;
        this.y += c.y;
    }

    public void sub(double x, double y) {
        this.x -= x;
        this.y -= y;
    }

    public void sub(Coordinate c) {
        this.x -= c.x;
        this.y -= c.y;
    }

    public void mult(double x, double y) {
        this.x *= x;
        this.y *= y;
    }

    public void mult(Coordinate c) {
        this.x *= c.x;
        this.y *= c.y;
    }

    public void div(double x, double y) {
        this.x /= x;
        this.y /= y;
    }

    public void div(Coordinate c) {
        this.x /= c.x;
        this.y /= c.y;
    }

    public double dist(double x, double y) {
        return Math.sqrt(Math.pow(this.x - x, 2) + Math.pow(this.y - y, 2));
    }

    public double dist(Coordinate c) {
        return Math.sqrt(Math.pow(this.x - c.x, 2) + Math.pow(this.y - c.y, 2));
    }

    public double distSq(double x, double y) {
        return Math.pow(this.x - x, 2) + Math.pow(this.y - y, 2);
    }

    public double distSq(Coordinate c) {
        return Math.pow(this.x - c.x, 2) + Math.pow(this.y - c.y, 2);
    }

    public double mag() {
        return Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
    }

    public void normalize() {
        double mag = mag();
        this.x /= mag;
        this.y /= mag;
    }

    public void limit(double max) {
        if (mag() > max) {
            normalize();
            mult(max, max);
        }
    }

}
