package cz.uhk.brabec.graphics.image_data;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageBuffer implements Image<Integer> {

    private BufferedImage img;

    public ImageBuffer(BufferedImage img) {
        this.img = img;
    }

    public void clear() {
        Graphics g = img.getGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, img.getWidth(), img.getHeight());
    }

    @Override
    public int getWidth() {
        return img.getWidth();
    }

    @Override
    public int getHeight() {
        return img.getHeight();
    }

    @Override
    public void setValue(int x, int y, Integer value) {
        img.setRGB(x, y, value);
    }

    @Override
    public Integer getValue(int x, int y) {
        return img.getRGB(x, y);
    }

}
