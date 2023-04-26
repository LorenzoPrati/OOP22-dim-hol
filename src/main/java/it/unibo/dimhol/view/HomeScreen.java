package it.unibo.dimhol.view;

import it.unibo.dimhol.Engine;

import javax.swing.*;

public class HomeScreen extends JPanel {

    private final Engine engine;
    public HomeScreen(final Engine engine) {
        this.engine = engine;
        var b = new JButton("play");
        b.addActionListener(e -> {
            engine.newGame();
        });
        this.add(b);
        var b1 = new JButton("example");
        b1.addActionListener(e -> {
            engine.getWindow().changePanel(new ExamplePanelScreen(engine));
        });
        this.add(b1);
        var b2 = new JButton("exit");
        b2.addActionListener(e -> {
            System.exit(0);
        });
        this.add(b2);
        var b3 = new JButton("full screen for window OS");
        b3.addActionListener(e -> {
           engine.getWindow().makeFullScreen();
        });
        this.add(b3);
    }
}
