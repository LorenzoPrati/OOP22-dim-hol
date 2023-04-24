package it.unibo.dimhol.gameLevels;

import it.unibo.dimhol.World;
import it.unibo.dimhol.components.PlayerComponent;
import it.unibo.dimhol.entity.Entity;
import it.unibo.dimhol.entity.GenericFactory;
import it.unibo.dimhol.map.MapLoad;
import it.unibo.dimhol.map.MapLoaderImpl;
import it.unibo.dimhol.map.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The LevelGenerator class generates a new level for the game, including the placement of the player and enemies.
 */
public class LevelManager {
    private static final int ENEMY_DENSITY = 10;
    private final World world;
    private final MapLoad mapLoad = new MapLoaderImpl("src/main/java/it/unibo/dimhol/map/mapResources/FirstTryTiled.xml");
    private final GenericFactory genericFactory = new GenericFactory();
    private Tile[][] map;
    private final int currentLevel = 0;
    private Entity player;
    private LevelManager(World world) {
        this.world = world;
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
            Tile playerTile = getRandomTile(walkableTiles);
            placePlayer(playerTile.getX(5.0), playerTile.getY(5.0));
            placeEnemies(walkableTiles, playerTile, 10.0, 10.0);
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
        map = mapLoad.getMapTileLayers().get(0);
    }

    /**
     * Finds all the walkable tiles in the current map.
     *
     * @return A list of all the walkable tiles.
     */
    private List<Tile> getWalkableTiles() {
        List<Tile> walkableTiles = new ArrayList<>();
        for (Tile[] tiles : map) {
            for (Tile tile : tiles) {
                if (!tile.isWalkable()) {
                    walkableTiles.add(tile);
                }
            }
        }
        return walkableTiles;
    }
    /**
     * Selects a random tile from the given list of walkable tiles.
     * @param walkableTiles The list of walkable tiles to choose from.
     * @return A random walkable tile.
     */
    private Tile getRandomTile(List<Tile> walkableTiles) {
        return walkableTiles.get(new Random().nextInt(walkableTiles.size()));
    }
    /**
     * Place the player entity at the specified position.
     *
     * @param x the x-coordinate of the player entity.
     * @param y the y-coordinate of the player entity.
     */
    private void placePlayer(double x, double y) {
        Entity playerEntity = genericFactory.createPlayer(x, y);
        world.addEntity(playerEntity);
//        world.setPlayer(playerEntity);
    }
    /**
     * Places enemies entities on random tiles in the game map.
     *
     * @param walkableTiles The list of walkable tiles in the map.
     * @param playerTile The index of the tile where the player will be placed.
     * @param x The x-coordinate where enemies should be placed.
     * @param y The y-coordinate where enemies should be placed.
     */
    private void placeEnemies(List<Tile> walkableTiles, Tile playerTile, double x, double y) {
        int numEnemies = calculateNumEnemies(walkableTiles.size());
        List<Tile> tilesToPlaceEnemiesOn = new ArrayList<>(walkableTiles); //creates a new list to store the tiles where enemies will be placed on.
        tilesToPlaceEnemiesOn.remove(playerTile); //remove the player tile form the list.

        for (int i = 0; i < numEnemies; i++) {
            int randomIndex = (int) (Math.random() * walkableTiles.size()); //Get a random tile index from the list.
            Tile tilesToPlaceEnemyOn = tilesToPlaceEnemiesOn.get(randomIndex);
            Entity enemyEntity = spawnEnemy(x, y);
            world.addEntity(enemyEntity);
            tilesToPlaceEnemiesOn.remove(tilesToPlaceEnemyOn); //remove the tile from the list so another enemy won't be placed there.
        }
    }
    /**
     * Calculates the number of enemies to place based on the density, that is defined in ENEMY_DENSITY.
     * @param numWalkableTiles
     * @return The number of enemies to be placed.
     */
    private int calculateNumEnemies(int numWalkableTiles) {
        /* subtracts 1 from the total number of walkable tiles in the game, as the player tile should not be counted as a valid enemy placement tile.
        * Then it's multiplied by the density value */
        return (int) Math.ceil((numWalkableTiles - 1) * ENEMY_DENSITY); //ceil do rounding to the result (to negative).
    }
    /**
     * Creates an enemy entity with the specified x and y coordinates.
     * @param x The x-coordinate where the enemy should be placed.
     * @param y The y-coordinate where the enemy should be placed.
     * @return The created enemy entity.
     */
    private Entity spawnEnemy(double x, double y) {
        return genericFactory.createZombieEnemy(x, y);
//        return genericFactory.createShooterEnemy(x, y);
    }
}