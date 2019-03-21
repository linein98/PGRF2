package cz.uhk.brabec.graphics.render;


import cz.uhk.brabec.graphics.image_data.TestVisibility;
import cz.uhk.brabec.graphics.model.Vertex;
import transforms.Col;
import transforms.Point3D;

import java.util.Random;
import java.util.function.Function;

public class Rasterizer {

    private TestVisibility testVisibility;
    private Function<Vertex, Col> shader;
    private Random random;

    public Rasterizer(TestVisibility testVisibility, Function<Vertex, Col> shader) {
        this.testVisibility = testVisibility;
        this.shader = shader;
        random = new Random();
    }

    public void rasterizeTriangle(Vertex a, Vertex b, Vertex c) {
        a = a.dehomog();
        b = b.dehomog();
        c = c.dehomog();

        Point3D pointA = getPointCoordinates(a.getPoint());
        Point3D pointB = getPointCoordinates(b.getPoint());
        Point3D pointC = getPointCoordinates(c.getPoint());

        if (pointA.getY() > pointB.getY()) {
            Point3D temp = pointA;
            pointA = pointB;
            pointB = temp;
        }

        if (pointB.getY() > pointC.getY()) {
            Point3D temp = pointB;
            pointB = pointC;
            pointC = temp;
        }

        if (pointA.getY() > pointB.getY()) {
            Point3D temp = pointA;
            pointA = pointB;
            pointB = temp;
        }

        for (int y = Math.max((int) pointA.getY() + 1, 0); y < Math.min(pointB.getY(), testVisibility.getHeight() - 1); y++) {
            double t1 = (y - pointA.getY()) / (pointB.getY() - pointA.getY());
            double x1 = pointA.getX() * (1.0 - t1) + pointB.getX() * t1;
            double z1 = pointA.getZ() * (1.0 - t1) + pointB.getZ() * t1;
            double t2 = (y - pointA.getY()) / (pointC.getY() - pointA.getY());
            double x2 = pointA.getX() * (1.0 - t2) + pointC.getX() * t2;
            double z2 = pointA.getZ() * (1.0 - t2) + pointC.getZ() * t2;

            if (x1 > x2) {
                double temp = x1;
                x1 = x2;
                x2 = temp;
                temp = z1;
                z1 = z2;
                z2 = temp;
            }

            for (int x = Math.max((int) x1 + 1, 0); x < Math.min(x2, testVisibility.getWidth() - 1); x++) {
                double t = (x - x1) / (x2 - x1);
                double z = z1 * (1 - t) + z2 * t;
                Vertex abc = new Vertex(null, a.getColor().add(b.getColor().add(c.getColor())));
                testVisibility.renderPixel(x, y, (float) z, shader.apply(abc));
            }
        }

        for (int y = Math.max((int) pointB.getY() + 1, 0); y < Math.min(pointC.getY(), testVisibility.getHeight() - 1); y++) {
            double t1 = (y - pointB.getY()) / (pointC.getY() - pointB.getY());
            double x1 = pointB.getX() * (1.0 - t1) + pointC.getX() * t1;
            double z1 = pointB.getZ() * (1.0 - t1) + pointC.getZ() * t1;
            double t2 = (y - pointA.getY()) / (pointC.getY() - pointA.getY());
            double x2 = pointA.getX() * (1.0 - t2) + pointC.getX() * t2;
            double z2 = pointA.getZ() * (1.0 - t2) + pointC.getZ() * t2;

            if (x1 > x2) {
                double temp = x1;
                x1 = x2;
                x2 = temp;
                temp = z1;
                z1 = z2;
                z2 = temp;
            }

            for (int x = Math.max((int) x1 + 1, 0); x < Math.min(x2, testVisibility.getWidth() - 1); x++) {
                double t = (x - x1) / (x2 - x1);
                double z = z1 * (1 - t) + z2 * t;
                Vertex abc = new Vertex(null, a.getColor().add(b.getColor().add(c.getColor())));
                testVisibility.renderPixel(x, y, (float) z, shader.apply(abc));
            }
        }
    }

    private Point3D getPointCoordinates(Point3D point) {
        double x = (point.getX() + 1) * (testVisibility.getWidth() - 1) / 2;
        double y = (-point.getY() + 1) * (testVisibility.getHeight() - 1) / 2;

        return new Point3D(x, y, point.getZ(), point.getW());
    }

    public void rasterizeLine(Vertex a, Vertex b) {
        a = a.dehomog();
        b = b.dehomog();

        Point3D pointA = getPointCoordinates(a.getPoint());
        Point3D pointB = getPointCoordinates(b.getPoint());

        if (Math.abs(pointA.getX() - pointB.getX()) > Math.abs(pointA.getY() - pointB.getY())) { // X-axis
            if (pointA.getX() > pointB.getX()) {
                Point3D temp = pointA;
                pointA = pointB;
                pointB = temp;
            }
            double t;
            double z;
            int y;
            for (int x = Math.max((int) pointA.getX() + 1, 0); x < Math.min(pointB.getX(), testVisibility.getWidth() - 1); x++) {
                t = (x - pointA.getX()) / (pointB.getX() - pointA.getX());
                y = (int) ((1.0 - t) * pointA.getY() + t * pointB.getY());
                z = pointA.getZ() * (1.0 - t) + pointB.getZ() * t;

                Vertex abc = new Vertex(null, a.getColor().add(b.getColor()));
                testVisibility.renderPixel(x, y, (float) z, shader.apply(abc));
            }
        } else {
            if (pointA.getY() > pointB.getY()) {
                Point3D temp = pointA;
                pointA = pointB;
                pointB = temp;
            }
            double t;
            double z;
            int x;
            for (int y = Math.max((int) pointA.getY() + 1, 0); y < Math.min(pointB.getY(), testVisibility.getWidth() - 1); y++) {
                t = (y - pointA.getY()) / (pointB.getY() - pointA.getY());
                z = pointA.getZ() * (1.0 - t) + pointB.getZ() * t;
                x = (int) ((1.0 - t) * pointA.getX() + t * pointB.getX());

                Vertex abc = new Vertex(null, a.getColor().add(b.getColor()));
                testVisibility.renderPixel(x, y, (float) z, shader.apply(abc));
            }
        }
    }

    public void rasterizePoint(Vertex v) {
        v = v.dehomog();
        Point3D a = getPointCoordinates(v.getPoint());
        testVisibility.renderPixel((int) a.getX(), (int) a.getY(), (float) a.getZ(), shader.apply(v));
    }

}
