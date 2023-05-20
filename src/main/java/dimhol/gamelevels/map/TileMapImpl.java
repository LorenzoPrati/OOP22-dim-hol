package dimhol.gamelevels.map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The TileMap is a two-dimensional grid of tiles.
 */
public final class TileMapImpl implements TileMap {
    private final Tile[][] tilemap;
    private final int width;
    private final int height;
    private final int tileWidth;
    private final int tileHeight;
    private final List<Tile[][]> layers;

    /**
     * Creates a new TileMap with the given tiles.
     *
     * @param tilemap    A 2D array of Tiles representing the map.
     * @param width      The width of the map in tiles.
     * @param height     The height of the map in tiles.
     * @param tileWidth  The width of each tile in pixels.
     * @param tileHeight The height of each tile in pixels.
     */
    public TileMapImpl(final List<Tile[][]> tilemap, final int width, final int height,
                       final int tileWidth, final int tileHeight) {
        this.tilemap = tilemap.get(0);
        this.width = width;
        this.height = height;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.layers = new ArrayList<>(tilemap);
    }

    /**
     * Gets the Tile at the given coordinates.
     *
     * @param x The x-coordinate of the Tile.
     * @param y The y-coordinate of the Tile.
     * @return The Tile at the specified coordinates.
     */
    public Tile getTile(final int x, final int y) {
        return tilemap[x][y];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getWidth() {
        return width;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public int getHeight() {
        return height;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public int getTileWidth() {
        return tileWidth;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public int getTileHeight() {
        return tileHeight;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public List<Tile[][]> getLayers() {
        return Collections.unmodifiableList(this.layers);
    }

    /**
     * Sets the new tile map for the TileMap instance.
     * This method allows replacing the entire map with a new one.
     *
     * @param newMap the new TileMap to set.
     */
    public void setTileMap(final TileMapImpl newMap) {
        // TODO: Implementation of setTileMap method will be added here
    }
}
