package it.unibo.dimhol;

import it.unibo.dimhol.view.*;

/**
 * Engine class.
 */
public final class Engine {
    /*
    Game loop settings
     */
    private static final double FRAMES_PER_SECOND = 60;
    private static double SECOND_TO_NANOSECOND = 1_000_000_000;
    private static double NANO_SECONDS_PER_FRAME = SECOND_TO_NANOSECOND / FRAMES_PER_SECOND;
    private static double SECOND_TO_MILLISECOND = 1_000;

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
    private boolean debug;

    /**
     * Constructs an Engine.
     */
    public Engine() {
        this.window = new MainWindow(this);
        //System.setProperty("sun.java2d.opengl", "true");
    }

    /**
     * Starts a new game.
     */
    public void newGame() {
        this.world = new World();
        this.world.setInputListener(new InputListener(this));
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
        this.world.getScene().setInput(this.world.getInputListener());
        this.pause = false;
    }

    /**
     * Terminates the game loop.
     */
    public void endGame() {
        this.running = false;
    }

    private void gameLoop() {
        long prev = System.nanoTime(); //time since previous loop
        int frames = 0;
        double dt = 0;
        double time = System.currentTimeMillis();
        while (this.running) {
            long now = System.nanoTime();
            dt += (now - prev) / NANO_SECONDS_PER_FRAME;
            prev = now;
            if (dt >= 1) {
                if (!this.pause) {
                    this.world.update();
                    this.window.render();
                }
                frames++;
                dt--;
                /*
                Display fps (if in debug mode)
                 */
                if(System.currentTimeMillis() - time >= 1 * SECOND_TO_MILLISECOND) {
                    if (this.debug) {
                        System.out.println("FPS=" + frames);
                    }
                    time += 1 * SECOND_TO_MILLISECOND;
                    frames = 0;
                }
            }
        }
    }

    //private void showMatchResult() {
        //this.window.changePanel(new ResultScreen(this, world.getResult()));
    //}


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
