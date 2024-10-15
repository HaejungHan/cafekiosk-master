package org.example;


import org.example.controller.CafeController;
import org.example.view.CafeView;

public class App {
    public static void main(String[] args) {

        CafeView view = new CafeView();
        CafeController controller = new CafeController(view);
        view.setController(controller);

        view.setVisible(true);
    }
}

