// src/main/java/com/ariesninja/BlazeEngine/utils/LightingCalculator.java
package com.ariesninja.BlazeEngine.math;

import com.ariesninja.BlazeEngine.structs.Light;
import com.ariesninja.BlazeEngine.utils3d.EnhancedPolygon;
import com.ariesninja.BlazeEngine.structs.World;

import java.awt.*;
import java.util.List;

public class Lighting {
    public void applyLighting(List<EnhancedPolygon> polygons, List<Light> lights, World world) {
        for (EnhancedPolygon polygon : polygons) {
            int r = 0, g = 0, b = 0;

            for (Light light : lights) {
                Color lightColor = Computation.calculateLighting(polygon, light, world);
                r += lightColor.getRed();
                g += lightColor.getGreen();
                b += lightColor.getBlue();
            }

            // Clamp the color values to be within the valid range
            r = Math.min(255, r);
            g = Math.min(255, g);
            b = Math.min(255, b);

            polygon.setColor(new Color(r, g, b));
        }
    }
}