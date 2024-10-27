package com.ariesninja.BlazeEngine;

public class Pose3D {

    double x;
    double y;
    double z;
    double r_x;
    double r_y;
    double r_z;

    public static class Position {
        double x;
        double y;
        double z;

        public Position(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public double getZ() {
            return z;
        }
    }

    public static class Rotation {
        double r_x;
        double r_y;
        double r_z;

        public Rotation(double r_x, double r_y, double r_z) {
            this.r_x = r_x;
            this.r_y = r_y;
            this.r_z = r_z;
        }

        public double getRX() {
            return r_x;
        }

        public double getRY() {
            return r_y;
        }

        public double getRZ() {
            return r_z;
        }
    }

    public Pose3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.r_x = 0;
        this.r_y = 0;
        this.r_z = 0;
    }

    public Pose3D(double x, double y, double z, double r_x, double r_y, double r_z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.r_x = r_x;
        this.r_y = r_y;
        this.r_z = r_z;
    }

    public Pose3D() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.r_x = 0;
        this.r_y = 0;
        this.r_z = 0;
    }

    public void setPosition(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void setRotation(double r_x, double r_y, double r_z) {
        this.r_x = r_x;
        this.r_y = r_y;
        this.r_z = r_z;
    }

    public Position getPosition() {
        return new Position(x, y, z);
    }

    public Rotation getRotation() {
        return new Rotation(r_x, r_y, r_z);
    }

    public void move(double dx, double dy, double dz) {
        x += dx;
        y += dy;
        z += dz;
    }

    public void rotate(double dr_x, double dr_y, double dr_z) {
        r_x += dr_x;
        r_y += dr_y;
        r_z += dr_z;
    }

    public void transform(double dx, double dy, double dz, double dr_x, double dr_y, double dr_z) {
        move(dx, dy, dz);
        rotate(dr_x, dr_y, dr_z);
    }

    public void transform(Pose3D p) {
        move(p.x, p.y, p.z);
        rotate(p.r_x, p.r_y, p.r_z);
    }

    public void transform(Position p) {
        move(p.x, p.y, p.z);
    }

    public void transform(Rotation r) {
        rotate(r.r_x, r.r_y, r.r_z);
    }

}
