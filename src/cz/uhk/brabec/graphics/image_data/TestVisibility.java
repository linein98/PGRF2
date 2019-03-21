package cz.uhk.brabec.graphics.image_data;

import transforms.Col;

import java.awt.image.BufferedImage;

public class TestVisibility {

    private ImageBuffer imgBuffer;
    private DepthBuffer depthBuffer;

    public TestVisibility(BufferedImage bufferedImage) {
        imgBuffer = new ImageBuffer(bufferedImage);
        depthBuffer = new DepthBuffer(imgBuffer.getWidth(), imgBuffer.getHeight());
    }

    public void clear() {
        depthBuffer.clear();
        imgBuffer.clear();
    }

    public boolean isVisible(int x, int y, float z) {
        return depthBuffer.getValue(x, y) >= z;
    }

    public void renderPixel(int x, int y, float z, Col color) {
        if (x >= 0 && x < getWidth() && y >= 0 && y < getHeight())
            if (isVisible(x, y, z)) {
                depthBuffer.setValue(x, y, z);
                imgBuffer.setValue(x, y, color.getARGB());
            }
    }

    public int getWidth() {
        return imgBuffer.getWidth();
    }

    public int getHeight() {
        return imgBuffer.getHeight();
    }

}
