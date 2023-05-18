package dimhol.gamelevels;

import dimhol.components.PlayerComponent;
import dimhol.core.World;
import dimhol.entity.Entity;
import dimhol.entity.factories.BossFactory;
import dimhol.entity.factories.EnemyFactory;
import dimhol.entity.factories.GenericFactory;
import dimhol.gamelevels.map.MapLoader;
import dimhol.gamelevels.map.TileMap;
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
    private final static int BOSS_ROOM_INDEX = 4;
    private static final int DEFAULT_CYCLE_LENGTH = 5; // This will give us a cyclic pattern of 0, 1, 2, 3, 4, - 0, 1, 2, 3, 4 and so on.
    private static final int DEFAULT_SHOPS_PER_CYCLE = 3;
    private final World world;
    private final MapLoader mapLoader;
    private final NormalRoomStrategy normalRoomStrategy;
    private final ShopRoomStrategy shopRoomStrategy;
    private final BossRoomStrategy bossRoomStrategy;
    private TileMap tileMap;
    private int currentLevel;

    /**
     * Constructs a LevelManagerImpl object.
     *
     * @param world The game world.
     * @param mapLoader The map loader.
     */
    public LevelManagerImpl(final World world, final MapLoader mapLoader) {
        this.world = world;
        this.mapLoader = mapLoader;
        GenericFactory genericFactory = new GenericFactory();
        EnemyFactory enemyFactory = new EnemyFactory();
        Random random = new Random();
        this.tileMap = mapLoader.loadShopRoom();
        normalRoomStrategy = new NormalRoomStrategy(genericFactory, enemyFactory, random);
        shopRoomStrategy = new ShopRoomStrategy(genericFactory, random);
        bossRoomStrategy = new BossRoomStrategy(genericFactory, enemyFactory, new BossFactory(), random);
        this.currentLevel = 0;
    }

    /**
     * Changes the current level by generating a new level with the placement of the player and enemies.
     */
    @Override
    public void changeLevel() {
        currentLevel++;
        var player = savePlayer();
        clearEntities();
        determineRoomType();
        changeMap();
        generateLevel(player);
    }

    /**
     * Determines the type of room to generate based on the current level.
     */
    private void determineRoomType() {
        // Calculate the room index within the current cycle
        int roomIndex = currentLevel % DEFAULT_CYCLE_LENGTH;

        if (roomIndex == BOSS_ROOM_INDEX) {  // Check the room index to determine the room type
            //Generate a boss room
            mapLoader.loadBossRoom();
            System.out.println("Boss");
        } else if (roomIndex % DEFAULT_SHOPS_PER_CYCLE == 0) {
            //Generate a shop
            mapLoader.loadShopRoom();
            System.out.println("Shop");
        } else {
            //Generate a normal room
            mapLoader.loadNormalRoom();
            System.out.println("Normal");
        }
        System.out.println("current room " + roomIndex);
    }

    /**
     * Clears all entities in the game world level.
     */
    private void clearEntities() {
        world.getEntities().clear();
    }

    /**
     * Retrieves the tile map for the current level.
     *
     * @return The tile map for the current level.
     */
    @Override
    public TileMap getTileMap() {
        return this.tileMap;
    }

    /**
     * Retrieves the set of free tiles in the map.
     *
     * @return The set of free tiles as pairs of (x,y) coordinates.
     */
    private Set<Pair<Integer, Integer>> getFreeTiles() {
        Set<Pair<Integer, Integer>> freeTiles = new HashSet<>();
        for (int i = 0; i < tileMap.getHeight(); i++) {
            for (int j = 0; j < tileMap.getWidth(); j++) {
                if (tileMap.getTile(j, i).isWalkable()) {
                    freeTiles.add(new ImmutablePair<>(i, j));
                }
            }
        }
        return freeTiles;
    }

    /**
     * Changes the map based on the current level and room.
     */
    private void changeMap() {
        this.tileMap = mapLoader.loadNormalRoom(); //current room
    }

    /**
     * Retrieves the player entity from the game world.
     *
     * @return The player entity if found, otherwise null.
     */
    private Optional<Entity> savePlayer() {
        return world.getEntities().stream()
                .filter(entity -> entity.hasComponent(PlayerComponent.class))
                .findFirst();
    }

    /**
     * Generates the level by adding entities (player, enemies) to the world.
     *
     * @param player The player entity.
     */
    private void generateLevel(final Optional<Entity> player) {
        normalRoomEntitiesGenerator(player, getFreeTiles());
        //TODO: handle them.
//        shopRoomEntitiesGenerator(player, getFreeTiles());
//        bossRoomEntitiesGenerator(player, getFreeTiles());
    }

    /**
     * Generates entities for a normal room.
     * @param player The player entity.
     * @param freeTiles The set of free tiles in the room.
     */
    private void normalRoomEntitiesGenerator(Optional<Entity> player, Set<Pair<Integer, Integer>> freeTiles) {
        normalRoomStrategy.generate(player, getFreeTiles()).forEach(world::addEntity);
    }

    /**
     * Generates entities for a shop room.
     * @param player The player entity.
     * @param freeTiles The set of free tiles in the room.
     */
    private void shopRoomEntitiesGenerator(Optional<Entity> player, final Set<Pair<Integer, Integer>> freeTiles) {
        shopRoomStrategy.generate(player, getFreeTiles()).forEach(world::addEntity);
    }

    /**
     * Generates entities for a normal room.
     *
     * @param player The player entity.
     * @param freeTiles The set of free tiles in the room.
     */
    private void bossRoomEntitiesGenerator(Optional<Entity> player, Set<Pair<Integer, Integer>> freeTiles) {
        bossRoomStrategy.generate(player, getFreeTiles()).forEach(world::addEntity);
    }
}
