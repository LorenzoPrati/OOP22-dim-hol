package dimhol.gamelevels.map;

import java.util.List;

/**
 * The TileMap interface represents a map made up of tiles.
 */
public interface TileMap {

    /**
     * Returns the tile at the specified coordinates.
     *
     * @param x the x-coordinate of the tile
     * @param y the y-coordinate of the tile
     * @return the tile at the specified coordinates
     */
    Tile getTile(int x, int y);

    /**
     * Sets the tile at the specified coordinates.
     *
     * @param x    the x-coordinate of the tile
     * @param y    the y-coordinate of the tile
     * @param tile the tile to be set at the specified coordinates
     */
    void setTile(int x, int y, Tile tile);

    /**
     * Returns the width of the tile map.
     *
     * @return the width of the tile map.
     */
    int getWidth();

    /**
     * Returns the height of the tile map.
     *
     * @return the height of the tile map.
     */
    int getHeight();

    /**
     * Return the width of a single tile in the tile map.
     *
     * @return the width of a single tile in the tile map.
     */
    int getTileWidth();

    /**
     * Return the height of a single tile in the tile map.
     *
     * @return the height of a single tile in the tile map.
     */
    int getTileHeight();

    /**
     * Returns the layers of the tile map.
     *
     * @return The layers of the tile map.
     */
    List<Tile[][]> getLayers();

    /**
     * Checks if the given coordinates are valid within the map.
     *
     * @param x the x-coordinate to check
     * @param y the y-coordinate to check
     * @return true if the coordinates are valid, false otherwise
     */
    boolean isValidCoordinate(int x, int y);
}
