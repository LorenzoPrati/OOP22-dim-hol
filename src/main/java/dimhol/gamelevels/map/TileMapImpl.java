package dimhol.gamelevels.map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a two-dimensional grid of tiles in a game map.
 */
public final class TileMapImpl implements TileMap {
    private final Tile[][] tilemap;
    private final int width;
    private final int height;
    private final int tileWidth;
    private final int tileHeight;
    private final List<Tile[][]> layers;

    /**
     * Creates a new TileMap with the given layers, width, height, tileWidth, and tileHeight.
     *
     * @param layers     A list of 2D arrays representing the map layers.
     * @param width      The width of the map in tiles.
     * @param height     The height of the map in tiles.
     * @param tileWidth  The width of each tile in pixels.
     * @param tileHeight The height of each tile in pixels.
     */
    public TileMapImpl(final List<Tile[][]> layers, final int width, final int height,
                       final int tileWidth, final int tileHeight) {
        this.layers = new ArrayList<>(layers);
        this.width = width;
        this.height = height;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.tilemap = layers.get(0);
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
    public void setTile(final int x, final int y, final Tile tile) {
        if (!isValidCoordinate(x, y)) {
            throw new IllegalArgumentException("Invalid coordinates: (" + x + ", " + y + ")");
        }
        tilemap[x][y] = tile;
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
        return Collections.unmodifiableList(layers);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValidCoordinate(final int x, final int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }
}
