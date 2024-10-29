import com.ariesninja.BlazeEngine.*;

import java.awt.*;

public class Main {

    public static void main(String[] args) {

        Client client = new Client(1920, 1080);

        World w = new World();

        client.load(w);

        client.getCamera().setPose(new Pose3D(0,0,-5));

        // Ground
         Instance ground = w.place(new Model.RECTANGULAR_PRISM(40, 1, 40), new Pose3D(-20, -10, -20), new Color(128, 128, 128));
         w.place(ground);

        w.place(new Model.CUBE(1), new Pose3D(8, 0, 0), Color.RED);

        w.place(new Model.CUBE(1), new Pose3D(1, 6, 0), Color.GREEN);

        // w.place(new Model.RECTANGULAR_PRISM(1, 1, 38), new Pose3D(-18, -2, -18), Color.BLUE);

//        w.place(new Model.CUBE(1), new Pose3D(0, 1, 0));
//        w.place(new Model.RECTANGULAR_PRISM(3, 1, 1), new Pose3D(0, 2, 0));
//        w.place(new Model.RECTANGULAR_PRISM(1, 3, 1), new Pose3D(-1, 0, 0));
//        w.place(new Model.RECTANGULAR_PRISM(1, 3, 1), new Pose3D(1, 0, 0));

        // Make a letter A using the POLYGON_BUILDER model
        //Model m = Custom.a();

        //w.place(m, new Pose3D(2, 0, 0));

        client.getCamera().setPose(new Pose3D(0,0,-5));

    }

}
