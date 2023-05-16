package dimhol.map;

import java.util.List;

/**
 * The LoadMap interface represents a contract for classes that can load and provide information about a tile map.
 */
public interface MapLoader {
    /**
     * Returns the width of the tile map.
     * @return the width of the tile map
     */
    int getWidth();

    /**
     * Returns the height of the tile map.
     * @return the height of the tile map
     */
    int getHeight();

    /**
     * Return the width of a single tile in the tile map.
     * @return the width of a single tile in the tile map
     */
    int getTileWidth();

    /**
     * Return the height of a single tile in the tile map.
     * @return the height of a single tile in the tile map
     */
    int getTileHeight();

    /**
     * Returns the tile map data as a {@link TileMap} object.
     * @return the tile map data
     */
    TileMap getTileMap();

    /**
     *
     * @return map tile layers.
     */
    List<Tile[][]> getMapTileLayers();
}