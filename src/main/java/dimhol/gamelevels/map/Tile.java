package dimhol.gamelevels.map;

public interface Tile {
    /**
     * Returns whether this Tile is collidable.
     *
     * @return true if this Tile si collidable, false otherwise.
     */
    boolean isWalkable();

    /**
     * Returns the tile set ID.
     *
     * @return tileSetID the number of the identifier.
     */
    int getTileSetId();
}
