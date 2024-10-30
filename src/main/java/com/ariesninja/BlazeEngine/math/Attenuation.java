package com.ariesninja.BlazeEngine.math;

public class Attenuation {

    public double constant_attenuation = 1;
    public double linear_attenuation = 0.09;
    public double quadratic_attenuation = 0.03;

    public Attenuation() {
    }

    public Attenuation(double constant_attenuation, double linear_attenuation, double quadratic_attenuation) {
        this.constant_attenuation = constant_attenuation;
        this.linear_attenuation = linear_attenuation;
        this.quadratic_attenuation = quadratic_attenuation;
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

    public void setConstant_attenuation(double constant_attenuation) {
        this.constant_attenuation = constant_attenuation;
    }

    public void setLinear_attenuation(double linear_attenuation) {
        this.linear_attenuation = linear_attenuation;
    }

    public void setQuadratic_attenuation(double quadratic_attenuation) {
        this.quadratic_attenuation = quadratic_attenuation;
    }

}
