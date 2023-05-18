package dimhol.gamelevels.map;

import java.util.List;

public interface TileMap {

    /**
     *
     * @return
     */
    Tile getTile(int x, int y);

    /**
     * Returns the width of the tile map.
     * @return the width of the tile map.
     */
    int getWidth();

    /**
     * Returns the height of the tile map.
     * @return the height of the tile map.
     */
    int getHeight();

    /**
     * Return the width of a single tile in the tile map.
     * @return the width of a single tile in the tile map
     */
    int getTileWidth();

    /**
     * Return the height of a single tile in the tile map.
     * @return the height of a single tile in the tile map
     */
    int getTileHeight();

    /**
     *
     */
    List<Tile[][]> getLayers();
}
