package com.ariesninja.BlazeEngine;

import com.ariesninja.BlazeEngine.utils3d.Coordinate3D;

import java.awt.*;

public class Light {

    Coordinate3D position;
    double intensity;
    Color color;

    public Light(Coordinate3D position, double intensity, Color color) {
        this.position = position;
        this.intensity = intensity;
        this.color = color;
    }

    public Coordinate3D getPosition() {
        return position;
    }

    public double getIntensity() {
        return intensity;
    }

    public Color getColor() {
        return color;
    }

    public void setPosition(Coordinate3D position) {
        this.position = position;
    }

    public void setIntensity(double intensity) {
        this.intensity = intensity;
    }

    public void setColor(Color color) {
        this.color = color;
    }

}
