package cz.uhk.brabec.graphics.image_data;

public class DepthBuffer implements Image<Float> {

    private float[][] depthBuffer;

    private int width;
    private int height;

    public DepthBuffer(int width, int height) {
        this.width = width;
        this.height = height;
        depthBuffer = new float[height][width];
    }

    public void clear() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                depthBuffer[i][j] = 1.0f;
            }
        }
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void setValue(int x, int y, Float value) {
        depthBuffer[y][x] = value;
    }

    @Override
    public Float getValue(int x, int y) {
        return depthBuffer[y][x];
    }

}
