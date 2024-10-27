package com.ariesninja.BlazeEngine;

public class Instance {

    Model model;
    Pose3D pose;

    public Instance(Model m, Pose3D p) {
        this.model = m;
        this.pose = p;
    }

    public Model getModel() {
        return model;
    }

    public Pose3D getPose() {
        return pose;
    }

    public void setPose(Pose3D p) {
        this.pose = p;
    }

    public void setModel(Model m) {
        this.model = m;
    }

}
