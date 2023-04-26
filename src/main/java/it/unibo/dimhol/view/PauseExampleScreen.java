package it.unibo.dimhol.view;

import it.unibo.dimhol.Engine;

import javax.swing.*;
import java.awt.*;

public class PauseExampleScreen extends JPanel {
    private final Engine engine;
    public PauseExampleScreen(final Engine engine) {
        this.engine = engine;
        var label = new JLabel("pause screen");
        this.add(label);
        var b = new JButton("resume");
        b.addActionListener(e -> {
            engine.resumeGame();
        });
        this.add(b);
        var b1 = new JButton("home");
        b1.addActionListener(e -> {
            engine.endGame();
            engine.getWindow().changePanel(new HomeScreen(engine));
        });
        this.add(b1);
    }
}
