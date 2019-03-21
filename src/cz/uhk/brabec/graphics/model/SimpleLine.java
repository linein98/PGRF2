package cz.uhk.brabec.graphics.model;

import transforms.Col;
import transforms.Mat4Identity;
import transforms.Point3D;

import java.util.ArrayList;
import java.util.List;

public class SimpleLine extends Solid {

    public SimpleLine() {
        var a = new Vertex(new Point3D(0, 0, 0), new Col(0x00ff00));
        var b = new Vertex(new Point3D(1, 0, 0), new Col(0xff0000));

        vertexBuffer = List.of(a, b);

        indexBuffer = new ArrayList<>();
        indexBuffer.add(0);
        indexBuffer.add(1);

        partBuffer = new ArrayList<>();
        partBuffer.add(new Part(Topology.Lines, 1, 0));

        model = new Mat4Identity();
        visible = true;
    }

}
