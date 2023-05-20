package dimhol.gamelevels;

import dimhol.gamelevels.map.MapLoader;
import dimhol.gamelevels.map.TileMapImpl;

/**
 * The interface for defining a map generation strategy.
 */
public interface MapGenerationStrategy {
    /**
     * Generates a new tile map based on the strategy.
     *
     * @param mapLoader The map loader for loading tile maps.
     * @return The newly generated tile map.
     */
    TileMapImpl generateMap(MapLoader mapLoader);
}

