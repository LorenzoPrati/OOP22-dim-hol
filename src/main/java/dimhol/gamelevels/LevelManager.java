package dimhol.gamelevels;

import dimhol.gamelevels.map.TileMap;

/**
 * The LevelGenerator class generates a new level for the game, including the placement of the player and enemies.
 */
public interface LevelManager {
    /**
     * Changes the current level in the game.
     */
    void changeLevel();

    /**
     * Retrieves the tile map for the current level.
     *
     * @return the tile map for the current level
     */
     TileMap getTileMap();
}
