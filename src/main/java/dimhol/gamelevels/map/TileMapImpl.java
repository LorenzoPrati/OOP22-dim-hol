package dimhol.gamelevels.map;

import java.util.List;

/**
 * The TileMap is a two-dimensional grid of tiles.
 */
public class TileMapImpl implements TileMap {
    private final Tile[][] tilemap;
    private final int width;
    private final int height;
    private final int tileWidth;
    private final int tileHeight;
    private final List<Tile[][]> layers;

    /**
     * Creates a new TileMap with the give tiles.
     * @param tilemap a 2D array of Tiles representing the map.
     */
    public TileMapImpl(final List<Tile[][]> tilemap, int width, int height, int tileWidth, int tileHeight) {
        this.tilemap = tilemap.get(0);
        this.width = width;
        this.height = height;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.layers = tilemap;
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

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getTileWidth() {
        return tileWidth;
    }

    @Override
    public int getTileHeight() {
        return tileHeight;
    }

    @Override
    public List<Tile[][]> getLayers() {
        return this.layers;
    }

    public void setTileMap(TileMapImpl newMap) {

    }
}
