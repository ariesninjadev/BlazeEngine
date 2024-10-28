package com.ariesninja.BlazeEngine.utils3d;

public class Coordinate3D {

    public double x;
    public double y;
    public double z;

    public Coordinate3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Coordinate3D() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public void set(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void set(Coordinate3D c) {
        this.x = c.x;
        this.y = c.y;
        this.z = c.z;
    }

    public void add(double x, double y, double z) {
        this.x += x;
        this.y += y;
        this.z += z;
    }

    public void add(Coordinate3D c) {
        this.x += c.x;
        this.y += c.y;
        this.z += c.z;
    }

    public void sub(double x, double y, double z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
    }

    public void sub(Coordinate3D c) {
        this.x -= c.x;
        this.y -= c.y;
        this.z -= c.z;
    }

    public void mult(double x, double y, double z) {
        this.x *= x;
        this.y *= y;
        this.z *= z;
    }

    public void mult(Coordinate3D c) {
        this.x *= c.x;
        this.y *= c.y;
        this.z *= c.z;
    }

    public void div(double x, double y, double z) {
        this.x /= x;
        this.y /= y;
        this.z /= z;
    }

    public void div(Coordinate3D c) {
        this.x /= c.x;
        this.y /= c.y;
        this.z /= c.z;
    }

    public double dist(double x, double y, double z) {
        return Math.sqrt(Math.pow(this.x - x, 2) + Math.pow(this.y - y, 2) + Math.pow(this.z - z, 2));
    }

    public double dist(Coordinate3D c) {
        return Math.sqrt(Math.pow(this.x - c.x, 2) + Math.pow(this.y - c.y, 2) + Math.pow(this.z - c.z, 2));
    }

    public double length() {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
    }

    public void normalize() {
        double l = length();
        x /= l;
        y /= l;
        z /= l;
    }

    public void rotateX(double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        double ny = y * cos - z * sin;
        double nz = y * sin + z * cos;
        y = ny;
        z = nz;
    }

    public void rotateY(double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        double nx = x * cos + z * sin;
        double nz = -x * sin + z * cos;
        x = nx;
        z = nz;
    }

    public void rotateZ(double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        double nx = x * cos - y * sin;
        double ny = x * sin + y * cos;
        x = nx;
        y = ny;
    }

    public void rotate(double angleX, double angleY, double angleZ) {
        rotateX(angleX);
        rotateY(angleY);
        rotateZ(angleZ);
    }

    public void transform(double dx, double dy, double dz, double angleX, double angleY, double angleZ) {
        add(dx, dy, dz);
        rotate(angleX, angleY, angleZ);
    }

    public void transform(Coordinate3D c) {
        set(c);
    }

    public Coordinate3D copy() {
        return new Coordinate3D(x, y, z);
    }

    public Coordinate3D subtract(Coordinate3D center) {
        return new Coordinate3D(x - center.x, y - center.y, z - center.z);
    }
}
