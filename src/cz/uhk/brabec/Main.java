package cz.uhk.brabec;

import cz.uhk.brabec.controllers.Controller;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Controller::new);
    }

}
