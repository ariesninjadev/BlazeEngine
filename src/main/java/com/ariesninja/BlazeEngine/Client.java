package com.ariesninja.BlazeEngine;

import com.ariesninja.BlazeEngine.utils2d.Coordinate;
import com.ariesninja.BlazeEngine.utils2d.Line;
import com.ariesninja.BlazeEngine.visual.ClientControl;
import com.ariesninja.BlazeEngine.visual.GraphicalDisplay;
import com.ariesninja.BlazeEngine.utils3d.Perspective;

import java.awt.*;
import java.util.ArrayList;

public class Client {

    private GraphicalDisplay w;

    private World world;
    private Camera camera;

    public Client() {
        w = new GraphicalDisplay(this, "Graphical", 500, 400, Color.BLACK);

        ClientControl.controlCamera(this);
    }

    public void load(World wr) {
        this.world = wr;
        camera = new Camera(wr, new Pose3D(0,0,0), 90, 1.25, 0.1, 1000);
    }

    public ArrayList<Line> generateFrame() {
        ArrayList<Line> lines = new ArrayList<>();
        for (Instance i : world.getModels()) {
            ArrayList<Line> instanceLines = Perspective.calculate(i, camera);
            lines.addAll(instanceLines);
        }
        return lines;
    }

    public Camera getCamera() {
        return camera;
    }

    //public render
}
