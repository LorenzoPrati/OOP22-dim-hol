package dimhol.core;

import dimhol.view.MainWindow;

/**
 * The interface of the engine. It provides the methods to control
 * the game loop and access the game view window.
 */
public interface Engine {

    /**
     * Starts a new match.
     */
    void newGame();

    /**
     * Pauses the current match.
     */
    void pauseGame();

    /**
     * Resumes the current match if paused.
     */
    void resumeGame();

    /**
     * Ends the current match.
     */
    void endGame();

    /**
     * Gets the main window of the game.
     *
     * @return the main window
     */
    MainWindow getMainWindow();

    /**
     * Sets the debug mode.
     *
     * @param debug true if debug mode needs to be enabled
     */
    void setDebugMode(boolean debug);

    /**
     * Checks if the debug mode is enabled.
     *
     * @return true if the debug mode is enabled
     */
    boolean isDebug();
}
