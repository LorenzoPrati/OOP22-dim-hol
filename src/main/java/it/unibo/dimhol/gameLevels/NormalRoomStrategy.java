package it.unibo.dimhol.gameLevels;

import it.unibo.dimhol.World;
import it.unibo.dimhol.entity.GenericFactory;
import it.unibo.dimhol.map.MapLoaderImpl;

import java.util.Random;

public class NormalRoomStrategy implements RoomStrategy {
    private static final int MAX_ENEMIES = 30;
    private static final int MIN_ENEMIES = 1;
    private World world;
//    private static final int ENEMY_DENSITY = 10;
    MapLoaderImpl mapLoader = new MapLoaderImpl("src/main/java/it/unibo/dimhol/map/mapResources/FirstTryTiled.xml");

    public NormalRoomStrategy(World world) {
        this.world = world;
    }

    @Override
    public void generate() {
        //Generate enemies and player into the normal room.
//        int numEnemies = calculateNumEnemies(walkableTiles.size());
        int numEnemies = new Random().nextInt(MIN_ENEMIES, MAX_ENEMIES + 1);
        for (int i = 0; i < numEnemies; i++) {
            GenericFactory enemy = new GenericFactory();
            double x = new Random().nextInt(0, mapLoader.getWidth());
            double y = new Random().nextInt(0, mapLoader.getHeight());
//        enemy.setPosition(x,y); -> Implemented into the World.
            //Add enemy to list of entities into the world.
            //TODO:change
            world.addEntity(enemy.createZombieEnemy(x,y));
        }
        //Generate the player:
        GenericFactory player = new GenericFactory();
        double x = new Random().nextInt(0, mapLoader.getWidth() - 1);
        double y = new Random().nextInt(0, mapLoader.getHeight() - 1);
        world.addEntity(player.createPlayer(x,y));
    }
    @Override
    public void spawnRewards() {
        //TODO: Algorithm to spawn rewards in a normal room.
        //Actually rewards aren't allowed to spawn in a normal room.
    }
}

//TAKE A LOOK HERE:
//    private int calculateNumEnemies(int numWalkableTiles) {
/* subtracts 1 from the total number of walkable tiles in the game, as the player tile should not be counted as a valid enemy placement tile.
 * Then it's multiplied by the density value */
//        return (int) Math.ceil((numWalkableTiles - 1) * ENEMY_DENSITY); //ceil do rounding to the result (to negative).
//    }
//TODO: Add game objects if needed.
//...
//world.setEntities(entities);
