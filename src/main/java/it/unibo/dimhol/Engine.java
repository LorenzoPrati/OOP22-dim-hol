package it.unibo.dimhol;

import it.unibo.dimhol.view.*;

/**
 * Engine class.
 */
public class Engine {

    private static final long PERIOD = 20;
    private MainWindow window;
    private World world;
    private Thread game;
    private boolean pause;

    public Engine() {
        window = new MainWindow(Engine.this);
    }

    public void startGame() {
        this.world = new World(this);
        this.resumeGame();
        this.game = new Thread(new Runnable() {
            @Override
            public void run() {
                gameLoop();
            }
        });
        this.game.start();
    }

    public MainWindow getWindow() {
        return this.window;
    }

    public void gameLoop() {
        long prevTime = System.currentTimeMillis();
        while(!world.isGameOver()) {
            long currTime = System.currentTimeMillis();
            long dt = currTime - prevTime;
            if (!this.pause) {
                this.world.update(dt);
                this.window.render();
            }
            this.waitForNextFrame(currTime);
            prevTime = currTime;
        }
        this.window.changePanel(new ResultScreen(this, world.getResult()));
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
        this.pause = true;
        this.window.changePanel(new PauseExampleScreen(this));
    }

    public void resumeGame() {
        this.window.changePanel(this.world.getScene());
        /*
         * input is set after the scene is made visible
         */
        this.world.getScene().setInput(this.world.getInput());
        this.pause = false;
    }

}
