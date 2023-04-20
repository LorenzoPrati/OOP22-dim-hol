package it.unibo.dimhol.map;

import it.unibo.dimhol.entity.GenericFactory;
import it.unibo.dimhol.systems.GameSystem;

import java.util.ArrayList;
import java.util.List;

public class LevelGenerator implements GameSystem, Room {
    /**
     * The Starting room.
     */
    final static int CURRENT_ROOM = 0;

    /**
     * Counter to track the corresponding room.
     */
    final int currenRoom = CURRENT_ROOM; //TODO: handle the final and shop room.

    /**
     * List of all the level entities.
     */
    private List<GenericFactory> levelEntities;
    private LoadMapImpl loadMap;
    private Tile tile;

    public LevelGenerator() { levelEntities = new ArrayList<GenericFactory>(); }

    /**
     * Activates the LevelGenerator.
     * @param
     */
    public void activate() {
        /**
         * Clear all the level entities from the room.
         */
        levelEntities.clear();
        //TODO: Tile[][] tileMap = ... -> to Get the tileMap for the current room level.
    }

    @Override
    public void update(long dt) {

    }

    @Override
    public void normalRoom() {

    }

    @Override
    public void ShopRoom() {

    }

    @Override
    public void BossRoom() {

    }
}
