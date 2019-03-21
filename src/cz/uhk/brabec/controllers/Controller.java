package cz.uhk.brabec.controllers;

import cz.uhk.brabec.view.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

/**
 * inicializace potrebnych casti a odchytavani uzivatelskeho vstupu
 */
public class Controller {

    /**
     * Okno s panelem, do ktereho se vykresluje
     */
    private final Window window;
    /**
     * obraz vykreslovany na panel
     */
    private final BufferedImage bufferedImage;
    private final SceneController sceneController;
    private final Graphics graphics;

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private Point point;

    public Controller() {
        window = new Window(WIDTH, HEIGHT, "Graphics | [H] - nápověda");
        graphics = window.getPanel().getGraphics();
        bufferedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        sceneController = new SceneController(bufferedImage, graphics);
        setControls();
    }

    private void setControls() {
        window.getPanel().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                point = e.getPoint();
            }
        });

        window.getPanel().addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    double azimuthDiff = (point.x - e.getX()) / 5000.0;
                    double zenithDiff = (point.y - e.getY()) / 5000.0;
                    sceneController.cameraMove(azimuthDiff, zenithDiff);
                }
            }
        });

        window.getPanel().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W:
                        sceneController.cameraMove(e.getKeyCode());
                        break;
                    case KeyEvent.VK_S:
                        sceneController.cameraMove(e.getKeyCode());
                        break;
                    case KeyEvent.VK_A:
                        sceneController.cameraMove(e.getKeyCode());
                        break;
                    case KeyEvent.VK_D:
                        sceneController.cameraMove(e.getKeyCode());
                        break;
                    case KeyEvent.VK_Q:
                        sceneController.cameraMove(e.getKeyCode());
                        break;
                    case KeyEvent.VK_E:
                        sceneController.cameraMove(e.getKeyCode());
                        break;
                    case KeyEvent.VK_F:
                        sceneController.setWireframe();
                        break;
                    case KeyEvent.VK_P:
                        sceneController.setPerspProjection();
                        break;
                    case KeyEvent.VK_O:
                        sceneController.setOrthoProjection();
                        break;
                    case KeyEvent.VK_R:
                        sceneController.setDefaultCamera();
                        break;
                    case KeyEvent.VK_H:
                        sceneController.showHelp();
                        break;
                    case KeyEvent.VK_NUMPAD1:
                    case KeyEvent.VK_1:
                        sceneController.setVisibility(0);
                        break;
                    case KeyEvent.VK_NUMPAD2:
                    case KeyEvent.VK_2:
                        sceneController.setVisibility(1);
                        break;
                    case KeyEvent.VK_NUMPAD3:
                    case KeyEvent.VK_3:
                        sceneController.setVisibility(2);
                        break;
                    case KeyEvent.VK_NUMPAD4:
                    case KeyEvent.VK_4:
                        sceneController.setVisibility(3);
                        break;
                }
            }
        });
    }

}
