package it.unibo.dimhol.view;

import it.unibo.dimhol.Engine;

import javax.swing.*;

public class HomeScreen extends JPanel {

    private final Engine engine;
    public HomeScreen(final Engine engine) {
        this.engine = engine;
        var b = new JButton("play");
        b.addActionListener(e -> {
            engine.startGame();
        });
        this.add(b);
        var b1 = new JButton("example");
        b1.addActionListener(e -> {
            engine.getWindow().changePanel(new ExamplePanelScreen(engine));
        });
        this.add(b1);
    }
}
