package dimhol.gamelevels.map;

/**
 * Represents a tile in the game map.
 * Tiles are used to build the game world and provide information
 * about their properties such as walkability and tile set ID.
 */
public interface Tile {
    /**
     * Returns whether this Tile is walkable.
     *
     * @return true if this Tile is walkable, false otherwise.
     */
    boolean isWalkableTile();

    /**
     * Returns the tile set ID.
     *
     * @return The number identifier of the tile.
     */
    int getTileSetId();
}
