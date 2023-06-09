package dimhol.gamelevels;

import dimhol.components.PositionComponent;
import dimhol.entity.Entity;
import dimhol.entity.factories.BossFactory;
import dimhol.entity.factories.EnemyFactory;
import dimhol.entity.factories.GenericFactory;
import dimhol.entity.factories.InteractableObjectFactory;
import dimhol.entity.factories.ItemFactory;
import org.apache.commons.lang3.tuple.Pair;
import org.locationtech.jts.math.Vector2D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


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
public final class BossRoomStrategy extends AbstractRoomStrategy {

    private static final int PLAYER_ENTITY_WIDTH = 1;
    private static final int PLAYER_ENTITY_HEIGHT = 1;
    private static final int MINIONS_ENTITY_WIDTH = 1;
    private static final int MINIONS_ENTITY_HEIGHT = 1;
    private static final int BOSS_ENTITY_WIDTH = 4;
    private static final int BOSS_ENTITY_HEIGTH = 3;
    private static final Logger LOGGER = LoggerFactory.getLogger(BossRoomStrategy.class);
    private final BossFactory bossFactory;


    /**
     * Constructs a BossRoomStrategy.
     *
     * @param genericFactory            The factory for creating generic entities.
     * @param itemFactory               The factory for creating item entities.
     * @param enemyFactory              The factory for creating enemy entities.
     * @param interactableObjectFactory The factory for creating interactable objects entities.
     * @param randomGenerator           The random number generator.
     * @param bossFactory               The factory for creating boss entities.
     */
    public BossRoomStrategy(final GenericFactory genericFactory, final ItemFactory itemFactory,
                            final EnemyFactory enemyFactory,
                            final InteractableObjectFactory interactableObjectFactory,
                            final Random randomGenerator,
                            final BossFactory bossFactory) {
        super(genericFactory, enemyFactory, itemFactory, interactableObjectFactory, randomGenerator);
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
    public List<Entity> generate(final Optional<Entity> entity, final Set<Pair<Integer, Integer>> freeTiles,
                                 final List<Entity> entities) {

        final List<Entity> newListOfEntities = new ArrayList<>();

        // Place the player:
        generatePlayer(freeTiles, entities, newListOfEntities, PLAYER_ENTITY_WIDTH, PLAYER_ENTITY_HEIGHT);

        // Place the boss:
        generateAndPlaceBoss(freeTiles, newListOfEntities, BOSS_ENTITY_WIDTH, BOSS_ENTITY_HEIGTH);

        // Generate enemy waves:
        generateEnemies(freeTiles, newListOfEntities);

        //generate Minions:
        generateAndPlaceMinions(freeTiles, newListOfEntities, MINIONS_ENTITY_WIDTH, MINIONS_ENTITY_HEIGHT);

        return newListOfEntities;
    }

    /**
     * Generates and places minions entities in the room.
     *
     * @param freeTiles           The set of available tiles where the minions can be placed.
     * @param entities            The
     * @param minionsEntityWidth  The minions
     * @param minionsEntityHeight The minions
     */
    private void generateAndPlaceMinions(final Set<Pair<Integer, Integer>> freeTiles, final List<Entity> entities,
                                         final int minionsEntityWidth, final int minionsEntityHeight) {
        final Entity minions = createMinions(freeTiles);
        placeEntityAtRandomPosition(minions, freeTiles, minionsEntityWidth, minionsEntityHeight);
        entities.add(minions);
    }

    /**
     * Creates a coin minion entity and assigns it a random position from the set of available tiles.
     *
     * @param availableTiles The set of available tiles where the minion can be placed.
     * @return The created minion entity.
     */
    private Entity createMinions(final Set<Pair<Integer, Integer>> availableTiles) {
        final Pair<Integer, Integer> randomCoordinates = getRandomTile(availableTiles);
        final double x = randomCoordinates.getLeft();
        final double y = randomCoordinates.getRight();
        return bossFactory.createMinion(x, y);
    }

    /**
     * Generates and places the boss entity in a random position.
     *
     * @param availableTiles   The set of available tiles where the boss can be placed.
     * @param entities         The list of entities where the boss will be added.
     * @param bossEntityWidth  The
     * @param bossEntityHeight The
     */
    private void generateAndPlaceBoss(final Set<Pair<Integer, Integer>> availableTiles, final List<Entity> entities,
                                      final int bossEntityWidth, final int bossEntityHeight) {
        generateEntitiesWithExceptionHandling(() -> calculateNumEntities(availableTiles, BOSS_ENTITY_WIDTH, BOSS_ENTITY_HEIGTH),
                numBosses -> IntStream.range(0, numBosses).forEach(i -> {
                    final Entity boss = createBoss(availableTiles);
                    placeEntityWithDimension(boss, availableTiles, bossEntityWidth, bossEntityHeight, new Random());
                    entities.add(boss);
                }),
                this::handleEntityGenerationError
        );
    }

    /**
     * Places the entity at a random position with specified dimensions within the set of free tiles.
     *
     * @param entity          The entity to place.
     * @param freeTiles       The set of available tiles where the entity can be placed.
     * @param entityWidth     The width of the entity.
     * @param entityHeight    The height of the entity.
     * @param randomGenerator The random generator.
     */
    private void placeEntityWithDimension(final Entity entity, final Set<Pair<Integer, Integer>> freeTiles,
                                          final int entityWidth, final int entityHeight, final Random randomGenerator) {
        final List<Pair<Integer, Integer>> validTiles = findValidTilesWithDimension(freeTiles, entityWidth, entityHeight);
        if (!validTiles.isEmpty()) {
            final Pair<Integer, Integer> randomTile = validTiles.get(randomGenerator.nextInt(validTiles.size()));
            final PositionComponent positionComponent = (PositionComponent) entity.getComponent(PositionComponent.class);
            final Vector2D position = new Vector2D(randomTile.getLeft(), randomTile.getRight());
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
    private List<Pair<Integer, Integer>> findValidTilesWithDimension(final Set<Pair<Integer, Integer>> freeTiles,
                                                                     final int width,
                                                                     final int height) {
        final List<Pair<Integer, Integer>> validTiles = new ArrayList<>();
        for (final Pair<Integer, Integer> tile : freeTiles) {
            final int tileX = tile.getLeft();
            final int tileY = tile.getRight();
            boolean isValid = true;
            // Check if the dimensions of the entity fit within the current tile and its neighboring tiles
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    final Pair<Integer, Integer> currentTile = Pair.of(tileX + i, tileY + j);
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
     * Retrieves a random tile that can accommodate the boss entity's dimensions.
     *
     * @param freeTiles The set of free tiles in the room.
     * @return A random tile that can accommodate the boss entity.
     * @throws IllegalStateException if no free tiles can accommodate the boss entity.
     */
    private Pair<Integer, Integer> getRandomTileForBoss(final Set<Pair<Integer, Integer>> freeTiles) {
        final List<Pair<Integer, Integer>> shuffledTiles = new ArrayList<>(freeTiles);
        Collections.shuffle(shuffledTiles);
        return shuffledTiles.stream()
                .filter(tile -> canAccommodateTileForBoss(tile, freeTiles))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No free tiles can accommodate the boss entity."));
    }

    /**
     * Checks if a given tile can accommodate a Boss entity.
     *
     * @param tile           the tile to be checked for accommodation.
     * @param availableTiles the set of available tiles to check against.
     * @return true if the tile can accommodate a gate, false otherwise.
     */
    private boolean canAccommodateTileForBoss(final Pair<Integer, Integer> tile,
                                              final Set<Pair<Integer, Integer>> availableTiles) {
        validateAvailableTilesNotEmpty(availableTiles);
        return canAccommodate(tile, availableTiles, BOSS_ENTITY_WIDTH, BOSS_ENTITY_HEIGTH, "Boss");
    }


    /**
     * Retrieves the list of tiles occupied by the boss entity, based on its starting position.
     *
     * @param startPos The starting position of the boss entity.
     * @return The list of tiles occupied by the boss entity.
     */
    private List<Pair<Integer, Integer>> getBossTiles(final Pair<Integer, Integer> startPos) {
        final List<Pair<Integer, Integer>> bossTiles = new ArrayList<>();
        final int startX = startPos.getLeft();
        final int startY = startPos.getRight();

        for (int x = startX; x < startX + BOSS_ENTITY_WIDTH; x++) {
            for (int y = startY; y < startY + BOSS_ENTITY_HEIGTH; y++) {
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

        for (final Pair<Integer, Integer> tile : tiles) {
            totalX += tile.getLeft();
            totalY += tile.getRight();
        }

        final double centerX = totalX / tiles.size() + 0.5;
        final double centerY = totalY / tiles.size() + 0.5;

        return new Vector2D(centerX, centerY);
    }

    /**
     * Creates a boss entity with a random position, considering its tile dimensions.
     *
     * @param freeTiles The set of available tiles where the boss can be placed.
     * @return The created boss entity.
     */
    private Entity createBoss(final Set<Pair<Integer, Integer>> freeTiles) {
        final Pair<Integer, Integer> randomTile = getRandomTileForBoss(freeTiles);
        final List<Pair<Integer, Integer>> bossTiles = getBossTiles(randomTile);
        final Vector2D bossPosition = getTileCenterPosition(bossTiles);

        return bossFactory.createBoss(bossPosition.getX(), bossPosition.getY());
    }

    /**
     * Generates entities with exception handling.
     *
     * @param <T>                  the type of the entity count parameter
     * @param entityCountSupplier  the supplier for obtaining the entity count
     * @param entityGenerationFunc the consumer for generating entities based on the entity count
     * @param errorHandlingFunc    the consumer for handling any exceptions that occur during entity generation
     */
    private <T> void generateEntitiesWithExceptionHandling(
            final Supplier<T> entityCountSupplier,
            final Consumer<T> entityGenerationFunc,
            final Consumer<EntityGenerationException> errorHandlingFunc) {
        try {
            final T entityCount = entityCountSupplier.get();
            entityGenerationFunc.accept(entityCount);
        } catch (Exception e) {
            errorHandlingFunc.accept(new EntityGenerationException("Error generating entities", e));
        }
    }

    /**
     * Handles the error that occurs during entity generation.
     * The handleEntityGenerationError method is responsible for handling any exceptions that occur during entity generation.
     *
     * @param e The exception that occurred.
     */
    private void handleEntityGenerationError(final Exception e) {
        LOGGER.error("Error generating entities: " + e.getMessage(), e);
    }
}
