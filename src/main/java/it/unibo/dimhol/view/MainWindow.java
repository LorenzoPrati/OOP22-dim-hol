package it.unibo.dimhol.view;

import it.unibo.dimhol.Engine;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    private final Engine engine;
    JPanel currPanel;
    public MainWindow(final Engine engine) {
        this.engine = engine;
        this.currPanel = new HomeScreen(engine);
        this.getContentPane().add(currPanel);
        this.setPreferredSize(new Dimension(900, 900));
        this.pack();
        this.setVisible(true);
        this.setResizable(false);
        this.setFocusable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void changePanel(JPanel panel) {
        this.remove(currPanel);
        this.add(panel);
        this.currPanel = panel;
        this.repaint();
        this.revalidate();
    }

    public void render() {
        this.repaint();
        Toolkit.getDefaultToolkit().sync();
    }
}
