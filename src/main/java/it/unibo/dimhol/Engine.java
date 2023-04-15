package it.unibo.dimhol;

import it.unibo.dimhol.view.ExamplePanelScreen;
import it.unibo.dimhol.view.MainWindow;
import it.unibo.dimhol.view.PauseExampleScreen;
import it.unibo.dimhol.view.Scene;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Engine {

    private static final long PERIOD = 20;
    private MainWindow window;
    private World world;

    private Thread game;

    public Engine() {
        window = new MainWindow(Engine.this);
    }

    public void startGame() {
        this.world = new World(this);
        this.resumeGame();
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

    public void stopGame() {
        this.game.interrupt();
        this.window.changePanel(new PauseExampleScreen(this));
    }

    public void resumeGame() {
        this.window.changePanel(this.world.getScene());
        /**
         * input is set after the scene is made visible
         */
        this.world.getScene().setInput(this.world.getInput());
        this.game = new Thread(new Runnable() {
            @Override
            public void run() {
                gameLoop();
            }
        });
        this.game.start();
    }
}
