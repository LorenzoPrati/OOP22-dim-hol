package controller.game_engine;

/**
 * Engine is the manager of the game scenes, it can change scene and 
 * handle the game loop.
 */
public interface Engine {
    
    /**
     * Gets notified the game loop need to be started.
     */
    void notifyStart();

    /**
     * Gets notified the game loop need to be pasued.
     */
    void notifyPause();

    /**
     * Gets notified the game loop need to be restarted.
     */
    void notifyResume();

    /**
     * Gets notified to show the commands tutorial.
     */
    void notifyTutorial();

    /**
     * Gets notified the game has ended.
     * @param result
     */
    void notifyGameEnd(Boolean result);

    /**
     * Gets notified to return to the home screen.
     */
    void notifyHome();

    /**
     * Gets notified to show the loading scene.
     */
    void loadScene();


}
