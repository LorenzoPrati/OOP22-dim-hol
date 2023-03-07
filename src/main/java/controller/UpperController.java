package controller;

/**
 * An interface to model the engine of the game
 */
public interface UpperController {


    /**
     * notify to show the 
     */
    void notifyStartGame();


    /**
     * 
     */
    void notifyPauseGame();

    /**
     * 
     */
    void notifyResumeGame();

    /**
     * 
     */
    void notifyExitGame();

    /**
     * 
     */
    void closeGame();

    /**
     * 
     */
    void notifyTutorialScreen();

    /**
     * @param result
     */
    void notifyFinishedGame(Boolean result);

    /**
     * 
     */
    void notifyHomeScreen();

}
