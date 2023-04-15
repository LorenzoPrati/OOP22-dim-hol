package it.unibo.dimhol;

import it.unibo.dimhol.view.MainWindow;
import it.unibo.dimhol.view.Scene;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Engine {

    private static final long PERIOD = 20;
    private MainWindow window;
    private World world;

    public Engine() {
        window = new MainWindow(Engine.this);
    }

    public void startGame() {
        this.world = new World();
        this.window.changePanel(this.world.getScene());
        /**
         * input is set after the scene is made visible
         */
        this.world.getScene().setInput(this.world.getInput());
        new Thread(new Runnable() {
            @Override
            public void run() {
                gameLoop();
            }
        }).start();
    }

    public MainWindow getWindow() {
        return this.window;
    }

    public void gameLoop() {
        long prevTime = System.currentTimeMillis();
        while(true) {
            long currTime = System.currentTimeMillis();
            long dt = currTime - prevTime;
            this.world.update(dt);
            this.window.repaint();
            this.waitForNextFrame(currTime);
            prevTime = currTime;
        }
    }

    private void waitForNextFrame(long startTime) {
        long dt = System.currentTimeMillis() - startTime;
        if (dt < PERIOD) {
            try {
                Thread.sleep(PERIOD - dt);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
