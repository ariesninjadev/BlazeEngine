package com.ariesninja.BlazeEngine;

import com.ariesninja.BlazeEngine.structs.Light;
import com.ariesninja.BlazeEngine.structs.Model;
import com.ariesninja.BlazeEngine.utils3d.Pose3D;

import java.awt.*;
import java.util.ArrayList;

public class World {

    ArrayList<Instance> models;
    ArrayList<Light> lights;

    public World() {

        models = new ArrayList<>();
        lights = new ArrayList<>();

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

    public ArrayList<Light> getLights() {
        return lights;
    }

    public void setLights(ArrayList<Light> lights) {
        this.lights = lights;
    }

    public void addLight(Light l) {
        lights.add(l);
    }

    public void removeLight(Light l) {
        lights.remove(l);
    }

    public void reset() {

        models = new ArrayList<>();
        lights = new ArrayList<>();

    }

}
