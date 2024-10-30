import com.ariesninja.BlazeEngine.*;
import com.ariesninja.BlazeEngine.structs.Light;
import com.ariesninja.BlazeEngine.structs.Model;
import com.ariesninja.BlazeEngine.utils3d.Coordinate3D;
import com.ariesninja.BlazeEngine.utils3d.Pose3D;

import java.awt.*;

public class Main {

    public static void main(String[] args) {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.width * 2 / 3.0);
        int height = (int) (screenSize.height * 2 / 3.0);

        // Create the Client with the calculated dimensions
        Client client = new Client(width, height);

        World w = new World();

        client.load(w);

        client.getCamera().setPose(new Pose3D(0, 0, -5));
        client.getCamera().setFov(60);

        // Ground
        //w.place(new Model.RECTANGULAR_PRISM(40, 2, 40), new Pose3D(-20, -10, -20), new Color(128, 128, 128));

        //w.place(new Model.CUBE(1), new Pose3D(0, 0, 0), Color.WHITE);

        Light light = new Light(new Coordinate3D(9, 12, 27), 7, new Color(54, 126, 221));
        w.addLight(light);
        Instance lightCube = w.place(new Model.CUBE(0.25), new Pose3D(9, 12, 27), new Color(54, 126, 221));

//        Light light2 = new Light(new Coordinate3D(6, 0, 0), 4, Color.WHITE);
//        w.addLight(light2);
//        Instance lightCube2 = w.place(new Model.CUBE(0.25), new Pose3D(6, 0, 0), Color.WHITE);

        //w.place(new Model.CUBE(1), new Pose3D(8, 0, 0), Color.RED);

       // w.place(new Model.CUBE(1), new Pose3D(0, 8, 0), Color.GREEN);

        for (int i = 1; i < 16; i++) {
            for (int j = 4; j < 12; j++) {
                w.place(new Model.CUBE(1), new Pose3D(i, j, j+i), Color.WHITE);
            }
        }

        client.getCamera().setPose(new Pose3D(6, 10, -5, -10, 0, 0));

        // Clock that moves the light between x = 0 and x = 20 by 0.1 every 100ms
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
                int red = (currentColor.getRed() + 1) % 256;
                int green = (currentColor.getGreen() + 1) % 256;
                int blue = (currentColor.getBlue() + 1) % 256;
                Color newColor = new Color(red, green, blue);
                light.setColor(newColor);
                lightCube.setColor(newColor);

                // Move light
                if (right) {
                    light.move(0.1, 0, 0);
                    lightCube.move(0.1, 0, 0);
                    if (light.getPosition().x >= 16) {
                        right = false;
                    }
                } else {
                    light.move(-0.1, 0, 0);
                    lightCube.move(-0.1, 0, 0);
                    if (light.getPosition().x <= 0) {
                        right = true;
                    }
                }
            }
        }).start();

    }

}
