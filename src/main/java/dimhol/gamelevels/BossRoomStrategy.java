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
    private static final int NUM_ENEMY_WAVES = 3;
    private static final int MAX_ENEMIES_PER_TILE = 100;
    private final GenericFactory genericFactory;
    private final EnemyFactory enemyFactory;
    private final BossFactory bossFactory;
    private final Random random;

    /**
     * Constructs a BossRoomStrategy.
     *
     * @param genericFactory The factory for creating generic entities.
     * @param enemyFactory   The factory for creating enemy entities.
     * @param bossFactory    The factory for creating boss entities.
     * @param random         The random number generator.
     */
    public BossRoomStrategy(final GenericFactory genericFactory,
                            final EnemyFactory enemyFactory, final BossFactory bossFactory, final Random random) {
        this.genericFactory = genericFactory;
        this.enemyFactory = enemyFactory;
        this.bossFactory = bossFactory;
        this.random = new Random(random.nextInt());
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

        return entities;
    }

    private Entity generateAndPlacePlayer(Set<Pair<Integer, Integer>> freeTiles) {
        Entity player = createPlayer(freeTiles);
        placeEntityAtRandomPosition(player, freeTiles);
        return player;
    }

    private void generateAndPlaceBoss(Set<Pair<Integer, Integer>> freeTiles, List<Entity> entities) {
        generateEntitiesWithExceptionHandling(() -> calculateNumEnemies(freeTiles.size()),
                numBoss -> IntStream.range(0, numBoss).mapToObj(i -> createBoss(freeTiles)).forEach(boss -> {
                    placeEntityAtRandomPosition(boss, freeTiles);
                    entities.add(boss);
                }),
                this::handleEntityGenerationError
        );
    }

    private void generateAndPlaceEnemyWaves(Set<Pair<Integer, Integer>> freeTiles, List<Entity> entities) {
        generateEntitiesWithExceptionHandling(() -> calculateNumEnemies(freeTiles.size()),
                numEnemies -> IntStream.range(0, NUM_ENEMY_WAVES).forEach(wave -> IntStream.range(0, numEnemies)
                        .mapToObj(i -> createEnemy(freeTiles, wave)).forEach(enemy -> {
                            placeEntityAtRandomPosition(enemy, freeTiles);
                            entities.add(enemy);
                        })),
                this::handleEntityGenerationError
        );
    }

    /**
     * Generates entities with exception handling.
     * The generateEntitiesWithExceptionHandling method takes a supplier (entityCountSupplier) to retrieve the number of entities,
     * a consumer (entityGenerationConsumer) to generate the entities,
     * and a consumer (errorConsumer) to handle any exceptions that occur during the process.
     *
     * @param entityCountSupplier      The supplier to retrieve the number of entities.
     * @param entityGenerationConsumer The consumer to generate the entities.
     * @param <T>                      The type of the number of entities.
     */
    private <T> void generateEntitiesWithExceptionHandling(Supplier<T> entityCountSupplier, Consumer<T> entityGenerationConsumer,
                                                           Consumer<Exception> errorConsumer) {
        try {
            T entityCount = entityCountSupplier.get();
            entityGenerationConsumer.accept(entityCount);
        } catch (Exception e) {
            errorConsumer.accept(e);
        }
    }

    /**
     * Handles the error that occurs during entity generation.
     * The handleEntityGenerationError method is responsible for handling any exceptions that occur during entity generation.
     *
     * @param e The exception that occurred.
     */
    private void handleEntityGenerationError(Exception e) {
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
        Entity enemy = wave % 2 == 0 ?
                enemyFactory.createZombie(0, 0) :
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

    /**
     * Creates a boss entity and assigns it a random position from the set of free tiles.
     *
     * @param freeTiles The set of available tiles where the boss can be placed.
     * @return The created boss entity.
     */
    private Entity createBoss(final Set<Pair<Integer, Integer>> freeTiles) {
        return bossFactory.createBoss(getRandomTile(freeTiles).getLeft().doubleValue(),
                getRandomTile(freeTiles).getRight().doubleValue());
    }

    /**
     * Calculates the number of enemies to generate based on the number of free tiles.
     *
     * @param numFreeTiles The number of free tiles in the room.
     * @return The number of enemies to generate.
     */
    private int calculateNumEnemies(final int numFreeTiles) {
        if (numFreeTiles < MAX_ENEMIES_PER_TILE) {
            throw new IllegalArgumentException("The number of free tiles is less than the entities spawned!");
        }
        int maxNumOfEnemies = Math.min(1, numFreeTiles / MAX_ENEMIES_PER_TILE);
        return random.nextInt(maxNumOfEnemies) + 1;
    }
}
