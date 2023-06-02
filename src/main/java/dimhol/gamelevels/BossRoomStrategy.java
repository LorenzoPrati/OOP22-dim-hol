package dimhol.gamelevels;

import dimhol.components.PositionComponent;
import dimhol.components.PowerComponent;
import dimhol.entity.Entity;
import dimhol.entity.factories.BossFactory;
import dimhol.entity.factories.EnemyFactory;
import dimhol.entity.factories.GenericFactory;
import org.apache.commons.lang3.tuple.ImmutablePair;
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
    private static final int NUM_ENEMIES_PER_WAVE = 3;
    private static final int NUM_ENEMY_WAVES = 3;
    private static final int MAX_ENEMIES_PER_TILE = 100;
    private static final int NUM_BOSS_ENTITIES = 1;
    private static final int BOSS_ENTITY_WIDTH = 4;
    private static final int BOSS_ENTITY_HEIGHT = 3;
    private static final int PLAYER_ENTITY_WIDTH = 1;
    private static final int PLAYER_ENTITY_HEIGHT = 1;
    private static final int ENEMY_ENTITY_WIDTH = 1;
    private static final int ENEMY_ENTITY_HEIGHT = 1;
    private static final int MINIONS_ENTITY_WIDTH = 1;
    private static final int MINIONS_ENTITY_HEIGHT = 1;
    private final GenericFactory genericFactory;
    private final EnemyFactory enemyFactory;
    private final BossFactory bossFactory;

    /**
     * Constructs a BossRoomStrategy.
     *
     * @param genericFactory The factory for creating generic entities.
     * @param enemyFactory The factory for creating enemy entities.
     * @param bossFactory The factory for creating boss entities.
     */
    public BossRoomStrategy(final GenericFactory genericFactory, final EnemyFactory enemyFactory,
                            final BossFactory bossFactory) {
        this.genericFactory = genericFactory;
        this.enemyFactory = enemyFactory;
        this.bossFactory = bossFactory;
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
        generateAndPlacePlayer(freeTiles, entities, PLAYER_ENTITY_WIDTH, PLAYER_ENTITY_HEIGHT);

        // Place the boss:
        generateAndPlaceBoss(freeTiles, entities, BOSS_ENTITY_WIDTH, BOSS_ENTITY_HEIGHT);

        // Generate enemy waves:
        generateAndPlaceEnemyWaves(freeTiles, entities, ENEMY_ENTITY_WIDTH, ENEMY_ENTITY_HEIGHT);

        //generate Minions:
        generateAndPlaceMinions(freeTiles, entities, MINIONS_ENTITY_WIDTH, MINIONS_ENTITY_HEIGHT);

        return entities;
    }

    /**
     * Generates and places minions entities in the room.
     *
     * @param freeTiles           The set of available tiles where the minions can be placed.
     * @param entities           The
     * @param minionsEntityWidth The minions
     * @param minionsEntityHeight The minions
     */
    private void generateAndPlaceMinions(final Set<Pair<Integer, Integer>> freeTiles, final List<Entity> entities,
                                         final int minionsEntityWidth, final int minionsEntityHeight) {
        Entity minions = createMinions(freeTiles);
        placeEntityAtRandomPosition(minions, freeTiles, minionsEntityWidth, minionsEntityHeight);
        entities.add(minions);
    }

    /**
     * Creates a minion entity.
     *
     * @param freeTiles The set of available tiles where the minion can be placed.
     * @return The created minion entity.
     */
    private Entity createMinions(final Set<Pair<Integer, Integer>> freeTiles) {
        var minionsFreeTiles = getRandomTile(freeTiles);
        return bossFactory.createMinion(minionsFreeTiles.getLeft().doubleValue(),
                minionsFreeTiles.getRight().doubleValue());
    }

    /**
     * Generates and places the player entity in a random position.
     *
     * @param freeTiles The set of available tiles where the player can be placed.
     * @param entities The
     * @param playerEntityWidth The
     * @param playerEntityHeight The
     */
    private Entity generateAndPlacePlayer(final Set<Pair<Integer, Integer>> freeTiles, final List<Entity> entities,
                                        final int playerEntityWidth, final int playerEntityHeight) {
        Optional<Entity> existingPlayer = entities.stream()
                .filter(entity -> entity.hasComponent(PositionComponent.class))
                .findFirst();

        if (existingPlayer.isPresent()) {
            return existingPlayer.get();
        } else {
            Entity player = createPlayer(freeTiles);
            placeEntityAtRandomPosition(player, freeTiles, playerEntityWidth, playerEntityHeight);
            entities.add(player);
            return player;
        }
    }

    /**
     * Generates and places the boss entity in a random position.
     *
     * @param freeTiles The set of available tiles where the boss can be placed.
     * @param entities  The list of entities where the boss will be added.
     * @param bossEntityWidth The
     * @param bossEntityHeight The
     */
    private void generateAndPlaceBoss(final Set<Pair<Integer, Integer>> freeTiles, final List<Entity> entities,
                                      final int bossEntityWidth, final int bossEntityHeight) {
        generateEntitiesWithExceptionHandling(() -> calculateNumEntities(freeTiles.size()), // Supplier to calculate the number of entities
                numBosses -> IntStream.range(0, numBosses).forEach(i -> {
                    Entity boss = createBoss(freeTiles); // Create the boss entity
                    placeEntityWithDimension(boss, freeTiles, bossEntityWidth, bossEntityHeight);
                    entities.add(boss); // Add the boss entity to the list of entities
                }),
                this::handleEntityGenerationError // Error handling consumer
        );
    }

    /**
     * Places the entity at a random position with specified dimensions within the set of free tiles.
     *
     * @param entity    The entity to place.
     * @param freeTiles The set of available tiles where the entity can be placed.
     * @param entityWidth     The width of the entity.
     * @param entityHeight    The height of the entity.
     */
    private void placeEntityWithDimension(final Entity entity, final Set<Pair<Integer, Integer>> freeTiles, final int entityWidth, final int entityHeight) {
        List<Pair<Integer, Integer>> validTiles = findValidTilesWithDimension(freeTiles, entityWidth, entityHeight);
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
    private List<Pair<Integer, Integer>> findValidTilesWithDimension(final Set<Pair<Integer, Integer>> freeTiles, final int width, final int height) {
        List<Pair<Integer, Integer>> validTiles = new ArrayList<>();
        for (Pair<Integer, Integer> tile : freeTiles) {
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
        if (numFreeTiles < calculateRequiredTiles(BOSS_ENTITY_WIDTH, BOSS_ENTITY_HEIGHT)) {
            throw new IllegalArgumentException("Not enough free tiles to spawn the boss entity with the specified dimensions!");
        }
        int maxNumOfEntities = numFreeTiles / calculateRequiredTiles(BOSS_ENTITY_WIDTH, BOSS_ENTITY_HEIGHT);
        int totalEntityCount = Math.min(NUM_BOSS_ENTITIES, maxNumOfEntities);
        return totalEntityCount > 0 ? totalEntityCount : 1;
    }

    /**
     * Calculates the number of tiles required to accommodate an entity with the specified dimensions.
     *
     * @param entityWidth  The width of the entity.
     * @param entityHight The height of the entity.
     * @return The number of tiles required.
     * @throws IllegalArgumentException if the entity dimensions are invalid.
     */
    private int calculateRequiredTiles(final int entityWidth, final int entityHight) {
        if (entityWidth <= 0 || entityHight <= 0) {
            throw new IllegalArgumentException("Invalid entity dimensions!");
        }
        return entityWidth * entityHight;
    }

    /**
     * Retrieves a random tile that can accommodate the boss entity's dimensions.
     *
     * @param freeTiles The set of free tiles in the room.
     * @return A random tile that can accommodate the boss entity.
     * @throws IllegalStateException if no free tiles can accommodate the boss entity.
     */
    private Pair<Integer, Integer> getRandomTileForBoss(final Set<Pair<Integer, Integer>> freeTiles) {
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
    private boolean canAccommodate(final Pair<Integer, Integer> tile, final Set<Pair<Integer, Integer>> freeTiles,
                                   final int entityWidth, final int entityHeight, final String entityName) {
        return NormalRoomStrategy.canAccommodate(tile, freeTiles, entityWidth, entityHeight, entityName);
    }

    private boolean canAccommodateBoss(Pair<Integer, Integer> tile, Set<Pair<Integer, Integer>> freeTiles) {
        return canAccommodate(tile, freeTiles, BOSS_ENTITY_WIDTH, BOSS_ENTITY_HEIGHT, "Boss");
    }


    /**
     * Retrieves the list of tiles occupied by the boss entity, based on its starting position.
     *
     * @param startPos The starting position of the boss entity.
     * @return The list of tiles occupied by the boss entity.
     */
    private List<Pair<Integer, Integer>> getBossTiles(final Pair<Integer, Integer> startPos) {
        List<Pair<Integer, Integer>> bossTiles = new ArrayList<>();
        int startX = startPos.getLeft();
        int startY = startPos.getRight();

        for (int x = startX; x < startX + BOSS_ENTITY_WIDTH; x++) {
            for (int y = startY; y < startY + BOSS_ENTITY_HEIGHT; y++) {
                bossTiles.add(Pair.of(x, y));
            }
        }

        return bossTiles;
    }

    /**
     * Calculates the center position of a group of tiles.
     *
     * @param tiles The group of tiles.
     * @return The center position as a Vector2D.
     */
    private Vector2D getTileCenterPosition(final List<Pair<Integer, Integer>> tiles) {
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

        return bossFactory.createBoss(bossPosition.getX(), bossPosition.getY());
    }

    /**
     * Generates and places the enemy waves in random positions.
     *
     * @param freeTiles The set of available tiles where the enemies can be placed.
     * @param entities  The list of entities where the enemies will be added.
     */
    private void generateAndPlaceEnemyWaves(final Set<Pair<Integer, Integer>> freeTiles, final List<Entity> entities, final int width, final int height) {
        generateEntitiesWithExceptionHandling(() -> calculateNumEnemies(freeTiles.size()),
                numEnemies -> IntStream.range(0, NUM_ENEMY_WAVES).forEach(wave -> IntStream.range(0, numEnemies)
                        .mapToObj(i -> createEnemy(freeTiles, wave, width, height)).forEach(enemy -> {
                            placeEntityAtRandomPosition(enemy, freeTiles, width, height);
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
    private Entity createEnemy(final Set<Pair<Integer, Integer>> freeTiles, final int wave, final int width, final int heigth) {
        Entity enemy = wave % 2 == 0
                ?
                enemyFactory.createZombie(0, 0)
                :
                enemyFactory.createShooter(0, 0);
        setEnemyPower(enemy, wave);
        placeEntity(enemy, freeTiles, width, heigth);
        return enemy;
    }

    /**
     * Assigns a random position from the set of free tiles to the specified entity.
     *
     * @param entity    The entity to place.
     * @param freeTiles The set of available tiles where the entity can be placed.
     */
    private void placeEntityAtRandomPosition(final Entity entity, final Set<Pair<Integer, Integer>> freeTiles, final int width,
                                             final int height) {
        List<ImmutablePair<Integer, Integer>> availablePositions = findAvailablePositions(freeTiles, width, height);
        if (!availablePositions.isEmpty()) {
            int randomIndex = new Random().nextInt(availablePositions.size());
            Pair<Integer, Integer> position = availablePositions.get(randomIndex);
            int x = position.getLeft(); //Get the x-coordinate
            int y = position.getRight(); //Get the y-coordinate
            var pos = (PositionComponent) entity.getComponent(PositionComponent.class);
            pos.setPos(new Vector2D(x,y));

            freeTiles.remove(position);
        }
    }

        /**

         Finds all available positions in the set of free tiles where an entity of given width and height can be placed.
         @param freeTiles The set of available tiles where the entity can be placed.
         @param width The width of the entity in tiles.
         @param height The height of the entity in tiles.
         @return A list of available positions where the entity can be placed.
         */
        private List<ImmutablePair<Integer, Integer>> findAvailablePositions(Set<Pair<Integer, Integer>> freeTiles,
                                                                             int width, int height) {
            List<ImmutablePair<Integer, Integer>> availablePositions = new ArrayList<>();
            for (Pair<Integer, Integer> tile : freeTiles) {
                int startX = tile.getLeft();
                int startY = tile.getRight();
                boolean positionOccupied = false;
                for (int i = startX; i < startX + width; i++) {
                    for (int j = startY; j < startY + height; j++) {
                        if (!freeTiles.contains(ImmutablePair.of(i, j))) {
                            positionOccupied = true;
                            break;
                        }
                    }
                    if (positionOccupied) {
                        break;
                    }
                }
                if (!positionOccupied) {
                    availablePositions.add(ImmutablePair.of(tile.getLeft(), tile.getRight()));
                }
            }
            return availablePositions;
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
        var playerFreeTile = getRandomTile(freeTiles);
        return genericFactory.createPlayer(playerFreeTile.getLeft().doubleValue(),
                playerFreeTile.getRight().doubleValue());
    }

    /**
     * Assigns a random position from the set of free tiles to the specified entity.
     *
     * @param entity    The entity to place.
     * @param freeTiles The set of available tiles where the entity can be placed.
     */
    private void placeEntity(final Entity entity, final Set<Pair<Integer, Integer>> freeTiles, final int width,
                             final int height) {
        placeEntityAtRandomPosition(entity, freeTiles, width, height);
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
        return BOSS_ENTITY_WIDTH * BOSS_ENTITY_HEIGHT;
    }
}
