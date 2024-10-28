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

}
