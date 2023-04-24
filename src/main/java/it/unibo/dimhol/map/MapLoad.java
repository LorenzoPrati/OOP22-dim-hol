package it.unibo.dimhol.map;

/**
 * The LoadMap interface represents a contract for classes that can load and provide information about a tile map.
 */
public interface MapLoad {
    /**
     * Returns the 2D arrays of integers that represents the tile map.
     * @return the tile map ad a 2D array of integers
     */
    Tile[][] getMap();

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
}
