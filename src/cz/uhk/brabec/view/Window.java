package cz.uhk.brabec.view;

import javax.swing.*;
import java.awt.*;

public class Window {

    private final JFrame frame;
    private final JPanel panel;

    public Window(int width, int height, String title) {
        frame = new JFrame(title);
        panel = new JPanel();
        panel.setPreferredSize(new Dimension(width, height));
        frame.add(panel);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        panel.requestFocus();
    }

    public JPanel getPanel() {
        return panel;
    }

}
