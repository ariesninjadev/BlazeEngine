// In `src/main/java/com/ariesninja/BlazeEngine/renderer/ShadowRenderer.java`
package com.ariesninja.BlazeEngine.math;

import com.ariesninja.BlazeEngine.Client;
import com.ariesninja.BlazeEngine.math.*;
import com.ariesninja.BlazeEngine.structs.Instance;
import com.ariesninja.BlazeEngine.structs.Light;
import com.ariesninja.BlazeEngine.structs.World;
import com.ariesninja.BlazeEngine.utils3d.Coordinate3D;
import com.ariesninja.BlazeEngine.utils3d.Pose3D;
import com.ariesninja.BlazeEngine.utils3d.Surface3D;

public class Shadows {

    public static double[][] createDepthMap(Client client, Light light) {
        World world = client.getWorld();
        int mapSize = 1024; // Size of the depth map
        double[][] depthMap = new double[mapSize][mapSize];

        for (int x = 0; x < mapSize; x++) {
            for (int y = 0; y < mapSize; y++) {
                depthMap[x][y] = Double.MAX_VALUE;
            }
        }

        for (Instance instance : world.getModels()) {
            System.out.println("Instance: " + instance);
            for (Surface3D surface : instance.getModel().getSurfaces()) {
                for (Coordinate3D vertex : surface.getVertices()) {
                    double depth = calculateVertexDepth(instance, light, vertex.x, vertex.y, vertex.z);
                    int mapX = (int) ((vertex.x / client.getCamera().getScreenWidth()) * mapSize);
                    int mapY = (int) ((vertex.y / client.getCamera().getScreenHeight()) * mapSize);
                    System.out.println("Depth: " + depth + " MapX: " + mapX + " MapY: " + mapY);
                    if (depth < depthMap[mapX][mapY]) {
                        depthMap[mapX][mapY] = depth;
                        depthMap[mapX+1][mapY] = depth;
                        depthMap[mapX][mapY+1] = depth;
                        depthMap[mapX+1][mapY+1] = depth;
                    }
                }
            }
        }

        //System.out.println(depthMap[512][512]);

        return depthMap;
    }

    private static double calculateVertexDepth(Instance instance, Light light, double x, double y, double z) {
        Pose3D pose = instance.getPose();
        double px = pose.getPosition().getX();
        double py = pose.getPosition().getY();
        double pz = pose.getPosition().getZ();

        double lightX = light.getPosition().x;
        double lightY = light.getPosition().y;
        double lightZ = light.getPosition().z;

        double adjustedX = x + px;
        double adjustedY = y + py;
        double adjustedZ = z + pz;

        return Math.sqrt(Math.pow(adjustedX - lightX, 2) +
                Math.pow(adjustedY - lightY, 2) +
                Math.pow(adjustedZ - lightZ, 2));
    }
}