package dimhol.core;

import dimhol.view.*;

/**
 * Implementation of the Engine interface.
 */
public final class EngineImpl implements Engine {

    /**
     * Frame per seconds.
     */
    private static final int FPS = 60;
    /**
     * Milliseconds per frame.
     */
    private static final int MS_PER_FRAME = 1_000 / FPS;
    /**
     * Milliseconds to seconds.
     */
    private static final double MS_TO_SECOND = 0.001;

    /**
     * The main window of the game.
     */
    private final MainWindow mainWindow;
    /**
     * The world of the current match.
     */
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
     * True if tutorial done.
     */
    private boolean tutorialDone;

    /**
     * Constructs an EngineImpl.
     */
    public EngineImpl() {
        this.mainWindow = new MainWindowImpl(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void newGame() {
        if (!this.tutorialDone) {
            this.tutorialDone = true;
            this.mainWindow.changePanel(new TutorialScreen(this));
        } else {
            this.runGame();
        }
    }

    private void runGame() {
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
     * {@inheritDoc}
     */
    @Override
    public void pauseGame() {
        this.pause =false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resumeGame() {
        this.pause = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void endGame() {
        this.running = false;
    }

    /**
     *
     */
    private void gameLoop() {
        long prev = System.currentTimeMillis(); //time since previous loop
        double dt;
        while (this.running) {
            if (!this.world.isGameOver()) {
                long curr = System.currentTimeMillis();
                dt = curr - prev;
                if (!this.pause) {
                    this.world.update(dt * MS_TO_SECOND);
                }
                this.waitForNextFrame(curr);
                prev = curr;
            } else {
                this.handleEndGame();
                break;
            }
        }
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
     * {@inheritDoc}
     */
    @Override
    public MainWindow getMainWindow() {
        return mainWindow;
    }

    /**
     * Handles the end game screen transition.
     */
    private void handleEndGame() {
        this.mainWindow.changePanel(new ResultScreen(this, world.isWin()));
    }
}
