package com.ariesninja.BlazeEngine.structs;

import com.ariesninja.BlazeEngine.utils3d.Pose3D;

import java.awt.*;

public class Instance {

    Model model;
    Pose3D pose;
    Color color;

    public Instance(Model m, Pose3D p) {
        this.model = m;
        this.pose = p;
        this.color = Color.WHITE;
    }

    public Instance(Model m, Pose3D p, Color c) {
        this.model = m;
        this.pose = p;
        this.color = c;
    }

    public Model getModel() {
        return model;
    }

    public Pose3D getPose() {
        return pose;
    }

    public Color getColor() {
        return color;
    }

    public void setPose(Pose3D p) {
        this.pose = p;
    }

    public void setModel(Model m) {
        this.model = m;
    }

    public void setColor(Color c) {
        this.color = c;
    }

    public void move(double x, double y, double z) {
        this.pose.move(x, y, z);
    }

}
