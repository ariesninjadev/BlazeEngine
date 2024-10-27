import com.ariesninja.BlazeEngine.Client;
import com.ariesninja.BlazeEngine.Model;
import com.ariesninja.BlazeEngine.Pose3D;
import com.ariesninja.BlazeEngine.World;

import java.awt.*;

public class Main {

    public static void main(String[] args) {

        Client client = new Client();

        World w = new World();

        client.load(w);

        Model m = new Model.CUBE(1);

        w.place(m, new Pose3D());

        client.getCamera().setPose(new Pose3D(-4,-3,-2));

    }

}
