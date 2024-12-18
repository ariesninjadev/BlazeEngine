package com.ariesninja.BlazeEngine.utils3d;

public class Pose3D {

    double x;
    double y;
    double z;
    double r_x;
    double r_y;
    double r_z;

    public static class Position {
        public double x;
        public double y;
        public double z;

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
        public double r_x;
        public double r_y;
        public double r_z;

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
        r_x = clamp(r_x + dr_x);
        r_y = clamp(r_y + dr_y);
        r_z = clamp(r_z + dr_z);
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

    @Override
    public String toString() {
        // Round to 2 decimal places
        return "Pose3D{" +
                "x=" + Math.round(x * 100.0) / 100.0 +
                ", y=" + Math.round(y * 100.0) / 100.0 +
                ", z=" + Math.round(z * 100.0) / 100.0 +
                ", r_x=" + Math.round(r_x * 100.0) / 100.0 +
                ", r_y=" + Math.round(r_y * 100.0) / 100.0 +
                ", r_z=" + Math.round(r_z * 100.0) / 100.0 +
                '}';
    }

    private double clamp(double rotation) {
        rotation = ((rotation + 180) % 360 + 360) % 360 - 180;
        return rotation;
    }

}
