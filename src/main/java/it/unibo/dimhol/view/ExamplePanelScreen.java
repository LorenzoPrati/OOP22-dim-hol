package it.unibo.dimhol.view;

import it.unibo.dimhol.Engine;

import javax.swing.*;

public class ExamplePanelScreen extends JPanel {
    private final Engine engine;
    public ExamplePanelScreen(final Engine engine) {
        this.engine = engine;
        var label = new JLabel("example");
        this.add(label);
        var b = new JButton("return to home");
        b.addActionListener(e -> {
            engine.getWindow().changePanel(new HomeScreen(engine));
        });
        this.add(b);
    }
}
