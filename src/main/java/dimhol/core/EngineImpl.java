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

    private final MainWindow mainWindow;
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
     * Constructs an EngineImpl.
     */
    public EngineImpl() {
        this.mainWindow = new MainWindowImpl(this);
    }

    /**
     * Starts a new game.
     */
    @Override
    public void newGame() {
        this.world = new WorldImpl(this);
        this.world.changeLevel();
        this.running = true;
        this.pause = false;
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
    }

    /**
     * Resume the game.
     */
    @Override
    public void resumeGame() {
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
        this.mainWindow.changePanel(new ResultScreen(this, world.isWin()));
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
     * Gets the main mainWindow.
     *
     * @return the main mainWindow
     */
    @Override
    public MainWindow getMainWindow() {
        return mainWindow;
    }
}
