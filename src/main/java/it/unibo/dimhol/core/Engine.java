package it.unibo.dimhol.core;

import it.unibo.dimhol.view.*;

/**
 * Engine class.
 */
public final class Engine {
    /*
    Game loop settings
     */
    private static final int FPS = 60;
    private static final int MS_PER_FRAME = 1_000 / FPS;

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
    /**
     * True if in debug mode.
     */
    private boolean debug = true;

    /**
     * Constructs an Engine.
     */
    public Engine() {
        this.window = new MainWindow(this);
        System.setProperty("sun.java2d.opengl", "true");
    }

    /**
     * Starts a new game.
     */
    public void newGame() {
        this.world = new WorldImpl();
        //this.world.setInputListener(new InputListener(this));
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
        this.world.getScene().setInput(new InputListener(this, world.getInput()));
        this.pause = false;
    }

    /**
     * Terminates the game loop.
     */
    public void endGame() {
        this.running = false;
    }

    private void gameLoop() {
        long prev = System.currentTimeMillis(); //time since previous loop
        double dt;
        while (this.running && !world.isGameOver()) {
            long curr = System.currentTimeMillis();
            dt = curr - prev;
            if (!this.pause) {
                this.world.update(dt/1000);
            }
            this.waitForNextFrame(curr);
            prev = curr;
        }
        this.window.changePanel(new ResultScreen(this, world.getResult()));
    }

    private void waitForNextFrame(final long time) {
        long dt = System.currentTimeMillis() - time;
        if (dt < MS_PER_FRAME) {
            try {
                Thread.sleep(MS_PER_FRAME - dt);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Gets the main window.
     *
     * @return the main window
     */
    public MainWindow getWindow() {
        return window;
    }

    /**
     * Gets the debug mode state.
     *
     * @return true if the debug mode is enabled, false otherwise.
     */
    public boolean isDebugMode() {
        return this.debug;
    }

    /**
     * Enable or disable the debug mode.
     *
     * @param debug the flag to enable/disable the debug mode.
     *              True to enable it, false otherwise.
     */
    public void setDebugMode(final boolean debug) {
        this.debug = debug;
    }
}
