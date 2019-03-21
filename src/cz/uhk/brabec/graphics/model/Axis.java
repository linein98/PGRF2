package cz.uhk.brabec.graphics.model;

import transforms.Col;
import transforms.Mat4Identity;
import transforms.Point3D;

import java.util.ArrayList;
import java.util.List;

public class Axis extends Solid {

    public Axis() {
        var x00 = new Vertex(new Point3D(0, 0, 0), new Col(0xff0000));
        var x01 = new Vertex(new Point3D(1, 0, 0), new Col(0xff0000));
        var x11 = new Vertex(new Point3D(0.95, 0.05, 0), new Col(0xff0000));
        var x12 = new Vertex(new Point3D(0.95, 0, 0.05), new Col(0xff0000));
        var x13 = new Vertex(new Point3D(0.95, -0.05, 0), new Col(0xff0000));
        var x14 = new Vertex(new Point3D(0.95, 0, -0.05), new Col(0xff0000));

        var y00 = new Vertex(new Point3D(0, 0, 0), new Col(0x00ff00));
        var y01 = new Vertex(new Point3D(0, 1, 0), new Col(0x00ff00));
        var y11 = new Vertex(new Point3D(0.05, 0.95, 0), new Col(0x00ff00));
        var y12 = new Vertex(new Point3D(0, 0.95, 0.05), new Col(0x00ff00));
        var y13 = new Vertex(new Point3D(-0.05, 0.95, 0), new Col(0x00ff00));
        var y14 = new Vertex(new Point3D(0, 0.95, -0.05), new Col(0x00ff00));

        var z00 = new Vertex(new Point3D(0, 0, 0), new Col(0x0000ff));
        var z01 = new Vertex(new Point3D(0, 0, 1), new Col(0x0000ff));
        var z11 = new Vertex(new Point3D(0.05, 0, 0.95), new Col(0x0000ff));
        var z12 = new Vertex(new Point3D(0, 0.05, 0.95), new Col(0x0000ff));
        var z13 = new Vertex(new Point3D(-0.05, 0, 0.95), new Col(0x0000ff));
        var z14 = new Vertex(new Point3D(0, -0.05, 0.95), new Col(0x0000ff));

        vertexBuffer = List.of(x00, x01, x11, x12, x13, x14,
                y00, y01, y11, y12, y13, y14,
                z00, z01, z11, z12, z13, z14);

        indexBuffer = new ArrayList<>();
        //x
        indexBuffer.add(0);
        indexBuffer.add(1);

        indexBuffer.add(1);
        indexBuffer.add(2);
        indexBuffer.add(3);
        indexBuffer.add(-1);
        indexBuffer.add(3);
        indexBuffer.add(4);
        indexBuffer.add(-1);
        indexBuffer.add(4);
        indexBuffer.add(5);
        indexBuffer.add(-1);
        indexBuffer.add(5);
        indexBuffer.add(2);
        //y
        indexBuffer.add(6);//14
        indexBuffer.add(7);

        indexBuffer.add(7);
        indexBuffer.add(8);
        indexBuffer.add(9);
        indexBuffer.add(-1);
        indexBuffer.add(9);
        indexBuffer.add(10);
        indexBuffer.add(-1);
        indexBuffer.add(10);
        indexBuffer.add(11);
        indexBuffer.add(-1);
        indexBuffer.add(11);
        indexBuffer.add(8);
        //z
        indexBuffer.add(12);//28
        indexBuffer.add(13);

        indexBuffer.add(13);
        indexBuffer.add(14);
        indexBuffer.add(15);
        indexBuffer.add(-1);
        indexBuffer.add(15);
        indexBuffer.add(16);
        indexBuffer.add(-1);
        indexBuffer.add(16);
        indexBuffer.add(17);
        indexBuffer.add(-1);
        indexBuffer.add(17);
        indexBuffer.add(14);

        partBuffer = new ArrayList<>();
        partBuffer.add(new Part(Topology.Lines, 1, 0));
        partBuffer.add(new Part(Topology.TriangleFan, 4, 2));
        partBuffer.add(new Part(Topology.Lines, 1, 14));
        partBuffer.add(new Part(Topology.TriangleFan, 4, 16));
        partBuffer.add(new Part(Topology.Lines, 1, 28));
        partBuffer.add(new Part(Topology.TriangleFan, 4, 30));

        model = new Mat4Identity();
        visible = true;
    }

}
