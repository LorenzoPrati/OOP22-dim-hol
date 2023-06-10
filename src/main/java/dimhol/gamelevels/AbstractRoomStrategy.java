package dimhol.gamelevels;

import dimhol.components.PlayerComponent;
import dimhol.components.PositionComponent;
import dimhol.entity.factories.EnemyFactory;
import dimhol.entity.factories.GenericFactory;
import dimhol.entity.Entity;
import dimhol.entity.factories.InteractableObjectFactory;
import dimhol.entity.factories.ItemFactory;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.locationtech.jts.math.Vector2D;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

/**
 * An abstract class to use common methods between the room strategy's.
 */
public abstract class AbstractRoomStrategy implements RoomStrategy {
    private static final int GATE_WIDTH = 3;
    private static final int GATE_HEIGHT = 3;
    private static final int MAX_ITEMS = 5;
    private static final int ITEM_WIDTH = 1;
    private static final int ITEM_HEIGHT = 1;
    private static final int MIN_ENTITIES = 1;
    private static final int ENTITY_WIDTH = 1;
    private static final int ENTITY_HEIGHT = 1;
    private static final int ENEMY_TILE_DENSITY = 100;
    private static final int MAX_ENEMIES = 10;
    private final GenericFactory genericFactory;
    private final ItemFactory itemFactory;
    private final InteractableObjectFactory interactableObjectFactory;
    private final Random randomGenerator;
    private final EnemyFactory enemyFactory;

    /**
     * Constructs an abstract class used by all the room strategies.
     *
     * @param genericFactory            The generic entity factory.
     * @param enemyFactory              The enemy factory.
     * @param itemFactory               The item factory.
     * @param interactableObjectFactory The interactable objects factory.
     * @param randomGenerator           The random generator.
     */
    protected AbstractRoomStrategy(final GenericFactory genericFactory,
                                   final EnemyFactory enemyFactory,
                                   final ItemFactory itemFactory,
                                   final InteractableObjectFactory interactableObjectFactory,
                                   final Random randomGenerator) {
        this.genericFactory = genericFactory;
        this.enemyFactory = enemyFactory;
        this.interactableObjectFactory = interactableObjectFactory;
        this.itemFactory = itemFactory;
        this.randomGenerator = new Random(randomGenerator.nextInt());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract List<Entity> generate(Optional<Entity> entity, Set<Pair<Integer, Integer>> availableTiles,
                                          List<Entity> entities);

    /**
     * Checks if an entity can be accommodated at the given position without overlapping with occupied tiles.
     *
     * @param position      The position of the entity.
     * @param occupiedTiles The set of occupied tiles.
     * @param entityWidth   The width of the entity.
     * @param entityHeight  The height of the entity.
     * @param entityName    The name of the entity.
     * @return True if the entity can be accommodated, false otherwise.
     */
    protected boolean canAccommodate(final Pair<Integer, Integer> position,
                                     final Set<Pair<Integer, Integer>> occupiedTiles,
                                     final int entityWidth,
                                     final int entityHeight,
                                     final String entityName) {
        final int startX = position.getLeft();
        final int startY = position.getRight();

        for (int x = startX; x <= startX + entityWidth; x++) {
            for (int y = startY; y <= startY + entityHeight; y++) {
                if (isTileOccupied(x, y, occupiedTiles)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if a tile is occupied by an entity.
     *
     * @param x             The x-coordinate of the tile.
     * @param y             The y-coordinate of the tile.
     * @param occupiedTiles The set of occupied tiles.
     * @return True if the tile is occupied, false otherwise.
     */
    protected boolean isTileOccupied(final int x, final int y, final Set<Pair<Integer, Integer>> occupiedTiles) {
        return !occupiedTiles.contains(Pair.of(x, y));
    }

    /**
     * Generates the player entity and places it in the room.
     *
     * @param availableTiles    The set of available positions where the player can be placed.
     * @param existingEntities  The list of entities already present in the room.
     * @param updatedEntityList The list of entities to add the player to.
     * @param playerWidth       The width of the player entity.
     * @param playerHeight      The height of the player entity.
     */
    protected void generatePlayer(final Set<Pair<Integer, Integer>> availableTiles,
                                  final List<Entity> existingEntities,
                                  final List<Entity> updatedEntityList,
                                  final int playerWidth,
                                  final int playerHeight) {
        final Optional<Entity> existingPlayer = findPlayerEntity(existingEntities);

        if (existingPlayer.isPresent()) {
            final Entity player = existingPlayer.get();
            placeEntityAtRandomPosition(player, availableTiles, ENTITY_WIDTH, ENTITY_HEIGHT);
            updatedEntityList.add(player);

        } else {
            final Entity newPlayer = createAndPlacePlayer(availableTiles, playerWidth, playerHeight);
            updatedEntityList.add(newPlayer);
        }

    }

    /**
     * Finds the player entity in the list of entities.
     *
     * @param entities The list of entities to search for the player.
     * @return An optional containing the player entity if found, otherwise an empty optional.
     */
    protected Optional<Entity> findPlayerEntity(final List<Entity> entities) {
        return entities.stream()
                .filter(entity -> entity.hasComponent(PlayerComponent.class))
                .findFirst();
    }

    /**
     * Retrieves a random tile from the set of available tiles.
     *
     * @param availableTiles The set of available tiles.
     * @return A random tile.
     * @throws IllegalArgumentException if availableTiles is null or empty.
     */
    protected Pair<Integer, Integer> getRandomTile(final Set<Pair<Integer, Integer>> availableTiles) {
        final int randomIndex = ThreadLocalRandom.current().nextInt(availableTiles.size());
        return availableTiles.stream()
                .skip(randomIndex)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }


    /**
     * Creates a player entity with a random position from the set of free tiles.
     *
     * @param freeTiles    The set of available tiles where the player can be placed.
     * @param playerWidth  The width of the player entity in tiles.
     * @param playerHeight The height of the player entity in tiles.
     * @return The created player entity.
     */
    protected Entity createAndPlacePlayer(final Set<Pair<Integer, Integer>> freeTiles,
                                          final int playerWidth, final int playerHeight) {
        final Entity player = createPlayer(freeTiles);
        placeEntityAtRandomPosition(player, freeTiles, playerWidth, playerHeight);
        return player;
    }

    /**
     * Creates a player entity with a random position from the set of available tiles.
     *
     * @param availableTiles The set of available tiles where the player can be placed.
     * @return The created player entity.
     */
    protected Entity createPlayer(final Set<Pair<Integer, Integer>> availableTiles) {
        final Pair<Integer, Integer> randomCoordinates = getRandomTile(availableTiles);
        final double x = randomCoordinates.getLeft().doubleValue();
        final double y = randomCoordinates.getRight().doubleValue();
        return genericFactory.createPlayer(x, y);
    }

    /**
     * Finds all available positions in the set of free tiles where an entity of given width and height can be placed.
     *
     * @param availableTiles The set of available tiles where the entity can be placed.
     * @param width          The width of the entity in tiles.
     * @param height         The height of the entity in tiles.
     * @return A list of available positions where the entity can be placed.
     */
    protected List<ImmutablePair<Integer, Integer>> findAvailablePositions(final Set<Pair<Integer, Integer>> availableTiles,
                                                                           final int width, final int height) {
        final List<ImmutablePair<Integer, Integer>> availablePositions = new ArrayList<>();
        for (final Pair<Integer, Integer> tile : availableTiles) {
            final int startX = tile.getLeft();
            final int startY = tile.getRight();
            final int endX = startX + width - 1;
            final int endY = startY + height - 1;
            boolean positionOccupied = false;
            for (int i = startX; i <= endX && !positionOccupied; i++) {
                for (int j = startY; j <= endY && !positionOccupied; j++) {
                    if (!availableTiles.contains(ImmutablePair.of(i, j))) {
                        positionOccupied = true;
                        break;
                    }
                }
            }
            if (!positionOccupied) {
                availablePositions.add(ImmutablePair.of(startX, startY));
            }
        }
        return availablePositions;
    }

    /**
     * Assigns a random position from the set of free tiles to the specified entity.
     *
     * @param entity         The entity to place.
     * @param entityWidth    The
     * @param entityHeight   The
     * @param availableTiles The set of available tiles where the entity can be placed.
     */
    protected void placeEntityAtRandomPosition(final Entity entity, final Set<Pair<Integer, Integer>> availableTiles,
                                               final int entityWidth, final int entityHeight) {
        final List<ImmutablePair<Integer, Integer>> availablePositions = new LinkedList<>(
                findAvailablePositions(availableTiles, entityWidth, entityHeight));
        if (!availablePositions.isEmpty()) {
            final Pair<Integer, Integer> position = getRandomTile(availableTiles);
            final double x = position.getLeft().doubleValue();
            final double y = position.getRight().doubleValue();
            final var pos = (PositionComponent) entity.getComponent(PositionComponent.class);
            pos.setPos(new Vector2D(x, y));

            availableTiles.remove(position);
        }
    }


    /**
     * Generates the gate entity and places it in the room.
     *
     * @param numGates       The number of gate entities to be generated.
     * @param availableTiles The set of available tiles where the gate can be placed.
     * @param entities       The list of entities to add the gate to.
     */
    protected void generateGate(final int numGates, final Set<Pair<Integer, Integer>> availableTiles,
                                final List<Entity> entities) {
        final List<Entity> gates = IntStream.range(0, numGates)
                .mapToObj(i -> createGate(availableTiles))
                .peek(gate -> placeEntityAtRandomPosition(gate, availableTiles, GATE_WIDTH, GATE_HEIGHT))
                .toList();

        entities.addAll(gates);
    }

    /**
     * Creates the gate interactable object entity.
     *
     * @param availableTiles The number of available tiles.
     * @return the gate entity.
     */
    protected Entity createGate(final Set<Pair<Integer, Integer>> availableTiles) {
        final Pair<Integer, Integer> randomCoordinates = getRandomTileForGate(availableTiles);
        final double x = randomCoordinates.getLeft().doubleValue();
        final double y = randomCoordinates.getRight().doubleValue();
        return interactableObjectFactory.createGate(x, y);
    }

    /**
     * Calculates the number of entities that can be placed based on the available tiles.
     *
     * @param availableTileCoordinates the coordinates of the available tiles.
     * @param entityWidth              the width of the entity.
     * @param entityHeight             the height of the entity.
     * @return the calculated number of entities.
     */
    protected int calculateNumEntities(final Set<Pair<Integer, Integer>> availableTileCoordinates,
                                       final int entityWidth,
                                       final int entityHeight) {
        final int numAvailableTiles = availableTileCoordinates.size();
        final int maxNumOfEntities = numAvailableTiles / calculateRequiredTiles(entityWidth, entityHeight);
        final int totalEntityCount = Math.min(MIN_ENTITIES, maxNumOfEntities);

        return totalEntityCount > 0 ? totalEntityCount : MIN_ENTITIES;
    }

    /**
     * Calculates the number of enemies based on the given parameters.
     *
     * @param numFreeTiles      The number of available tiles.
     * @param entityWidth       The width of the entity.
     * @param entityHeight      The height of the entity.
     * @param maxEnemiesPerTile The maximum number of enemies allowed per tile.
     * @param limitEnemies      The maximum limit for the number of enemies.
     * @return The calculated number of enemies.
     */
    protected int calculateNumEnemies(final int numFreeTiles,
                                      final int entityWidth, final int entityHeight,
                                      final int maxEnemiesPerTile,
                                      final int limitEnemies) {

        final int requiredTiles = calculateRequiredTiles(entityWidth, entityHeight);

        // Calculate the maximum number of enemies based on available tiles and entity size
        final int maxNumOfEnemies = (numFreeTiles - requiredTiles) / maxEnemiesPerTile;

        // Limit the maximum number of enemies
        final int maxAllowedEnemies = Math.min(maxNumOfEnemies, limitEnemies);

        // Generate a random number of enemies between 1 and the maximum allowed
        return getRandomNumber(1, maxAllowedEnemies);
    }

    /**
     * Generates a random number between the specified minimum and maximum values (inclusive).
     *
     * @param min The minimum value.
     * @param max The maximum value.
     * @return The random number generated.
     */
    protected int getRandomNumber(final int min, final int max) {
        return randomGenerator.nextInt(max - min + 1) + min;
    }

    /**
     * Creates a zombie enemy entity and assigns it a random position from the set of available tiles.
     *
     * @param availableTiles The set of available tiles where the enemy can be placed.
     * @return The created zombie enemy entity.
     */
    protected Entity createZombie(final Set<Pair<Integer, Integer>> availableTiles) {
        final Pair<Integer, Integer> randomCoordinates = getRandomTile(availableTiles);
        final double x = randomCoordinates.getLeft().doubleValue();
        final double y = randomCoordinates.getRight().doubleValue();
        return enemyFactory.createZombie(x, y);
    }


    /**
     * Creates a shooter enemy entity and assigns it a random position from the set of available tiles.
     *
     * @param availableTiles The set of available tiles where the enemy can be placed.
     * @return The created shooter enemy entity.
     */
    protected Entity createShooter(final Set<Pair<Integer, Integer>> availableTiles) {
        final Pair<Integer, Integer> randomCoordinates = getRandomTile(availableTiles);
        final double x = randomCoordinates.getLeft().doubleValue();
        final double y = randomCoordinates.getRight().doubleValue();
        return enemyFactory.createShooter(x, y);
    }

    /**
     * Generates the enemies and places them in the room.
     *
     * @param availableTiles The set of available tiles where the enemies can be placed.
     * @param entities       The list of entities to add the enemies to.
     */
    protected void generateEnemies(final Set<Pair<Integer, Integer>> availableTiles, final List<Entity> entities) {
        final var numFreeTiles = availableTiles.size();
        final int numEnemies = calculateNumEnemies(numFreeTiles, ENTITY_WIDTH, ENTITY_HEIGHT, ENEMY_TILE_DENSITY, MAX_ENEMIES);
        generateZombies(numEnemies, availableTiles, entities);
        generateShooters(numEnemies, availableTiles, entities);
    }

    /**
     * Generates the specified number of zombie enemies and places them in the room.
     *
     * @param numZombies     The number of zombies to generate.
     * @param entities       The list of entities to add the generated zombies to.
     * @param availableTiles The set of available tiles where zombies can be placed.
     */
    protected void generateZombies(final int numZombies, final Set<Pair<Integer, Integer>> availableTiles,
                                   final List<Entity> entities) {
        final List<Entity> zombies = IntStream.range(0, numZombies)
                .mapToObj(i -> createZombie(availableTiles))
                .peek(zombie -> placeEntityAtRandomPosition(zombie, availableTiles, ENTITY_WIDTH, ENTITY_HEIGHT))
                .toList();

        entities.addAll(zombies);
    }

    /**
     * Generates the specified number of shooter enemies and places them in the room.
     *
     * @param numShooters    The number of shooters to generate.
     * @param entities       The list of entities to add the generated shooters to.
     * @param availableTiles The set of available tiles where shooters can be placed.
     */
    protected void generateShooters(final int numShooters, final Set<Pair<Integer, Integer>> availableTiles,
                                    final List<Entity> entities) {
        final List<Entity> shooters = IntStream.range(0, numShooters)
                .mapToObj(i -> createShooter(availableTiles))
                .peek(shooter -> placeEntityAtRandomPosition(shooter, availableTiles, ENTITY_WIDTH, ENTITY_HEIGHT))
                .toList();

        entities.addAll(shooters);
    }

    /**
     * Calculates the number of tiles required to accommodate an entity with the specified dimensions.
     *
     * @param entityWidth  The width of the entity in tiles.
     * @param entityHeight The height of the entity in tiles.
     * @return The number of tiles required to accommodate the entity.
     */
    protected int calculateRequiredTiles(final int entityWidth, final int entityHeight) {
        return entityWidth * entityHeight;
    }

    /**
     * Retrieves a random tile from the set of available tiles that can accommodate a gate interactable object.
     *
     * @param availableTiles the set of available tiles to choose from.
     * @return a randomly selected tile that can accommodate a gate interactable object.
     */
    protected Pair<Integer, Integer> getRandomTileForGate(final Set<Pair<Integer, Integer>> availableTiles) {
        final List<Pair<Integer, Integer>> shuffledTiles = new ArrayList<>(availableTiles);
        Collections.shuffle(shuffledTiles);
        return shuffledTiles.stream()
                .filter(tile -> canAccommodateTileForGate(tile, availableTiles))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("No available tiles can accommodate the gate interactable obj."));
    }

    /**
     * Checks if a given tile can accommodate a gate.
     *
     * @param tile           the tile to be checked for accommodation.
     * @param availableTiles the set of available tiles to check against.
     * @return true if the tile can accommodate a gate, false otherwise.
     */
    protected boolean canAccommodateTileForGate(final Pair<Integer, Integer> tile,
                                                final Set<Pair<Integer, Integer>> availableTiles) {
        return canAccommodate(tile, availableTiles, GATE_WIDTH, GATE_HEIGHT, "Gate");
    }

    /**
     * Generates the items and places them in the room.
     *
     * @param availableTiles The set of available tiles where the items can be placed.
     * @param entities       The list of entities to add the items to.
     */
    protected void generateItems(final Set<Pair<Integer, Integer>> availableTiles, final List<Entity> entities) {
        final int numItems = calculateNumItems(availableTiles.size(), MAX_ITEMS);
        generateCoins(numItems, availableTiles, entities);
        generateHearts(numItems, availableTiles, entities);

    }

    /**
     * Calculates the number of items to generate based on the number of available tiles.
     *
     * @param numberOfAvailableTiles The number of available tiles in the room.
     * @param maxItems               The maximum number of items to be generated.
     * @return The number of items to generate.
     */
    protected int calculateNumItems(final int numberOfAvailableTiles, final int maxItems) {
        final int numMaxItems = Math.min(maxItems, numberOfAvailableTiles);
        return randomGenerator.nextInt(numMaxItems + 1);
    }

    /**
     * Generates a coin item and places it in the room.
     *
     * @param numCoins       The number of coins to generate.
     * @param availableTiles The set of available tiles where the coins can be placed.
     * @param entities       The list of entities to add the coins to.
     */
    protected void generateCoins(final int numCoins, final Set<Pair<Integer, Integer>> availableTiles,
                                 final List<Entity> entities) {
        final List<Entity> coins = IntStream.range(0, numCoins)
                .mapToObj(i -> createCoins(availableTiles))
                .peek(coin -> placeEntityAtRandomPosition(coin, availableTiles, ITEM_WIDTH, ITEM_HEIGHT))
                .toList();

        entities.addAll(coins);
    }

    /**
     * Creates a coin item entity and assigns it a random position from the set of available tiles.
     *
     * @param availableTiles The set of available tiles where the coin can be placed.
     * @return The created coin item entity.
     */
    protected Entity createCoins(final Set<Pair<Integer, Integer>> availableTiles) {
        final Pair<Integer, Integer> randomCoordinates = getRandomTile(availableTiles);
        final double x = randomCoordinates.getLeft().doubleValue();
        final double y = randomCoordinates.getRight().doubleValue();
        return itemFactory.createCoin(x, y);
    }

    /**
     * Generates a heart item and places it in the room.
     *
     * @param numHeart       The number of hearts to generate.
     * @param availableTiles The set of available tiles where the hearts can be placed.
     * @param entities       The list of entities to add the hearts to.
     */
    protected void generateHearts(final int numHeart, final Set<Pair<Integer, Integer>> availableTiles,
                                  final List<Entity> entities) {
        final List<Entity> coins = IntStream.range(0, numHeart)
                .mapToObj(i -> createHearts(availableTiles))
                .peek(coin -> placeEntityAtRandomPosition(coin, availableTiles, ITEM_WIDTH, ITEM_HEIGHT))
                .toList();

        entities.addAll(coins);
    }

    /**
     * Creates a heart item entity and assigns it a random position from the set of available tiles.
     *
     * @param availableTiles The set of available tiles where the heart can be placed.
     * @return The created heart item entity.
     */
    protected Entity createHearts(final Set<Pair<Integer, Integer>> availableTiles) {
        final Pair<Integer, Integer> randomCoordinates = getRandomTile(availableTiles);
        final double x = randomCoordinates.getLeft().doubleValue();
        final double y = randomCoordinates.getRight().doubleValue();
        return itemFactory.createHeart(x, y);
    }
}
