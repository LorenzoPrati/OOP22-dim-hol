package dimhol.gamelevels;

import dimhol.components.PositionComponent;
import dimhol.components.PowerComponent;
import dimhol.entity.Entity;
import dimhol.entity.factories.BossFactory;
import dimhol.entity.factories.EnemyFactory;
import dimhol.entity.factories.GenericFactory;
import org.apache.commons.lang3.tuple.Pair;
import org.locationtech.jts.math.Vector2D;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.IntStream;

/**
 * BossRoomStrategy is responsible for generating entities for a boss room in the game.
 * It generates the player, boss, and enemy waves for the room.
 */
public final class BossRoomStrategy implements RoomStrategy {

    private static final int ENEMY_POWER_MULTIPLIER = 10;
    private static final int NUM_ENEMIES_PER_WAVE = 3;
    private static final int NUM_ENEMY_WAVES = 3;
    private static final int MAX_ENEMIES_PER_TILE = 100;
    private static final int NUM_BOSS_ENTITIES = 1;
    private static final int BOSS_WIDTH = 4;
    private static final int BOSS_HEIGHT = 3;
    private final GenericFactory genericFactory;
    private final EnemyFactory enemyFactory;
    private final BossFactory bossFactory;
    private final int bossWidth;  // Width of the boss entity in tiles
    private final int bossHeight; // Height of the boss entity in tiles

    /**
     * Constructs a BossRoomStrategy.
     *
     * @param genericFactory The factory for creating generic entities.
     * @param enemyFactory   The factory for creating enemy entities.
     * @param bossFactory    The factory for creating boss entities.
     */
    public BossRoomStrategy(final GenericFactory genericFactory, final EnemyFactory enemyFactory,
                            final BossFactory bossFactory, final int bossWidth, final int bossHeight) {
        this.genericFactory = genericFactory;
        this.enemyFactory = enemyFactory;
        this.bossFactory = bossFactory;
        this.bossWidth = bossWidth;
        this.bossHeight = bossHeight;
    }

    /**
     * Generates entities for a boss room.
     *
     * @param entity    The optional entity parameter (not used in this implementation).
     * @param freeTiles The set of available tiles where entities can be placed.
     * @return A list of generated entities.
     */
    @Override
    public List<Entity> generate(final Optional<Entity> entity, final Set<Pair<Integer, Integer>> freeTiles) {
        List<Entity> entities = new ArrayList<>();

        // Place the player:
        Entity player = generateAndPlacePlayer(freeTiles);
        entities.add(player);

        // Place the boss:
        generateAndPlaceBoss(freeTiles, entities);

        // Generate enemy waves:
        generateAndPlaceEnemyWaves(freeTiles, entities);

        //generate Mins:
        bossFactory.createMinion(freeTiles.size(), freeTiles.size());
        System.out.println("DONE");

        return entities;
    }

    /**
     * Generates and places the player entity in a random position.
     *
     * @param freeTiles The set of available tiles where the player can be placed.
     * @return The generated player entity.
     */
    private Entity generateAndPlacePlayer(final Set<Pair<Integer, Integer>> freeTiles) {
        Entity player = createPlayer(freeTiles);
        placeEntityAtRandomPosition(player, freeTiles);
        return player;
    }

    /**
     * Generates and places the boss entity in a random position.
     *
     * @param freeTiles The set of available tiles where the boss can be placed.
     * @param entities  The list of entities where the boss will be added.
     */
    private void generateAndPlaceBoss(final Set<Pair<Integer, Integer>> freeTiles, final List<Entity> entities) {
        generateEntitiesWithExceptionHandling(() -> calculateNumEntities(freeTiles.size()), // Supplier to calculate the number of entities
                numBosses -> IntStream.range(0, (Integer) numBosses).forEach(i -> {
                    Entity boss = createBoss(freeTiles); // Create the boss entity
                    placeEntityWithDimension(boss, freeTiles, 4, 3); // Place the boss entity with dimensions
                    entities.add(boss); // Add the boss entity to the list of entities
                }),
                this::handleEntityGenerationError // Error handling consumer
        );
    }

    /**
     * Places the entity at a random position with specified dimensions within the set of free tiles.
     *
     * @param entity       The entity to place.
     * @param freeTiles    The set of available tiles where the entity can be placed.
     * @param width        The width of the entity.
     * @param height       The height of the entity.
     */
    private void placeEntityWithDimension(Entity entity, Set<Pair<Integer, Integer>> freeTiles, int width, int height) {
        List<Pair<Integer, Integer>> validTiles = findValidTilesWithDimension(freeTiles, width, height);
        if (!validTiles.isEmpty()) {
            Pair<Integer, Integer> randomTile = validTiles.get(new Random().nextInt(validTiles.size()));
            PositionComponent positionComponent = (PositionComponent) entity.getComponent(PositionComponent.class);
            Vector2D position = new Vector2D(randomTile.getLeft(), randomTile.getRight());
            positionComponent.setPos(position);
        }
    }

    /**
     * Finds valid tiles with the specified dimensions within the set of free tiles.
     *
     * @param freeTiles The set of available tiles where the entity can be placed.
     * @param width     The width of the entity.
     * @param height    The height of the entity.
     * @return A list of valid tiles with the specified dimensions.
     */
    private List<Pair<Integer, Integer>> findValidTilesWithDimension(Set<Pair<Integer, Integer>> freeTiles, int width, int height) {
        List<Pair<Integer, Integer>> validTiles = new ArrayList<>();
        for (Iterator<Pair<Integer, Integer>> iterator = freeTiles.iterator(); iterator.hasNext(); ) {
            Pair<Integer, Integer> tile = iterator.next();
            int tileX = tile.getLeft();
            int tileY = tile.getRight();
            boolean isValid = true;
            // Check if the dimensions of the entity fit within the current tile and its neighboring tiles
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    Pair<Integer, Integer> currentTile = Pair.of(tileX + i, tileY + j);
                    if (!freeTiles.contains(currentTile)) {
                        isValid = false;
                        break;
                    }
                }
                if (!isValid) {
                    break;
                }
            }
            if (isValid) {
                validTiles.add(tile);
            }
        }
        return validTiles;
    }

    /**
     * Calculates the number of entities to generate based on the number of free tiles and the entity dimensions.
     *
     * @param numFreeTiles The number of free tiles in the room.
     * @return The number of entities to generate.
     * @throws IllegalArgumentException if the number of free tiles is less than the entities spawned or the entity dimensions are invalid.
     */
    private int calculateNumEntities(final int numFreeTiles) {
        if (numFreeTiles < calculateRequiredTiles(BOSS_WIDTH, BOSS_HEIGHT)) {
            throw new IllegalArgumentException("Not enough free tiles to spawn the boss entity with the specified dimensions!");
        }
        int maxNumOfEntities = numFreeTiles / calculateRequiredTiles(BOSS_WIDTH, BOSS_HEIGHT);
        int numEntities = Math.min(NUM_BOSS_ENTITIES, maxNumOfEntities);
        return numEntities > 0 ? numEntities : 1;
    }

    /**
     * Calculates the number of tiles required to accommodate an entity with the specified dimensions.
     *
     * @param width  The width of the entity.
     * @param height The height of the entity.
     * @return The number of tiles required.
     * @throws IllegalArgumentException if the entity dimensions are invalid.
     */
    private int calculateRequiredTiles(int width, int height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Invalid entity dimensions!");
        }
        return width * height;
    }

    /**
     * Places the boss entity at a random position, considering its tile dimensions.
     *
     * @param boss      The boss entity.
     * @param freeTiles The set of available tiles where the boss can be placed.
     */
//    private void placeBossAtRandomPosition(Entity boss, Set<Pair<Integer, Integer>> freeTiles) {
//        Pair<Integer, Integer> randomTileForBoss = getRandomTileForBoss(freeTiles);
//        List<Pair<Integer, Integer>> bossTiles = getBossTiles(randomTileForBoss);
//        assignTilesToBoss(boss, bossTiles);
//    }

    /**
     * Retrieves a random tile that can accommodate the boss entity's dimensions.
     *
     * @param freeTiles The set of free tiles in the room.
     * @return A random tile that can accommodate the boss entity.
     * @throws IllegalStateException if no free tiles can accommodate the boss entity.
     */
    private Pair<Integer, Integer> getRandomTileForBoss(Set<Pair<Integer, Integer>> freeTiles) {
        List<Pair<Integer, Integer>> shuffledTiles = new ArrayList<>(freeTiles);
        Collections.shuffle(shuffledTiles);
        return shuffledTiles.stream()
                .filter(tile -> canAccommodateBoss(tile, freeTiles))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No free tiles can accommodate the boss entity."));
    }

    /**
     * Checks if a given tile can accommodate the boss entity's dimensions.
     *
     * @param tile      The tile to check.
     * @param freeTiles The set of free tiles in the room.
     * @return True if the tile can accommodate the boss entity, false otherwise.
     */
    private boolean canAccommodateBoss(Pair<Integer, Integer> tile, Set<Pair<Integer, Integer>> freeTiles) {
        int startX = tile.getLeft();
        int startY = tile.getRight();

        for (int x = startX; x < startX + bossWidth; x++) {
            for (int y = startY; y < startY + bossHeight; y++) {
                if (!freeTiles.contains(Pair.of(x, y))) {
                    return false;
                }
            }
        }
        System.out.println("Boss: can be accommodated");
        return true;
    }

    /**
     * Retrieves the list of tiles occupied by the boss entity, based on its starting position.
     *
     * @param startPos The starting position of the boss entity.
     * @return The list of tiles occupied by the boss entity.
     */
    private List<Pair<Integer, Integer>> getBossTiles(Pair<Integer, Integer> startPos) {
        List<Pair<Integer, Integer>> bossTiles = new ArrayList<>();
        int startX = startPos.getLeft();
        int startY = startPos.getRight();

        for (int x = startX; x < startX + bossWidth; x++) {
            for (int y = startY; y < startY + bossHeight; y++) {
                bossTiles.add(Pair.of(x, y));
            }
        }

        return bossTiles;
    }

    /**
     * Assigns the tiles occupied by the boss entity to the boss entity itself.
     *
     * @param boss      The boss entity.
     * @param bossTiles The list of tiles occupied by the boss entity.
     */
//    private void assignTilesToBoss(Entity boss, List<Pair<Integer, Integer>> bossTiles) {
//        PositionComponent positionComponent = (PositionComponent) boss.getComponent(PositionComponent.class);
//        positionComponent.setPos(getTileCenterPosition(bossTiles));
//        positionComponent.setTiles(bossTiles);
//        System.out.println("setTiles funzia");
//    }

    /**
     * Calculates the center position of a group of tiles.
     *
     * @param tiles The group of tiles.
     * @return The center position as a Vector2D.
     */
    private Vector2D getTileCenterPosition(List<Pair<Integer, Integer>> tiles) {
        double totalX = 0;
        double totalY = 0;

        for (Pair<Integer, Integer> tile : tiles) {
            totalX += tile.getLeft();
            totalY += tile.getRight();
        }

        double centerX = totalX / tiles.size() + 0.5;
        double centerY = totalY / tiles.size() + 0.5;

        return new Vector2D(centerX, centerY);
    }

    /**
     * Creates a boss entity with a random position, considering its tile dimensions.
     *
     * @param freeTiles The set of available tiles where the boss can be placed.
     * @return The created boss entity.
     */
    private Entity createBoss(final Set<Pair<Integer, Integer>> freeTiles) {
        Pair<Integer, Integer> randomTile = getRandomTileForBoss(freeTiles);
        List<Pair<Integer, Integer>> bossTiles = getBossTiles(randomTile);
        Vector2D bossPosition = getTileCenterPosition(bossTiles);

        return bossFactory.createBoss(bossPosition.getX(), bossPosition.getY()); //Missing the width and the height too.
    }


    /**
     * Generates and places the enemy waves in random positions.
     *
     * @param freeTiles The set of available tiles where the enemies can be placed.
     * @param entities  The list of entities where the enemies will be added.
     */
    private void generateAndPlaceEnemyWaves(final Set<Pair<Integer, Integer>> freeTiles, final List<Entity> entities) {
        generateEntitiesWithExceptionHandling(() -> calculateNumEnemies(freeTiles.size()),
                numEnemies -> IntStream.range(0, NUM_ENEMY_WAVES).forEach(wave -> IntStream.range(0, numEnemies)
                        .mapToObj(i -> createEnemy(freeTiles, wave)).forEach(enemy -> {
                            placeEntityAtRandomPosition(enemy, freeTiles);
                            entities.add(enemy);
                        })),
                this::handleEntityGenerationError
        );
    }

//    private void generateAndPlaceMins(final Set<Pair<Integer, Integer>> freeTiles, final List<Entity> entities) {
//
//    }

    /**
     * Generates entities with exception handling.
     * The generateEntitiesWithExceptionHandling method takes a supplier (entityCountSupplier) to retrieve the number of entities,
     * a consumer (entityGenerationConsumer) to generate the entities,
     * and a consumer (errorConsumer) to handle any exceptions that occur during the process.
     *
     * @param <T>                  The type of the number of entities.
     * @param entityCountSupplier  The supplier to retrieve the number of entities.
     * @param entityGenerationFunc The consumer to generate the entities.
     * @param errorHandlingFunc    The consumer to handle any exceptions that occur during generation.
     */
    private <T> void generateEntitiesWithExceptionHandling(
            final Supplier<T> entityCountSupplier,
            final Consumer<T> entityGenerationFunc,
            final Consumer<Exception> errorHandlingFunc) {
        try {
            T entityCount = entityCountSupplier.get();
            entityGenerationFunc.accept(entityCount);
        } catch (Exception e) {
            errorHandlingFunc.accept(e);
        }
    }

    /**
     * Handles the error that occurs during entity generation.
     * The handleEntityGenerationError method is responsible for handling any exceptions that occur during entity generation.
     *
     * @param e The exception that occurred.
     */
    private void handleEntityGenerationError(final Exception e) {
        System.err.println("Error generating entities: " + e.getMessage());
    }

    /**
     * Creates an enemy entity and assigns it a random position from the set of free tiles.
     *
     * @param freeTiles The set of available tiles where the enemy can be placed.
     * @param wave      The wave number of the enemy being created.
     * @return The created enemy entity.
     */
    private Entity createEnemy(final Set<Pair<Integer, Integer>> freeTiles, final int wave) {
        // Customize enemy creation based on wave number
        Entity enemy = wave % 2 == 0
                ?
                enemyFactory.createZombie(0, 0)
                :
                enemyFactory.createShooter(0, 0);
        setEnemyPower(enemy, wave);
        placeEntity(enemy, freeTiles);
        return enemy;
    }

    /**
     * Assigns a random position from the set of free tiles to the specified entity.
     *
     * @param entity    The entity to place.
     * @param freeTiles The set of available tiles where the entity can be placed.
     */
    private void placeEntityAtRandomPosition(final Entity entity, final Set<Pair<Integer, Integer>> freeTiles) {
        List<Pair<Integer, Integer>> shuffledTiles = new ArrayList<>(freeTiles);
        Collections.shuffle(shuffledTiles);
        Pair<Integer, Integer> randomTile = shuffledTiles.get(0);
        PositionComponent positionComponent = (PositionComponent) entity.getComponent(PositionComponent.class);
        Vector2D position = new Vector2D(randomTile.getLeft(), randomTile.getRight());
        positionComponent.setPos(position);
    }

    /**
     * Sets the power level of an enemy based on the wave number.
     *
     * @param enemy The enemy entity.
     * @param wave  The wave number.
     */
    private void setEnemyPower(final Entity enemy, final int wave) {
        double enemyPower = calculateEnemyPower(wave);
        PowerComponent powerComponent = new PowerComponent(enemyPower);
        enemy.addComponent(powerComponent);
    }

    /**
     * Calculates the power level of an enemy based on the wave number.
     *
     * @param wave The wave number.
     * @return The power level of the enemy.
     */
    private double calculateEnemyPower(final int wave) {
        return wave * ENEMY_POWER_MULTIPLIER;
    }

    /**
     * Creates a player entity with a random position from the set of free tiles.
     *
     * @param freeTiles The set of available tiles where the player can be placed.
     * @return The created player entity.
     */
    private Entity createPlayer(final Set<Pair<Integer, Integer>> freeTiles) {
        return genericFactory.createPlayer(getRandomTile(freeTiles).getLeft().doubleValue(),
                getRandomTile(freeTiles).getRight().doubleValue());
    }

    /**
     * Assigns a random position from the set of free tiles to the specified entity.
     *
     * @param entity    The entity to place.
     * @param freeTiles The set of available tiles where the entity can be placed.
     */
    private void placeEntity(final Entity entity, final Set<Pair<Integer, Integer>> freeTiles) {
        placeEntityAtRandomPosition(entity, freeTiles);
    }

    /**
     * Retrieves a random tile from the set of free tiles.
     *
     * @param freeTiles The set of free tiles in the room.
     * @return A random tile represented as a Pair of coordinates.
     * @throws IllegalStateException if no free tiles are available.
     */
    private Pair<Integer, Integer> getRandomTile(final Set<Pair<Integer, Integer>> freeTiles) {
        List<Pair<Integer, Integer>> shuffledTiles = new ArrayList<>(freeTiles);
        Collections.shuffle(shuffledTiles);
        return shuffledTiles.get(0);
    }

//    /**
//     * Creates a boss entity and assigns it a random position from the set of free tiles.
//     *
//     * @param freeTiles The set of available tiles where the boss can be placed.
//     * @return The created boss entity.
//     */
//    private Entity createBoss(final Set<Pair<Integer, Integer>> freeTiles) {
//        return bossFactory.createBoss(getRandomTile(freeTiles).getLeft().doubleValue(),
//                getRandomTile(freeTiles).getRight().doubleValue());
//    }

    /**
     * Calculates the number of enemies to generate based on the number of free tiles.
     *
     * @param numFreeTiles The number of free tiles in the room.
     * @return The number of enemies to generate.
     * @throws IllegalArgumentException if the number of free tiles is less than the entities spawned.
     */
    private int calculateNumEnemies(final int numFreeTiles) {
        if (numFreeTiles < calculateTotalTilesForBoss()) {  //Before used MAX_ENEMIES_PER_TILE
            throw new IllegalArgumentException("The number of free tiles is less than the entities spawned!");
        }
        int maxNumOfEnemies = (numFreeTiles - calculateTotalTilesForBoss()) / MAX_ENEMIES_PER_TILE;
        int numEnemies = Math.min(NUM_ENEMIES_PER_WAVE, maxNumOfEnemies);
        return numEnemies > 0 ? numEnemies : 1;
    }
    /**
     * Calculates the total number of tiles occupied by the boss entity.
     *
     * @return The total number of tiles occupied by the boss entity.
     */
    private int calculateTotalTilesForBoss() {
        return bossWidth * bossHeight;
    }
}
