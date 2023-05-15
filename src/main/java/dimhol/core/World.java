package dimhol.core;

import dimhol.entity.Entity;
import dimhol.map.MapLoaderImpl;
import dimhol.view.Scene;

import java.util.List;

/**
 * Models the world where the entities exist.
 */
public interface World {

    /**
     * Gets called each game loop. Updates the world.
     */
    void update(double dt);

    /**
     * Gets the entities currently alive in the world.
     *
     * @return the list of world entities
     */
    List<Entity> getEntities();

    /**
     * Adds an entity to the world.
     *
     * @param e the entity to add
     */
    void addEntity(Entity e);

    /**
     * Removes an entity from the world.
     *
     * @param e the entity to remove
     */
    void removeEntity(Entity e);

    /**
     * Gets the input.
     *
     * @return the world input.
     */
    Input getInput();

    /**
     * Gets the view representation of the world.
     *
     * @return the world scene.
     */
    Scene getScene();

    /**
     * Gets the logical representation of the map.
     *
     * @return the logical map.
     */
    MapLoaderImpl getMapLoader();

    /**
     * Checks if the game is over.
     *
     * @return true if the game is complete or the player dies,
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
     * @return true if player defeated the boss, false otherwise.
     */
    boolean getResult();
}
