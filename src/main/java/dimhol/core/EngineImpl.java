package dimhol.core;

import dimhol.view.*;

/**
 * EngineImpl class.
 */
public final class EngineImpl implements Engine {
    /*
    Game loop settings
     */
    private static final int FPS = 60;
    private static final int MS_PER_FRAME = 1_000 / FPS;

    private final MainWindow window;
    private World world;
    private Scene scene;
    /**
     * True if the game needs to be paused.
     */
    private boolean pause;
    /**
     * True if the game loop needs to run.
     */
    private boolean running;

    /**
     * Constructs an EngineImpl.
     */
    public EngineImpl() {
        this.window = new MainWindowImpl(this);
    }

    /**
     * Starts a new game.
     */
    @Override
    public void newGame() {
        this.scene = new SceneImpl();
        this.world = new WorldImpl(window, this);
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
    @Override
    public void stopGame() {
        this.pause = true;
        this.window.changePanel(new PauseScreen(this));
    }

    /**
     * Resume the game.
     */
    @Override
    public void resumeGame() {
        this.window.changePanel(this.scene.getPanel());
        /*
         * input is set after the scene is made visible
         */
        //this.world.getScene().setInput(new InputListener(this, world.getInput()));
        this.pause = false;
    }

    /**
     * Terminates the game loop.
     */
    @Override
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
        this.window.changePanel(new ResultScreen(this, world.isWin()));
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
    @Override
    public MainWindow getWindow() {
        return window;
    }
}
