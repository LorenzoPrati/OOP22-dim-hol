package dimhol.gamelevels;

import dimhol.components.PlayerComponent;
import dimhol.core.World;
import dimhol.entity.Entity;
import dimhol.entity.factories.EnemyFactory;
import dimhol.entity.factories.GenericFactory;
import dimhol.map.MapLoader;
import dimhol.map.TileMap;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashSet;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

/**
 * The LevelGenerator class generates a new level for the game, including the placement of the player and enemies.
 */
    public class LevelManagerImpl implements LevelManager {
    private final World world;
    private final MapLoader mapLoader;
    private final TileMap map;
    private final NormalRoomStrategy normalRoomStrategy;

    /**
     * Constructs a LevelManger object.
     *
     * @param world The game world.
     * @param mapLoader The map loader for loading tile maps.
     */

    public LevelManagerImpl(final World world, final MapLoader mapLoader) {
        this.world = world;
        this.mapLoader = mapLoader;
        this.map = mapLoader.getTileMap();
        EnemyFactory enemyFactory = new EnemyFactory();
        GenericFactory genericFactory = new GenericFactory();
        normalRoomStrategy = new NormalRoomStrategy(genericFactory, enemyFactory, new Random());
    }

    /**
     * Changes the current level by generating a new level with the placement of the player and enemies.
     */
     @Override
     public void changeLevel() {
        var player = savePlayer();
//        clearEntities();
        //changeMap(); assign a new map to -> Normal, Shop and Boss rooms.
        generateLevel(player);
    }

    /**
     * Retrieves the set of free tiles in the map.
     *
     * @return The set of free tiles as pairs of (x,y) coordinates.
     */
    private Set<Pair<Integer, Integer>> getFreeTiles() {
        var freeTiles = new HashSet<Pair<Integer, Integer>>();
        for (int i = 0; i < mapLoader.getHeight(); i++) {
            for (int j = 0; j < mapLoader.getWidth(); j++) {
                if (map.getTile(j, i).isWalkable()) {
                    freeTiles.add(new ImmutablePair<>(i, j));
                }
            }
        }
        return freeTiles;
    }

    /**
     * Clears all entities in the game world level.
     */
//    private void clearEntities() {
//        world.getEntities().clear();
//    }

    /**
     * Retrieves the player entity from the game world.
     *
     * @return The player entity if found, otherwise null.
     */
    private Entity savePlayer() {
        Optional<Entity> player = world.getEntities().stream()
                    .filter(entity -> entity.hasComponent(PlayerComponent.class))
                    .findFirst();
            return player.orElse(null);
            /*filters the entities in the world based on the case where they have a PlayerComponent,
            and then returns the first matching entity found as an Optional<Entity>.
            If no player entity is found, the Optional will be empty.*/
    }

    /**
     * Changes the map based on the current level and room.
     */
    private void changeMap() {
        // TODO: implement the logic to assign a new map for different rooms.
        // For example:
        // -Normal room: mapLoader.loadNormalRoom();
        // -Shop room: mapLoader.loadShopRoom();
        // -Boss room: mapLoader.loadBossRoom();
        // -Others...
    }

    /**
     * Generates the level by adding entities (player, enemies) to the world.
     *
     * @param player The player entity.
     */
    private void generateLevel(final Entity player) {
        this.normalRoomStrategy.generate(player, this.getFreeTiles()).forEach(world::addEntity);
    }
}
