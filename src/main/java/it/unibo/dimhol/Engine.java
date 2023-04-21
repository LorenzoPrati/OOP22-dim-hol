package it.unibo.dimhol;

import it.unibo.dimhol.view.*;

/**
 * Engine class.
 */
public final class Engine {

    private static final long PERIOD = 20;
    private final MainWindow window;
    private World world;
    /**
     * True if the game needs to be paused.
     */
    private boolean pause;
    /**
     * True if the game loop needs to run.
     */
    private boolean running;

    public Engine() {
        this.window = new MainWindow(this);
    }

    /**
     * Starts a new game.
     */
    public void newGame() {
        this.world = new World();
        this.world.setInput(new InputListener(this));
        this.resumeGame();
        this.running = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                gameLoop();
            }
        }).start();
    }

    /**
     * Pause the game.
     */
    public void stopGame() {
        this.pause = true;
        this.window.changePanel(new PauseExampleScreen(this));
    }

    /**
     * Resume the game.
     */
    public void resumeGame() {
        this.window.changePanel(this.world.getScene());
        /*
         * input is set after the scene is made visible
         */
        this.world.getScene().setInput(this.world.getInput());
        this.pause = false;
    }

    /**
     * Terminates the game loop.
     */
    public void endGame() {
        this.running = false;
    }

    private void gameLoop() {
        long prevTime = System.currentTimeMillis();
        while(!this.world.isGameOver() && this.running) {
            long currTime = System.currentTimeMillis();
            long dt = currTime - prevTime;
            if (!this.pause) {
                this.world.update(dt);
                this.window.render();
            }
            this.waitForNextFrame(currTime);
            prevTime = currTime;
        }
        this.showMatchResult();
    }

    private void showMatchResult() {
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

    public MainWindow getWindow() {
        return window;
    }

}
