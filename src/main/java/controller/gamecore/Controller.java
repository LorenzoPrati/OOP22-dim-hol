package controller.gamecore;

import controller.Engine;
import controller.gamecore.commands.Command;
import model.World;
/**
 * An interface to model the game controller.
 */
public interface Controller {

    /**
     * Notify if the command has been pressed.
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

    /**
     * Return the engine.
     * 
     * @return engine
     */
    Engine getEngine();
}
