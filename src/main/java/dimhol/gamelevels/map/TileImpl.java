package dimhol.gamelevels.map;

/**
 * A Tile represents a single tile in a game map, with information about whether it is collidable.
 */
public class TileImpl implements Tile {
    /**
     * Whether this Tile is collidable.
     */
    private final boolean walkable;
    private final int tileSetId;

    /**
     * Constructs a new Tile with the specified collidability.
     *
     * @param tileSetId The ID of the tile set.
     * @param walkable  Whether this Tile should be collidable.
     */
    public TileImpl(final int tileSetId, final boolean walkable) {
        this.tileSetId = tileSetId;
        this.walkable = walkable;
    }

    /**
     * Returns whether this Tile is collidable.
     *
     * @return true if this Tile si collidable, false otherwise.
     */
    @Override
    public boolean isWalkableTile() {
        return walkable;
    }

    /**
     * Returns the tile set ID.
     *
     * @return tileSetID the number of the identifier.
     */
    @Override
    public int getTileSetId() {
        return tileSetId;
    }
}
