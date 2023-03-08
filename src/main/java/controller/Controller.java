package controller;

public interface Controller {
    
    /**
     *  
     */
    // void registerAsObserver();
    // void notifyInput(...);

    /**
     * 
     */
    void notifyPressed(Command c);

    /**
     * Starts the Main Loop: 
     */
    void mainLoop();

    /**
     * Sets the running stete of the game loop.
     * 
     * @param isRunning
     */
    void setRunning(boolean isRunning);
}
