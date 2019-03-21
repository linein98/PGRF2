package cz.uhk.brabec.graphics.model;

import transforms.Col;
import transforms.Mat4;
import transforms.Point3D;

public class Vertex {

    private Point3D point;
    private Col color;

    public Vertex(Point3D point) {
        this.point = point;
        color = new Col(0xffffff);
    }

    public Vertex(Point3D point, Col color) {
        this.point = point;
        this.color = color;
    }

    public Point3D getPoint() {
        return point;
    }

    public Vertex mul(double d) {
        return new Vertex(new Point3D(point.mul(d)), color);
    }

    public Vertex mul(Mat4 mat) {
        return new Vertex(new Point3D(point.mul(mat)), color);
    }

    public Vertex add(Vertex v) {
        return new Vertex(point.add(v.getPoint()), color);
    }

    public Vertex dehomog() {
        return mul(1 / point.getW());
    }

    public Col getColor() {
        return color;
    }

    public void setColor(Col color) {
        this.color = color;
    }

}