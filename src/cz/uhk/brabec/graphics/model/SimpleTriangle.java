package cz.uhk.brabec.graphics.model;

import transforms.Mat4Identity;

import java.util.ArrayList;
import java.util.List;

public class SimpleTriangle extends Solid {

    public SimpleTriangle(Vertex a, Vertex b, Vertex c) {
        vertexBuffer = List.of(a, b, c);

        indexBuffer = new ArrayList<>();
        indexBuffer.add(0);
        indexBuffer.add(1);
        indexBuffer.add(2);

        partBuffer = new ArrayList<>();
        partBuffer.add(new Part(Topology.Triangles, 1, 0));

        model = new Mat4Identity();
        visible = true;
    }

}
