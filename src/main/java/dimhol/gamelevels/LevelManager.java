package dimhol.gamelevels;

import dimhol.components.PlayerComponent;
import dimhol.core.World;
import dimhol.entity.Entity;
import dimhol.entity.factories.GenericFactory;
import dimhol.map.MapLoad;
import dimhol.map.MapLoaderImpl;
import dimhol.map.Tile;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

/**
 * The LevelGenerator class generates a new level for the game, including the placement of the player and enemies.
 */
    public class LevelManager {
    private final World world;
    private final MapLoad mapLoad = new MapLoaderImpl("src/main/resources/config/map/nice-map.xml");
    private final GenericFactory genericFactory = new GenericFactory();
    private Tile[][] map;
    private NormalRoomStrategy normalRoomStrategy;
    public LevelManager(World world) {
        this.world = world;
    }
        /**
         * Changes the current level.
         */
        public void changeLevel() {
            var player = savePlayer();
            clearEntities();
            //changeMap(); assign a new map to -> Normal, Shop and Boss rooms.
            this.normalRoomStrategy.generate(player, this.getFreeTiles()).forEach(entity -> world.addEntity(entity));
        }

        private Set<Pair<Integer, Integer>> getFreeTiles() {
            var freeTiles = new HashSet<Pair<Integer, Integer>>();
            for (int i = 0; i < mapLoad.getHeight(); i++) {
                for (int j = 0; j < mapLoad.getWidth(); j++) {
                    if (map[i][j].isWalkable()) {
                        freeTiles.add(new ImmutablePair<>(i,j));
                    }
                }
            }
            return freeTiles;
        }

    private void clearEntities() {
            world.getEntities().clear();
        }

    private Entity savePlayer() {
            Entity player = null; //TODO: change to Optional.
            for (Entity entity : world.getEntities()) {
                if (entity.hasComponent(PlayerComponent.class)) {
                   player = entity;
                }
            }
            return player;
        }
}
