package dimhol.gamelevels.map;

import java.util.List;

/**
 * The LoadMap interface represents a contract for classes that can load and provide information about a tile map.
 */
public interface MapLoader {



    /**
     *
     * @return map tile layers.
     */
    List<Tile[][]> getMapTileLayers();

    /**
     *
     * @return
     */

    TileMap loadNormalRoom();

    /**
     *
     * @return
     */
    TileMap loadShopRoom();

    /**
     *
     * @return
     */
    TileMap loadBossRoom();

    /**
     *
     * @return
     */
    TileMap loadCustomMap();

    /**
     * Returns the tile map data as a {@link TileMapImpl} object.
     * @return the tile map data
     */
    TileMap getTileMap();
}
