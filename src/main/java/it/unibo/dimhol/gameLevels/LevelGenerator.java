package it.unibo.dimhol.gameLevels;

import it.unibo.dimhol.World;
import it.unibo.dimhol.components.PlayerComponent;
import it.unibo.dimhol.entity.Entity;
import it.unibo.dimhol.entity.GenericFactory;
import it.unibo.dimhol.events.AddEntityEvent;
import it.unibo.dimhol.map.LoadMap;
import it.unibo.dimhol.map.LoadMapImpl;
import it.unibo.dimhol.map.Tile;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.Point;

public class LevelGenerator {
    private static final int ENEMY_DENSITY = 50;
    private World world;
    private LoadMap loadMap = new LoadMapImpl("src/main/java/it/unibo/dimhol/map/mapResources/FirstTryTiled.xml");
    private GenericFactory genericFactory = new GenericFactory();
    private Tile[][] map;
    private int currentLevel;
    private Entity player;
    private Entity enemy;

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
            List<Tile> walkableTiles = getWalkableTiles();
            int playerIndex = new Random().nextInt(walkableTiles.size());
            Tile playerTile = walkableTiles.get(playerIndex);
            putPlayer(playerTile.getX(5.0), playerTile.getY(5.0)); //TODO: to be solved
            putEnemies(walkableTiles, playerIndex);
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
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j].isCollidable()) {
                    walkableTiles.add(map[i][j]);
                }
            }
        }
        return walkableTiles;
    }

    /**
     * Puts the player entity at the specified position.
     *
     * @param x the x-coordinate of the player entity.
     * @param y the y-coordinate of the player entity.
     */
    private void putPlayer(double x, double y) {
        Entity playerEntity = genericFactory.createPlayer(x, y);
        world.addEntity(playerEntity);
//        world.setPlayer(playerEntity);
    }

    /**
     * Puts the enemies entities randomly around the map.
     *
     * @param walkableTiles The list of walkable tiles in the map.
     * @param playerIndex The index of the tile where the player will be placed.
     */
    private void putEnemies(List<Tile> walkableTiles, int playerIndex) {
        //Get the number of enemies to place:
        int numEnemies = (int) Math.ceil((walkableTiles.size() - 1) * ENEMY_DENSITY); // It calculates the number of enemies to place based on the density defined in ENEMY_DENSITY
        //Remove the player tile from the walkable tiles:
        walkableTiles.remove(playerIndex);
        for (int i = 0; i < numEnemies; i++) {
            //Get a random tile index from the list of walkable tiles.
            int randomIndex = (int) (Math.random() * walkableTiles.size()); //randomly selects tiles from the list to place enemies on
            world.addEntity(enemy);
            //remove the tile form the list of walkable tiles so an enemy cannot be placed here.
            walkableTiles.remove(randomIndex);
        }
    }
}