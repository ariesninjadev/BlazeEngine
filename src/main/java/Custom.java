import com.ariesninja.BlazeEngine.structs.Model;
import com.ariesninja.BlazeEngine.utils3d.Coordinate3D;
import com.ariesninja.BlazeEngine.utils3d.Line3D;
import com.ariesninja.BlazeEngine.utils3d.Surface3D;

import java.util.ArrayList;
import java.util.List;

public class Custom {

    public static Model a() {

        ArrayList<Line3D> l = new ArrayList<>();


        l.add(new Line3D(new Coordinate3D(0, 3, 0), new Coordinate3D(3, -3, 0)));
        l.add(new Line3D(new Coordinate3D(0, 3, 0), new Coordinate3D(-3, -3, 0)));

        l.add(new Line3D(new Coordinate3D(1.5, -3, 0), new Coordinate3D(3, -3, 0)));
        l.add(new Line3D(new Coordinate3D(-1.5, -3, 0), new Coordinate3D(-3, -3, 0)));

        l.add(new Line3D(new Coordinate3D(1.5, -3, 0), new Coordinate3D(0.9, -1.8, 0)));
        l.add(new Line3D(new Coordinate3D(-1.5, -3, 0), new Coordinate3D(-0.9, -1.8, 0)));

        l.add(new Line3D(new Coordinate3D(0.9, -1.8, 0), new Coordinate3D(-0.9, -1.8, 0)));

        l.add(new Line3D(new Coordinate3D(0.5, -0.6, 0), new Coordinate3D(-0.5, -0.6, 0)));

        l.add(new Line3D(new Coordinate3D(0.5, -0.6, 0), new Coordinate3D(0, 0.4, 0)));
        l.add(new Line3D(new Coordinate3D(-0.5, -0.6, 0), new Coordinate3D(0, 0.4, 0)));

        Model.POLYGON_BUILDER m = new Model.POLYGON_BUILDER(l);

        // Faces

        List<Coordinate3D> faces = new ArrayList<>();
        faces.add(new Coordinate3D(0, 3, 0));
        faces.add(new Coordinate3D(0, 0.4, 0));
        faces.add(new Coordinate3D(-0.5, -0.6, 0));
        faces.add(new Coordinate3D(-0.9, -1.8, 0));
        faces.add(new Coordinate3D(-1.5, -3, 0));
        faces.add(new Coordinate3D(-3, -3, 0));
        m.surfaces.add(new Surface3D(faces));

        faces = new ArrayList<>();
        faces.add(new Coordinate3D(0, 3, 0));
        faces.add(new Coordinate3D(3, -3, 0));
        faces.add(new Coordinate3D(1.5, -3, 0));
        faces.add(new Coordinate3D(0.9, -1.8, 0));
        faces.add(new Coordinate3D(0.5, -0.6, 0));
        faces.add(new Coordinate3D(0, 0.4, 0));
        m.surfaces.add(new Surface3D(faces));

        faces = new ArrayList<>();
        faces.add(new Coordinate3D(0.5, -0.6, 0));
        faces.add(new Coordinate3D(0.9, -1.8, 0));
        faces.add(new Coordinate3D(-0.9, -1.8, 0));
        faces.add(new Coordinate3D(-0.5, -0.6, 0));
        m.surfaces.add(new Surface3D(faces));

        // Extrude

        m.extrude(0, 0, 1.5);

        return m;

    }

}
