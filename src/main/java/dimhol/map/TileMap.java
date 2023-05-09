package dimhol.map;

import java.util.stream.IntStream;

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
        int width = tilemap.length;
        int height = tilemap[0].length;
        Tile[][] copy = new Tile[width][height];
        IntStream.range(0, width).forEach(i -> System.arraycopy(tilemap[i], 0, copy[i], 0, height));
        this.tilemap = copy;
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
