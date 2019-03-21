package cz.uhk.brabec.graphics.render;

import cz.uhk.brabec.controllers.SceneController;
import cz.uhk.brabec.graphics.model.Part;
import cz.uhk.brabec.graphics.model.Solid;
import cz.uhk.brabec.graphics.model.Vertex;

import java.util.List;

public class Renderer {

    private Rasterizer rasterizer;
    private SceneController scene;

    public Renderer(Rasterizer rasterizer, SceneController scene) {
        this.rasterizer = rasterizer;
        this.scene = scene;
    }

    public void render(Solid solid, boolean wireframe) {
        List<Part> parts = solid.getPartBuffer();
        for (Part part : parts) {
            switch (part.getTopology()) {
                case Points:
                    for (int i = 0; i < part.getCount(); i++) {
                        int index = solid.getIndexBuffer().get(part.getStart() + i);
                        Vertex a = solid.getVertexBuffer().get(index);
                        renderPoint(a, solid);
                    }
                    break;
                case Lines:
                    for (int i = 0; i < part.getCount(); i++) {
                        Vertex a = solid.getVertexBuffer().get(solid.getIndexBuffer().get(2 * i + part.getStart()));
                        Vertex b = solid.getVertexBuffer().get(solid.getIndexBuffer().get(2 * i + part.getStart() + 1));

                        renderLine(a, b, solid);
                    }
                    break;
                case LineLoop:
                    break;
                case LineStrip:
                    break;
                case Triangles:
                    for (int i = 0; i < part.getCount(); i++) {
                        Vertex a = solid.getVertexBuffer().get(solid.getIndexBuffer().get(3 * i + part.getStart()));
                        Vertex b = solid.getVertexBuffer().get(solid.getIndexBuffer().get(3 * i + part.getStart() + 1));
                        Vertex c = solid.getVertexBuffer().get(solid.getIndexBuffer().get(3 * i + part.getStart() + 2));

                        if (wireframe) {
                            renderLine(a, b, solid);
                            renderLine(b, c, solid);
                            renderLine(c, a, solid);
                        } else {
                            renderTriangle(a, b, c, solid);
                        }
                    }
                    break;
                case TriangleStrip:
                    break;
                case TriangleFan:
                    for (int i = 0; i < part.getCount(); i++) {
                        Vertex a = solid.getVertexBuffer().get(solid.getIndexBuffer().get(part.getStart()));
                        Vertex b = solid.getVertexBuffer().get(solid.getIndexBuffer().get(3 * i + part.getStart() + 1));
                        Vertex c = solid.getVertexBuffer().get(solid.getIndexBuffer().get(3 * i + part.getStart() + 2));

                        if (wireframe) {
                            renderLine(a, b, solid);
                            renderLine(b, c, solid);
                            renderLine(c, a, solid);
                        } else {
                            renderTriangle(a, b, c, solid);
                        }
                    }
                    break;
            }
        }
    }

    private void renderPoint(Vertex a, Solid solid) {
        a = a.mul(solid.getModel()).mul(scene.getCamera().getViewMatrix()).mul(scene.getProjection());

        if (a.getPoint().getZ() < 0)
            return;

        rasterizer.rasterizePoint(a);
    }

    private void renderLine(Vertex a, Vertex b, Solid solid) {
        a = a.mul(solid.getModel()).mul(scene.getCamera().getViewMatrix()).mul(scene.getProjection());
        b = b.mul(solid.getModel()).mul(scene.getCamera().getViewMatrix()).mul(scene.getProjection());

        if (a.getPoint().getZ() < b.getPoint().getZ()) {
            Vertex t = a;
            a = b;
            b = t;
        }

        if (a.getPoint().getZ() < 0)
            return;

        if (b.getPoint().getZ() < 0) {
            double t = a.getPoint().getZ() / (a.getPoint().getZ() - b.getPoint().getZ());
            Vertex ab = b.mul(1 - t).add(a.mul(t));

            ab.setColor(b.getColor());
            rasterizer.rasterizeLine(a, ab);
            return;
        }

        rasterizer.rasterizeLine(a, b);
    }

    private void renderTriangle(Vertex a, Vertex b, Vertex c, Solid solid) {
        a = a.mul(solid.getModel()).mul(scene.getCamera().getViewMatrix()).mul(scene.getProjection());
        b = b.mul(solid.getModel()).mul(scene.getCamera().getViewMatrix()).mul(scene.getProjection());
        c = c.mul(solid.getModel()).mul(scene.getCamera().getViewMatrix()).mul(scene.getProjection());

        if (a.getPoint().getX() < -a.getPoint().getW() && b.getPoint().getX() < -b.getPoint().getW() && c.getPoint().getX() < -c.getPoint().getW() ||
                a.getPoint().getX() > a.getPoint().getW() && b.getPoint().getX() > b.getPoint().getW() && c.getPoint().getX() > c.getPoint().getW() ||
                a.getPoint().getY() < -a.getPoint().getW() && b.getPoint().getY() < -b.getPoint().getW() && c.getPoint().getY() < -c.getPoint().getW() ||
                a.getPoint().getY() > a.getPoint().getW() && b.getPoint().getY() > b.getPoint().getW() && c.getPoint().getY() > c.getPoint().getW() ||
                a.getPoint().getZ() < 0 && b.getPoint().getZ() < 0 && c.getPoint().getZ() < 0 ||
                a.getPoint().getZ() > a.getPoint().getW() && b.getPoint().getZ() > b.getPoint().getW() && c.getPoint().getZ() > c.getPoint().getW()) {
            return;
        }

        if (a.getPoint().getZ() < b.getPoint().getZ()) {
            Vertex t = a;
            a = b;
            b = t;
        }
        if (b.getPoint().getZ() < c.getPoint().getZ()) {
            Vertex t = b;
            b = c;
            c = t;
        }
        if (a.getPoint().getZ() < b.getPoint().getZ()) {
            Vertex t = a;
            a = b;
            b = t;
        }

        if (a.getPoint().getZ() < 0) {
            return;
        }

        if (b.getPoint().getZ() < 0) {
            double t = a.getPoint().getZ() / (a.getPoint().getZ() - b.getPoint().getZ());
            Vertex ab = b.mul(1 - t).add(a.mul(t));
            t = 0 - b.getPoint().getZ() / (a.getPoint().getZ() - b.getPoint().getZ());
            Vertex ac = c.mul(1 - t).add(a.mul(t));
            ab.setColor(b.getColor());
            ac.setColor(c.getColor());
            rasterizer.rasterizeTriangle(a, ab, ac);
            return;
        }

        if (c.getPoint().getZ() < 0) {

            double t = b.getPoint().getZ() / (b.getPoint().getZ() - c.getPoint().getZ());
            Vertex bc = c.mul(1 - t).add(b.mul(t));
            t = 0 - c.getPoint().getZ() / (a.getPoint().getZ() - c.getPoint().getZ());
            Vertex ac = c.mul(1 - t).add(a.mul(t));

            bc.setColor(c.getColor());
            ac.setColor(c.getColor());
            rasterizer.rasterizeTriangle(a, b, bc);
            rasterizer.rasterizeTriangle(a, ac, bc);
            return;
        }

        rasterizer.rasterizeTriangle(a, b, c);
    }

}
