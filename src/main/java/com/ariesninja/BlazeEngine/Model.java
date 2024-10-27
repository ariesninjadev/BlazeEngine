package com.ariesninja.BlazeEngine;

public class Model {

    double poly_width;
    double poly_height;
    double poly_length;

    boolean isWireframe = true; // No filling yet

    public static class CUBE extends Model {

        public CUBE(double s) {

            poly_width = s;
            poly_height = s;
            poly_length = s;

        }

        public double getSize() {
            return poly_width;
        }

    }

}
