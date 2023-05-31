package dimhol.core;

import dimhol.entity.Entity;
import dimhol.events.WorldEvent;
import dimhol.gamelevels.LevelManager;
import dimhol.input.Input;

import java.util.List;

/**
 * Models the world where the entities exist.
 */
public interface World {

    /**
     * Gets called each game loop. Updates the world.
     *
     * @param deltaTime the time elapsed time
     */
    void update(double deltaTime);

    /**
     * Gets the entities currently alive in the world.
     *
     * @return the list of world entities
     */
    List<Entity> getEntities();

    /**
     * Notifies the World that an event is occurred.
     *
     * @param event the event that occurred
     */
    void notifyEvent(WorldEvent event);

    /**
     * Adds an entity to the world.
     *
     * @param entity the entity to add
     */
    void addEntity(Entity entity);

    /**
     * Removes an entity from the world.
     *
     * @param entity the entity to remove
     */
    void removeEntity(Entity entity);


    /**
     * Handles the level change.
     */
    void changeLevel();

    /**
     * Gets the level manager.
     *
     * @return the level manager.
     */
    LevelManager getLevelManager();

    /**
     * Sets if the player has won.
     *
     * @param win the match result. True if player defeated the boss.
     */
    void setWin(boolean win);

    /**
     * Checks if the match ended with a win.
     *
     * @return true if the match ended with a win
     */
    boolean isWin();

    /**
     * Checks if the game is over.
     *
     * @return true if the game is complete or the player is dead,
     * false otherwise
     */
    boolean isGameOver();

    /**
     * Gets the input.
     *
     * @return the world input
     */
    Input getInput();
}
