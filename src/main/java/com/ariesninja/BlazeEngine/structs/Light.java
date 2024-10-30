package com.ariesninja.BlazeEngine.structs;

import com.ariesninja.BlazeEngine.utils3d.Coordinate3D;

import java.awt.*;

public class Light {

    Coordinate3D position;
    double intensity;
    Color color;

    double constant_attenuation = 1;
    double linear_attenuation = 0.09;
    double quadratic_attenuation = 0.03;
    double ambientFactor = -0.5;
    double scalingFactor = 0.6;

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

    public double getConstant_attenuation() {
        return constant_attenuation;
    }

    public double getLinear_attenuation() {
        return linear_attenuation;
    }

    public double getQuadratic_attenuation() {
        return quadratic_attenuation;
    }

    public double getAmbientFactor() {
        return ambientFactor;
    }

    public double getScalingFactor() {
        return scalingFactor;
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

    public void setConstant_attenuation(double constant_attenuation) {
        this.constant_attenuation = constant_attenuation;
    }

    public void setLinear_attenuation(double linear_attenuation) {
        this.linear_attenuation = linear_attenuation;
    }

    public void setQuadratic_attenuation(double quadratic_attenuation) {
        this.quadratic_attenuation = quadratic_attenuation;
    }

    public void setAmbientFactor(double ambientFactor) {
        this.ambientFactor = ambientFactor;
    }

    public void setScalingFactor(double scalingFactor) {
        this.scalingFactor = scalingFactor;
    }

    public void move(double x, double y, double z) {
        this.position.x += x;
        this.position.y += y;
        this.position.z += z;
    }

}
