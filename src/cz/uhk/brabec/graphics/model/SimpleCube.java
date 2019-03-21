package cz.uhk.brabec.graphics.model;

import transforms.Col;
import transforms.Mat4Identity;
import transforms.Point3D;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimpleCube extends Solid {

    public SimpleCube() {
        var a = new Vertex(new Point3D(-1, -1, -1), new Col(getColor()));
        var b = new Vertex(new Point3D(1, -1, -1), new Col(getColor()));
        var c = new Vertex(new Point3D(1, 1, -1), new Col(getColor()));
        var d = new Vertex(new Point3D(-1, 1, -1), new Col(getColor()));

        var e = new Vertex(new Point3D(-1, -1, 1), new Col(getColor()));
        var f = new Vertex(new Point3D(1, -1, 1), new Col(getColor()));
        var g = new Vertex(new Point3D(1, 1, 1), new Col(getColor()));
        var h = new Vertex(new Point3D(-1, 1, 1), new Col(getColor()));

        vertexBuffer = List.of(a, b, c, d, e, f, g, h);

        indexBuffer = new ArrayList<>();
        indexBuffer.add(0);
        indexBuffer.add(1);
        indexBuffer.add(2);
        indexBuffer.add(0);
        indexBuffer.add(3);
        indexBuffer.add(2);

        indexBuffer.add(0);
        indexBuffer.add(4);
        indexBuffer.add(5);
        indexBuffer.add(0);
        indexBuffer.add(1);
        indexBuffer.add(5);

        indexBuffer.add(1);
        indexBuffer.add(5);
        indexBuffer.add(6);
        indexBuffer.add(1);
        indexBuffer.add(2);
        indexBuffer.add(6);

        indexBuffer.add(4);
        indexBuffer.add(7);
        indexBuffer.add(3);
        indexBuffer.add(4);
        indexBuffer.add(0);
        indexBuffer.add(3);

        indexBuffer.add(7);
        indexBuffer.add(3);
        indexBuffer.add(2);
        indexBuffer.add(7);
        indexBuffer.add(6);
        indexBuffer.add(2);

        indexBuffer.add(7);
        indexBuffer.add(4);
        indexBuffer.add(5);
        indexBuffer.add(7);
        indexBuffer.add(6);
        indexBuffer.add(5);

        partBuffer = new ArrayList<>();
        partBuffer.add(new Part(Topology.Triangles, 12, 0));

        model = new Mat4Identity();
        visible = true;
    }

    /**
     * generator barev
     *
     * @return nahodne vygenerovanou barvu
     */
    private Col getColor() {
        Random random = new Random();
        int r = random.nextInt(255);
        int g = random.nextInt(255);
        int b = random.nextInt(255);
        return new Col(r, g, b);
    }

}
