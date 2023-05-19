package dimhol.gamelevels;

import dimhol.gamelevels.map.MapLoader;
import dimhol.gamelevels.map.TileMapImpl;

public interface MapGenerationStrategy {
    /**
     * Generates a new tile map based on the strategy.
     *
     * @param mapLoader The map loader for loading tile maps.
     * @return The newly generated tile map.
     */
    TileMapImpl generateMap(MapLoader mapLoader);
}

