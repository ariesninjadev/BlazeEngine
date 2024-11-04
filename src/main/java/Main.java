import com.ariesninja.BlazeEngine.*;
import com.ariesninja.BlazeEngine.math.Attenuation;
import com.ariesninja.BlazeEngine.structs.Instance;
import com.ariesninja.BlazeEngine.structs.Light;
import com.ariesninja.BlazeEngine.structs.Model;
import com.ariesninja.BlazeEngine.structs.World;
import com.ariesninja.BlazeEngine.utils3d.Coordinate3D;
import com.ariesninja.BlazeEngine.utils3d.Pose3D;

import java.awt.*;

public class Main {

    private static Color cycleColor(Color color, double speed) {
        float[] hsbVals = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        float hue = (hsbVals[0] + (float) speed) % 1.0f; // Cycle hue
        return Color.getHSBColor(hue, hsbVals[1], hsbVals[2]);
    }

    public static void main(String[] args) {

        // Calculate the desired dimensions of the window
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.width * 2 / 3.0);
        int height = (int) (screenSize.height * 2 / 3.0); //1080 695

        // Create the Client with the calculated dimensions
        Client client = new Client(Math.max(width, 1080), Math.max(height, 450));

        // Create a new World
        World w = new World();

        // Load the World into the Client
        client.load(w);

        // Set the camera's position and field of view
        client.getCamera().setPose(new Pose3D(6, 10, -5, -10, 0, 0));
        client.getCamera().setFov(90);

        // Create a light and a cube to represent it
        Light light = new Light(new Coordinate3D(16, 12, 28), 12, new Color(54, 126, 221));
        Instance lightCube = w.place(new Model.CUBE(0.25), new Pose3D(16, 12, 28), new Color(54, 126, 221));

        // Create a second light and a cube to represent it
        Light light2 = new Light(new Coordinate3D(1, 4, 2), 12, Color.WHITE, new Attenuation(1, 0.02, 0.4));
        Instance lightCube2 = w.place(new Model.CUBE(0.25), new Pose3D(1, 4, 2), Color.WHITE);
//
//        // Add the lights to the World
        w.addLight(light2);
        w.addLight(light);

//        Light light = new Light(new Coordinate3D(0, 8, 0), 2, Color.WHITE);
//        w.addLight(light);
//
//        w.place(new Model.CUBE(1), new Pose3D(0, 6, 0), Color.WHITE);
//
//        w.place(new Model.CUBE(1), new Pose3D(0, 2, 0), Color.WHITE);

        // Create a grid of black cubes
        for (int i = 1; i < 16; i++) {
            for (int j = 4; j < 12; j++) {
                w.place(new Model.CUBE(1), new Pose3D(i, j, j+i), Color.WHITE);
            }
        }
//
        // Start a new thread to animate one of the lights
        new Thread(() -> {
            boolean right = true;
            while (true) {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // Smoothly cycle colors
                Color currentColor = light.getColor();
                Color newColor = cycleColor(currentColor, 0.001);
                light.setColor(newColor);
                lightCube.setColor(newColor);

                // Move light with smooth translation
                double maxSpeed = 0.2;
                double minSpeed = 0.05;
                double range = 16.0;

                if (right) {
                    double distanceToEnd = range - light.getPosition().x;
                    double speed = minSpeed + (maxSpeed - minSpeed) * (distanceToEnd / range);
                    light.move(speed, 0, speed/1.5);
                    lightCube.move(speed, 0, speed/1.5);
                    if (light.getPosition().x >= range) {
                        right = false;
                    }
                } else {
                    double distanceToStart = light.getPosition().x;
                    double speed = minSpeed + (maxSpeed - minSpeed) * (distanceToStart / range);
                    light.move(-speed, 0, -speed/1.5);
                    lightCube.move(-speed, 0, -speed/1.5);
                    if (light.getPosition().x <= 0) {
                        right = true;
                    }
                }
            }
        }).start();

    }

}
