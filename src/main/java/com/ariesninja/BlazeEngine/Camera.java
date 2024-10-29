package com.ariesninja.BlazeEngine;

public class Camera {

    public Pose3D pose;
    public double fov;
    public double aspect;
    public double near;
    public double far;
    public World world;

    private int screenwidth;
    private int screenheight;

    public Camera(World w, Pose3D p, double fov, double aspect, double near, double far) {
        this.pose = p; // The camera's position and rotation
        this.fov = fov; // The field of view of the camera in degrees
        this.aspect = aspect; // The aspect ratio of the camera
        this.near = near; // The near clipping plane of the camera
        this.far = far; // The far clipping plane of the camera
        this.world = w; // The world that the camera is in
    }

    public void bind (int width, int height) {
        this.screenwidth = width;
        this.screenheight = height;
    }

    public int getScreenWidth() {
        return screenwidth;
    }

    public int getScreenHeight() {
        return screenheight;
    }

    public Pose3D getPose() {
        return pose;
    }

    public double getFov() {
        return fov;
    }

    public double getAspect() {
        return aspect;
    }

    public double getNear() {
        return near;
    }

    public double getFar() {
        return far;
    }

    public void setPose(Pose3D p) {
        this.pose = p;
    }

    public void setFov(double fov) {
        this.fov = fov;
    }

    public void setAspect(double aspect) {
        this.aspect = aspect;
    }

    public void setNear(double near) {
        this.near = near;
    }

    public void setFar(double far) {
        this.far = far;
    }

    public void setWorld(World w) {
        this.world = w;
    }

    public World getWorld() {
        return world;
    }

    public void move(double dx, double dy, double dz) {
        pose.move(dx, dy, dz);
    }

    public void rotate(double dr_x, double dr_y, double dr_z) {
        pose.rotate(dr_x, dr_y, dr_z);
    }

    public void transform(double dx, double dy, double dz, double dr_x, double dr_y, double dr_z) {
        pose.transform(dx, dy, dz, dr_x, dr_y, dr_z);
    }

    public void transform(Pose3D p) {
        pose.transform(p);
    }

    public double[][] getRotationMatrix() {
        double angleX = Math.toRadians(pose.getRotation().r_x);
        double angleY = -Math.toRadians(pose.getRotation().r_y);
        double angleZ = Math.toRadians(pose.getRotation().r_z);

        double cosX = Math.cos(angleX);
        double sinX = Math.sin(angleX);
        double cosY = Math.cos(angleY);
        double sinY = Math.sin(angleY);
        double cosZ = Math.cos(angleZ);
        double sinZ = Math.sin(angleZ);

        double[][] rotationMatrix = new double[3][3];

        rotationMatrix[0][0] = cosY * cosZ;
        rotationMatrix[0][1] = cosY * sinZ;
        rotationMatrix[0][2] = -sinY;

        rotationMatrix[1][0] = sinX * sinY * cosZ - cosX * sinZ;
        rotationMatrix[1][1] = sinX * sinY * sinZ + cosX * cosZ;
        rotationMatrix[1][2] = sinX * cosY;

        rotationMatrix[2][0] = cosX * sinY * cosZ + sinX * sinZ;
        rotationMatrix[2][1] = cosX * sinY * sinZ - sinX * cosZ;
        rotationMatrix[2][2] = cosX * cosY;

        return rotationMatrix;
    }

}
