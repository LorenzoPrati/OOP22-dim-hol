package dimhol.gamelevels.map;

import java.util.List;

/**
 * The LoadMap interface represents a contract for classes that can load and provide information about a tile map.
 */
public interface MapLoader {



    /**
     * Returns the tile layers of the map.
     *
     * @return the map tile layers
     */
    List<Tile[][]> getMapTileLayers();

    /**
     * Loads a normal room tile map.
     *
     * @return the loaded normal room tile map
     */
    TileMap loadNormalRoom();

    /**
     * Loads a normal room tile map.
     *
     * @return the loaded normal room tile map
     */
    TileMap loadShopRoom();

    /**
     * Loads a boss room tile map.
     *
     * @return the loaded boss room tile map
     */
    TileMap loadBossRoom();

    /**
     * Loads a custom map tile map.
     *
     * @return the loaded custom map tile map
     */
    TileMap loadCustomMap();


    /**
     * Returns the tile map data as a {@link TileMapImpl} object.
     *
     * @return the tile map data
     */
    TileMap getTileMap();
}
