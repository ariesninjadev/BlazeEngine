import com.ariesninja.BlazeEngine.*;
import com.ariesninja.BlazeEngine.structs.Light;
import com.ariesninja.BlazeEngine.structs.Model;
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

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.width * 2 / 3.0);
        int height = (int) (screenSize.height * 2 / 3.0); //1080 695

        // Create the Client with the calculated dimensions
        Client client = new Client(Math.max(width, 1080), Math.max(height, 660));

        World w = new World();

        client.load(w);

        client.getCamera().setPose(new Pose3D(0, 0, -5));
        client.getCamera().setFov(60);

        // Ground
        //w.place(new Model.RECTANGULAR_PRISM(40, 2, 40), new Pose3D(-20, -10, -20), new Color(128, 128, 128));

        //w.place(new Model.CUBE(1), new Pose3D(0, 0, 0), Color.WHITE);

        Light light = new Light(new Coordinate3D(9, 12, 27), 7, new Color(54, 126, 221));
        Instance lightCube = w.place(new Model.CUBE(0.25), new Pose3D(9, 12, 27), new Color(54, 126, 221));
//
//        Light light2 = new Light(new Coordinate3D(6, 0, 0), 4, Color.WHITE);
//        Instance lightCube2 = w.place(new Model.CUBE(0.25), new Pose3D(6, 0, 0), Color.WHITE);
//
//        w.addLight(light2);
        w.addLight(light);

        //w.place(new Model.CUBE(1), new Pose3D(8, 0, 0), Color.RED);

       // w.place(new Model.CUBE(1), new Pose3D(0, 8, 0), Color.GREEN);

        for (int i = 1; i < 16; i++) {
            for (int j = 4; j < 12; j++) {
                w.place(new Model.CUBE(1), new Pose3D(i, j, j+i), Color.BLACK);
            }
        }

        client.getCamera().setPose(new Pose3D(6, 10, -5, -10, 0, 0));



        new Thread(() -> {
            boolean right = true;
            while (true) {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                // Smoothly cycle colors
//                Color currentColor = light.getColor();
//                Color newColor = cycleColor(currentColor, 0.001); // Adjust speed as needed
//                light.setColor(newColor);
//                lightCube.setColor(newColor);

                // Move light with smooth translation
                double maxSpeed = 0.1;
                double minSpeed = 0.01;
                double range = 16.0;

                if (right) {
                    double distanceToEnd = range - light.getPosition().x;
                    double speed = minSpeed + (maxSpeed - minSpeed) * (distanceToEnd / range);
                    light.move(speed, 0, 0);
                    lightCube.move(speed, 0, 0);
                    if (light.getPosition().x >= range) {
                        right = false;
                    }
                } else {
                    double distanceToStart = light.getPosition().x;
                    double speed = minSpeed + (maxSpeed - minSpeed) * (distanceToStart / range);
                    light.move(-speed, 0, 0);
                    lightCube.move(-speed, 0, 0);
                    if (light.getPosition().x <= 0) {
                        right = true;
                    }
                }
            }
        }).start();

    }

}
