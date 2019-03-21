package cz.uhk.brabec.graphics.model;

import transforms.Col;
import transforms.Mat4Identity;
import transforms.Point3D;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimplePyramid extends Solid {

    public SimplePyramid() {
        var s = new Vertex(new Point3D(-0, 0, 1), new Col(getColor()));

        var a = new Vertex(new Point3D(-1, -1, -1), new Col(getColor()));
        var b = new Vertex(new Point3D(1, -1, -1), new Col(getColor()));
        var c = new Vertex(new Point3D(1, 1, -1), new Col(getColor()));
        var d = new Vertex(new Point3D(-1, 1, -1), new Col(getColor()));

        vertexBuffer = List.of(s, a, b, c, d);

        indexBuffer = new ArrayList<>();
        indexBuffer.add(0);
        indexBuffer.add(1);
        indexBuffer.add(2);
        indexBuffer.add(-1);
        indexBuffer.add(2);
        indexBuffer.add(3);
        indexBuffer.add(-1);
        indexBuffer.add(3);
        indexBuffer.add(4);
        indexBuffer.add(-1);
        indexBuffer.add(4);
        indexBuffer.add(1);

        indexBuffer.add(1);
        indexBuffer.add(2);
        indexBuffer.add(3);
        indexBuffer.add(1);
        indexBuffer.add(4);
        indexBuffer.add(3);

        partBuffer = new ArrayList<>();
        partBuffer.add(new Part(Topology.TriangleFan, 4, 0));
        partBuffer.add(new Part(Topology.Triangles, 2, 12));

        model = new Mat4Identity();
        visible = true;
    }

    private Col getColor() {
        Random random = new Random();
        int r = random.nextInt(255);
        int g = random.nextInt(255);
        int b = random.nextInt(255);
        return new Col(r, g, b);
    }

}
