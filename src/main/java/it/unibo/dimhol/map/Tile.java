package it.unibo.dimhol.map;

/**
 * A Tile represents a single tile in a game map, with information about whether it is collidable.
 */
public class Tile {
    /**
     * Whether this Tile is collidable.
     */
    private final boolean walkable;
    private final int tileSetId;

    /**
     * Constructs a new Tile with the specified collidability.
     *
     * @param tileSetId
     * @param walkable   whether this Tile should be collidable.
     */
    public Tile(final int tileSetId, final boolean walkable) {
        this.tileSetId = tileSetId;
        this.walkable = walkable;
    }

    /**
     * Returns whether this Tile is collidable.
     *
     * @return true if this Tile si collidable, false otherwise.
     */
    public boolean isWalkable() {
        return walkable;
    }
    public int getTileSetId() {
        return tileSetId;
    }

    public double getX(double x) {
        return x;
    }

    public double getY(double y) {
        return y;
    }
}