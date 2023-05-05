package dimhol.map;

/**
 * The TileMap is a two-dimensional grid of tiles.
 */
public class TileMap {
    private Tile[][] tilemap;

    /**
     * Creates a new TileMap with the give tiles.
     * @param tilemap a 2D array of Tiles representing the map.
     */
    public  TileMap(final Tile[][] tilemap) {
        this.tilemap = tilemap;
    }

    /**
     * Gets the Tile at the given "x" and "y" coordinates.
     * @param x the x-coordinate of the Tile.
     * @param y the y-coordinate of the Tile.
     * @return the Tile at the specified coordinates.
     */
    public Tile getTile(final int x, final int y) {
        return tilemap[x][y];
    }
}
