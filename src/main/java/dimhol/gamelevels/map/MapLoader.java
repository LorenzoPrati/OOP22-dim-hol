package dimhol.gamelevels.map;

import java.io.InputStream;
import java.util.List;

/**
 * Represents a contract for classes that can load and provide information about a tile map.
 */
public interface MapLoader {

    /**
     * Retrieves the tile layers of the map.
     *
     * @return the map tile layers as a list of 2D arrays of Tile objects.
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
     * Loads a room map from the given input stream.
     *
     * @param inputStream The input stream of the XML file representing the room map.
     * @return The loaded room map.
     * @throws MapLoadingException If an error occurs while loading the room map.
     */
    TileMap loadRoomMap(InputStream inputStream) throws MapLoadingException;

    /**
     * Returns the tile map data as a {@link TileMapImpl} object.
     *
     * @return the tile map data.
     */
    TileMap getTileMap();
}
