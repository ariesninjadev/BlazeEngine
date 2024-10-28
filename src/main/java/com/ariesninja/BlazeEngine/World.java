package com.ariesninja.BlazeEngine;

import java.awt.*;
import java.util.ArrayList;

public class World {

    ArrayList<Instance> models;

    public World() {
        models = new ArrayList<>();
    }

    public void place(Instance i) {
        models.add(i);
    }

    public Instance place(Model m, Pose3D p) {
        Instance i = new Instance(m, p);
        models.add(i);
        return i;
    }

    public Instance place(Model m, Pose3D p, Color c) {
        Instance i = new Instance(m, p, c);
        models.add(i);
        return i;
    }

    public void destroy(Instance i) {
        models.remove(i);
    }

    public ArrayList<Instance> getModels() {
        return models;
    }

    public void setModels(ArrayList<Instance> models) {
        this.models = models;
    }

    public void reset() {
        models = new ArrayList<>();
    }

}
