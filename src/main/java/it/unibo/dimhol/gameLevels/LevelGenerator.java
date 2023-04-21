package it.unibo.dimhol.gameLevels;

import it.unibo.dimhol.World;
import it.unibo.dimhol.components.PlayerComponent;
import it.unibo.dimhol.entity.Entity;
import it.unibo.dimhol.entity.GenericFactory;
import it.unibo.dimhol.events.AddEntityEvent;
import it.unibo.dimhol.map.LoadMap;
import it.unibo.dimhol.map.LoadMapImpl;
import it.unibo.dimhol.map.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LevelGenerator {
    private World world;
    private LoadMap loadMap = new LoadMapImpl("src/main/java/it/unibo/dimhol/map/mapResources/FirstTryTiled.xml");
    private GenericFactory genericFactory = new GenericFactory();
    private Tile[][] map;
    private int currentLevel;
    private Entity player;

    private LevelGenerator(World world) {
        this.world = world;
        currentLevel = 0;
    }

    //Change the level:
//    public void changeLevel() {
        //Saving player
        //Clear the room
        //Load the map
        //Finds walkable tiles positions -> empty or not
        //Puts player
        //Puts enemies

        /**
         * Changes the current level.
         */
        public void changeLevel() {
            savePlayer();
            clearEntities();
            loadMap();
            List<Tile> walkableTiles = findWalkableTile();
            int playerIndex = new Random().nextInt(walkableTiles.size());
            Tile playerTile = walkableTiles.get(playerIndex);
            putPlayer();
            putEnemies();
        }

    /**
     * Saves the player's entity.
     */
    private void savePlayer() {
        for (Entity entity : world.getEntities()) {
            if(entity.hasComponent(PlayerComponent.class)) {
                player = entity;
            }
        }
    }

    /**
     * Clears all entities except for the player.
     */
    private void clearEntities() {
        world.getEntities().clear();
    }

    /**
     * Loads the map into the level generator.
     */
    private void loadMap() {
        map = loadMap.getMap();
    }

    /**
     * Finds all the walkable tiles in the current map.
     *
     * @return A list of all the walkable tiles.
     */
    private List<Tile> getWalkableTiles() {
        List<Tile> walkableTiles = new ArrayList<>();
        for (int i = 0;)

        return null;
    }

    /**
     * Puts the player entity at the specified position.
     *
     * @param x the x-coordinate of the player entity.
     * @param y the y-coordinate of the player entity.
     */
    private void putPlayer(double x, double y) {
    }

    /**
     * Puts the enemies entities randomly around the map.
     *
     * @param walkableTiles The list of walkable tiles in the map.
     */
    private void putEnemies(List<Tile> walkableTiles) {

    }
}


















