package it.unibo.dimhol.map;

/**
 * A Tile represents a single tile in a game map, with information about whether it is collidable.
 */
public class Tile {
    /**
     * Whether this Tile is collidable.
     */
    private final boolean collidable;

    /**
     * Constructs a new Tile with the specified collidability.
     *
     * @param tileSetIdInt
     * @param collidable   whether this Tile should be collidable.
     */
    public Tile(int tileSetIdInt, final boolean collidable) {
        this.collidable = collidable;
    }

    /**
     * Returns whether this Tile is collidable.
     *
     * @return true if this Tile si collidable, false otherwise.
     */
    public boolean isCollidable() {
        return collidable;
    }
}