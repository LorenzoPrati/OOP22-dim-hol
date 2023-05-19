package dimhol.core;

import dimhol.entity.Entity;
import dimhol.events.WorldEvent;
import dimhol.gamelevels.LevelManager;
import dimhol.view.Scene;

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
     * Gets the input.
     *
     * @return the world input
     */
    Input getInput();

    /**
     * Gets the view representation of the world.
     *
     * @return the world scene
     */
    Scene getScene();

    /**
     * Checks if the game is over.
     *
     * @return true if the game is complete or the player is dead,
     * false otherwise
     */
    boolean isGameOver();

    /**
     * Sets that the game is over.
     */
    void setGameOver();

    /**
     * Checks match result.
     *
     * @return true if the player completed the game,
     * false otherwise
     */
    boolean isWin();

    /**
     * Notifies the World that an event is occurred.
     *
     * @param event the event that occurred
     */
    void notifyEvent(WorldEvent event);

    LevelManager getLevelManager();

    void changeLevel();
}
