package controller.gamecore;

import controller.gamecore.commands.Command;
import model.World;

/**
 * An interface to model the game controller.
 */

public interface Controller {
    
    /**
     *  To be added.
     */
    //TODO: void registerAsObserver();
    //TODO: void notifyInput(...);

    /**
     * 
     * @param command
     */
    void notifyPressed(Command command);

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

    /**
     * Return the world.
     * 
     * @return world
     */
    World getWorld();
}
