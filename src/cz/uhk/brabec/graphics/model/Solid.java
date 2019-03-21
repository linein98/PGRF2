package cz.uhk.brabec.graphics.model;

import transforms.Mat4;

import java.util.List;

public abstract class Solid {

    protected List<Vertex> vertexBuffer;
    protected List<Integer> indexBuffer;
    protected List<Part> partBuffer;
    protected Mat4 model;
    protected boolean visible;

    public List<Vertex> getVertexBuffer() {
        return vertexBuffer;
    }

    public List<Integer> getIndexBuffer() {
        return indexBuffer;
    }

    public List<Part> getPartBuffer() {
        return partBuffer;
    }

    public Mat4 getModel() {
        return model;
    }

    public void setModel(Mat4 model) {
        this.model = model;
    }

    public void modelMul(Mat4 mat) {
        model = model.mul(mat);
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible() {
        this.visible = !visible;
    }

}
