package dimhol.gamelevels;

import dimhol.components.PlayerComponent;
import dimhol.entity.Entity;
import dimhol.entity.factories.BossFactory;
import dimhol.entity.factories.EnemyFactory;
import dimhol.entity.factories.GenericFactory;
import dimhol.entity.factories.InteractableObjectFactory;
import dimhol.entity.factories.ItemFactory;
import dimhol.gamelevels.map.MapLoader;
import dimhol.gamelevels.map.MapLoaderImpl;
import dimhol.gamelevels.map.TileMap;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

/**
 * The LevelGenerator class generates a new level for the game, including the placement of the player and enemies.
 */
public class LevelManagerImpl implements LevelManager {
    private static final int BOSS_ROOM_INDEX = 4;
    private static final int DEFAULT_CYCLE_LENGTH = 5;
    // This will give us a cyclic pattern of 0, 1, 2, 3, 4, - 0, 1, 2, 3, 4 and so on.
    private static final int DEFAULT_SHOPS_PER_CYCLE = 3;
    private final MapLoader mapLoader;
    private final NormalRoomStrategy normalRoomStrategy;
    private final ShopRoomStrategy shopRoomStrategy;
    private final BossRoomStrategy bossRoomStrategy;
    private TileMap tileMap;
    private int currentLevel;

    /**
     * Constructs a LevelManagerImpl object.
     */
    public LevelManagerImpl() {
        this.mapLoader = new MapLoaderImpl();
        GenericFactory genericFactory = new GenericFactory();
        EnemyFactory enemyFactory = new EnemyFactory();
        ItemFactory itemFactory = new ItemFactory();
        InteractableObjectFactory interactableObjectFactory = new InteractableObjectFactory();
        Random random = new Random();
        this.tileMap = mapLoader.loadNormalRoom();
        normalRoomStrategy = new NormalRoomStrategy(genericFactory, enemyFactory, itemFactory, interactableObjectFactory, random);
        shopRoomStrategy = new ShopRoomStrategy(genericFactory, itemFactory, interactableObjectFactory, new RandomWrapper());
        bossRoomStrategy = new BossRoomStrategy(genericFactory, enemyFactory, new BossFactory());
        this.currentLevel = 0;
    }

    /**
     * Changes the current level by generating a new level with the placement of the player and enemies.
     *
     * @param entities The list of entities in the game world.
     * @return The list of entities for the new level.
     */
    @Override
    public List<Entity> changeLevel(final List<Entity> entities) {
        currentLevel++;
        var player = savePlayer(entities);
        return generateLevel(player, entities);
    }

    /**
     * Determines the type of room to generate based on the current level.
     *
     * @return The room strategy for generating the current room.
     */
    private RoomStrategy determineRoomType() {
        // Calculate the room index within the current cycle
        int roomIndex = currentLevel % DEFAULT_CYCLE_LENGTH;
        if (roomIndex == BOSS_ROOM_INDEX) {  // Check the room index to determine the room type
            //Generate a boss room
            tileMap = mapLoader.loadBossRoom();
            System.out.println("Boss");
            System.out.println("current room " + roomIndex);
            return bossRoomStrategy;
        }
        if (roomIndex % DEFAULT_SHOPS_PER_CYCLE == 0) {
            //Generate a shop
            tileMap = mapLoader.loadShopRoom();
            System.out.println("Shop");
            System.out.println("current room " + roomIndex);
            return shopRoomStrategy;
        }
        //Generate a normal room
        tileMap = mapLoader.loadNormalRoom();
        System.out.println("Normal");
        System.out.println("current room " + roomIndex);
        return normalRoomStrategy;
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
                if (tileMap.getTile(j, i).isWalkableTile()) {
                    freeTiles.add(new ImmutablePair<>(i, j));
                }
            }
        }
        return freeTiles;
    }

    /**
     * Retrieves the player entity from the game world.
     *
     * @param entities The list of entities in the game world.
     * @return The player entity if found, otherwise null.
     */
    private Optional<Entity> savePlayer(final List<Entity> entities) {
        return entities.stream()
                .filter(entity -> entity.hasComponent(PlayerComponent.class))
                .findFirst();
    }

    /**
     * Generates the level by adding entities (player, enemies) to the world.
     *
     * @param player The player entity.
     * @return The list of entities for the new level.
     */
    private List<Entity> generateLevel(final Optional<Entity> player, List<Entity> entities) {
        return this.determineRoomType().generate(player, getFreeTiles(), entities);
    }
}
