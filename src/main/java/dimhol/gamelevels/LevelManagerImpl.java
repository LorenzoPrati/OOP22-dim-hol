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
import dimhol.gamelevels.map.TileMapImpl;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * The LevelGenerator class generates a new level for the game, including the placement of the player and enemies.
 */
public class LevelManagerImpl implements LevelManager {
    private static final int DEFAULT_LENGTH_TO_BOSS_ROOM = 15;
    private static final int DEBUG_LENGTH_TO_BOSS_ROOM = 4;
    private static final int DEFAULT_SHOPS_PER_CYCLE = 3;
    private final MapLoader mapLoader;
    private final NormalRoomStrategy normalRoomStrategy;
    private final ShopRoomStrategy shopRoomStrategy;
    private final BossRoomStrategy bossRoomStrategy;
    private final int maxRoomNumber;
    private TileMap tileMap;
    private int currentLevel;

    /**
     * Constructs a LevelManagerImpl object.
     *
     * @param debug The debug mode, created to reach through the reduced length path the boss room.
     */
    public LevelManagerImpl(final boolean debug) {
        this.maxRoomNumber = debug ? DEBUG_LENGTH_TO_BOSS_ROOM : DEFAULT_LENGTH_TO_BOSS_ROOM;
        this.mapLoader = new MapLoaderImpl();
        final GenericFactory genericFactory = new GenericFactory();
        final EnemyFactory enemyFactory = new EnemyFactory();
        final ItemFactory itemFactory = new ItemFactory();
        final BossFactory bossFactory = new BossFactory();
        final InteractableObjectFactory interactableObjectFactory = new InteractableObjectFactory();
        final Random randomGenerator = new Random();
        this.tileMap = mapLoader.loadNormalRoom();
        normalRoomStrategy = new NormalRoomStrategy(genericFactory, enemyFactory, itemFactory,
                interactableObjectFactory, randomGenerator);
        shopRoomStrategy = new ShopRoomStrategy(genericFactory, enemyFactory, itemFactory,
                interactableObjectFactory, randomGenerator);
        bossRoomStrategy = new BossRoomStrategy(genericFactory, itemFactory, enemyFactory,
                interactableObjectFactory, randomGenerator, bossFactory);
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
        final var player = savePlayer(entities);
        return generateLevel(player, entities);
    }

    /**
     * Determines the type of room to generate based on the current level.
     *
     * @return The room strategy for generating the current room.
     */
    private RoomStrategy determineRoomType() {
        if (currentLevel == maxRoomNumber) {
            tileMap = mapLoader.loadBossRoom();
            return bossRoomStrategy;
        }
        if (currentLevel % DEFAULT_SHOPS_PER_CYCLE == 0) {
            tileMap = mapLoader.loadShopRoom();
            return shopRoomStrategy;
        }
        tileMap = mapLoader.loadNormalRoom();
        return normalRoomStrategy;
    }

    /**
     * Retrieves the tile map for the current level.
     *
     * @return The tile map for the current level.
     */
    @Override
    public TileMap getTileMap() {
        // Create a defensive copy of the tileMap
        return new TileMapImpl(
                tileMap.getLayers(),
                tileMap.getWidth(),
                tileMap.getHeight(),
                tileMap.getTileWidth(),
                tileMap.getTileHeight());
    }


    /**
     * Retrieves the set of free tiles in the map.
     *
     * @return The set of free tiles as pairs of (x,y) coordinates.
     */
    private Set<Pair<Integer, Integer>> getFreeTiles() {
        return IntStream.range(0, tileMap.getHeight())
                .boxed()
                .flatMap(row ->
                        IntStream.range(0, tileMap.getWidth())
                                .filter(column -> tileMap.getTile(column, row).isWalkableTile())
                                .mapToObj(column -> new ImmutablePair<>(row, column))
                )
                .collect(Collectors.toSet());
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
     * @param player   The player entity.
     * @param entities The
     * @return The list of entities for the new level.
     */
    private List<Entity> generateLevel(final Optional<Entity> player, final List<Entity> entities) {
        return this.determineRoomType().generate(player, getFreeTiles(), entities);
    }
}
