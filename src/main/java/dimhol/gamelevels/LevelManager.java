package dimhol.gamelevels;

import dimhol.entity.Entity;
import dimhol.gamelevels.map.TileMap;

import java.util.List;

/**
 * Generates a new level for the game, including the placement of the player and enemies.
 */
public interface LevelManager {

    /**
     * Changes the current level in the game and returns the updated list of entities.
     *
     * @param entities the list of entities in the current level.
     * the updated state of the game, including the list of entities.
     */
    List<Entity> changeLevel(List<Entity> entities);

    /**
     * Retrieves the tile map for the current level.
     *
     * @return the tile map for the current level.
     */
    TileMap getTileMap();
}
