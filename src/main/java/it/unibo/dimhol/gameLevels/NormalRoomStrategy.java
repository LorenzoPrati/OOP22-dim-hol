package it.unibo.dimhol.gameLevels;

import it.unibo.dimhol.World;
import it.unibo.dimhol.entity.GenericFactory;
import it.unibo.dimhol.map.MapLoaderImpl;
import it.unibo.dimhol.map.Tile;
import org.apache.commons.lang3.tuple.Pair;
import org.jooq.lambda.tuple.Tuple;

import java.util.Random;
import java.util.Set;
import java.util.logging.Level;

public class NormalRoomStrategy implements RoomStrategy, RewardSpawner {
    private static final int MAX_ENEMIES = 30;
    private static final int MIN_ENEMIES = 1;
    private MapLoaderImpl mapLoader;
    private LevelManager levelManager;
    private Set<Pair<Integer, Integer>> occupiedTiles;

//    private static final int ENEMY_DENSITY = 10;
//    public NormalRoomStrategy(MapLoaderImpl mapLoader) {
//        this.mapLoader = mapLoader;
//    }

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
//            world.addEntity(enemy.createZombieEnemy(x,y));
        }
    }

    @Override
    public void placePlayer() {
//        Generate the player:
        GenericFactory player = new GenericFactory();
        double x = new Random().nextInt(0, mapLoader.getWidth() - 1);
        double y = new Random().nextInt(0, mapLoader.getHeight() - 1);
        //TODO:change
//        world.addEntity(player.createPlayer(x,y));
    }

    @Override
    public void spawnRewards() {
        //TODO: Algorithm to spawn rewards in a normal room.
        //Actually rewards aren't allowed to spawn in a normal room.
    }
}
