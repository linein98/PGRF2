package cz.uhk.brabec.controllers;

import cz.uhk.brabec.graphics.image_data.TestVisibility;
import cz.uhk.brabec.graphics.model.*;
import cz.uhk.brabec.graphics.render.Rasterizer;
import cz.uhk.brabec.graphics.render.Renderer;
import transforms.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * sprava sceny
 */
public class SceneController {

    private final BufferedImage bufferedImage;
    /**
     * trida graphics panelu
     */
    private final Graphics graphics;
    private final TestVisibility testVisibility;
    private final Renderer renderer;
    private Rasterizer rasterizer;
    private boolean wireframe;
    private boolean help;

    private Camera camera;
    private Mat4 projection;

    private List<Solid> solids;

    public SceneController(BufferedImage bufferedImage, Graphics grahpics) {
        this.bufferedImage = bufferedImage;
        this.graphics = grahpics;
        testVisibility = new TestVisibility(bufferedImage);
        rasterizer = new Rasterizer(testVisibility, (vertex) -> vertex.getColor());
        renderer = new Renderer(rasterizer, this);
        wireframe = false;
        help = false;

        solids = new ArrayList<>();

        initDemoScene();
        setPerspProjection();
        setDefaultCamera();

        startRefreshing();
    }

    private void initDemoScene(){
        solids.add(new Axis());
        solids.get(0).modelMul(new Mat4Scale(3,3,3));
        solids.add(new SimpleCube());
        solids.add(new SimpleCube());
        solids.get(2).modelMul(new Mat4Scale(0.5, 0.5, 0.5));
        solids.get(2).modelMul(new Mat4Transl(1, -1, 0));
        solids.add(new SimplePyramid());
    }

    public void setPerspProjection() {
        projection = new Mat4PerspRH(Math.PI / 4, bufferedImage.getHeight() / (float) bufferedImage.getWidth(), 1, 200);
    }

    public void setOrthoProjection() {
        projection = new Mat4OrthoRH(bufferedImage.getWidth() / 180f, bufferedImage.getHeight() / 180f, 1, 200);
    }

    public void setDefaultCamera() {
        camera = new Camera(
                new Vec3D(0, -8, 4),
                Math.toRadians(90),
                Math.toRadians(-25),
                1, true
        );
    }

    public Mat4 getProjection() {
        return projection;
    }

    public Camera getCamera() {
        return camera;
    }

    public void repaint(){
        testVisibility.clear();
        for (Solid solid : solids) {
            if(solid.isVisible())
                renderer.render(solid, wireframe);
        }
        graphics.drawImage(bufferedImage, 0, 0, null);

        if(help) {
            renderHelp(graphics, getHelp(), 10, 5);
        }
    }

    /**
     * pravidelne prekreslovani sceny
     */
    private void startRefreshing() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                solids.get(1).modelMul(new Mat4RotZ(0.02));
                solids.get(1).modelMul(new Mat4RotX(0.02));
                repaint();
            }
        }, 0, 20);
    }

    public void setWireframe(){
        wireframe = !wireframe;
    }

    public void setVisibility(int index){
        solids.get(index).setVisible();
    }

    public void showHelp(){
        help = !help;
    }

    public void cameraMove(double azimuthDiff, double zenithDiff){
        double azimuth = camera.getAzimuth() + azimuthDiff;
        double zenith = camera.getZenith() + zenithDiff;

        camera = camera.withAzimuth(azimuth);
        camera = camera.withZenith(zenith);
    }

    public void cameraMove(int key) {
        switch (key) {
            case KeyEvent.VK_W:
                camera = camera.up(0.2);
                break;
            case KeyEvent.VK_S:
                camera = camera.down(0.2);
                break;
            case KeyEvent.VK_A:
                camera = camera.left(0.2);
                break;
            case KeyEvent.VK_D:
                camera = camera.right(0.2);
                break;
            case KeyEvent.VK_Q:
                camera = camera.forward(0.2);
                break;
            case KeyEvent.VK_E:
                camera = camera.backward(0.2);
                break;
        }
    }

    private String getHelp(){
        String output = "Ovládání kamery:\n[LMB] - rozhlížení\n[R] - vrátit kameru do původní pozice\n[W] - posun nahoru, [S] - posun dolů\n[A] - posun doleva, [D] - posun doprava\n" +
                "[E] - oddálení, [Q] - přiblížení\n\nNastavení scény:\n[F] - drátový model / vyplňování ploch\n" +
                "[P] - perspektivní projekce, [O] - pravoúhá projekce\n\nNastavení viditelnosti:\n" +
                "[1] - os, [2] - rotující krychle,\n[3] - zmenšené krychle, [4] - jehlan\n\n" +
                "Tomáš Brabec\nPočítačová grafika 2\nDatum odevzdání: 20.03.2019";

        return output;
    }

    void renderHelp(Graphics graphics, String help, int x, int y) {
        graphics.setColor(Color.WHITE);
        for (String line : help.split("\n"))
            graphics.drawString(line, x, y += graphics.getFontMetrics().getHeight());
    }
}
