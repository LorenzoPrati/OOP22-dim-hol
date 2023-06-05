package dimhol.gamelevels;

import dimhol.components.PlayerComponent;
import dimhol.components.PositionComponent;
import dimhol.entity.Entity;
import dimhol.entity.factories.EnemyFactory;
import dimhol.entity.factories.GenericFactory;
import dimhol.entity.factories.InteractableObjectFactory;
import dimhol.entity.factories.ItemFactory;
import org.apache.commons.lang3.tuple.Pair;
import org.locationtech.jts.math.Vector2D;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

/**
 * The strategy to generate a normal room in the game.
 */
public class NormalRoomStrategy implements RoomStrategy {
    private static final int ENEMY_DENSITY = 100;
    private static final int MAX_ENEMIES = 10;
    private static final int NUM_GATE_ENTITIES = 1;
    private static final int GATE_WIDTH = 3;
    private static final int GATE_HEIGHT = 3;
    private static final String INVALID_DIMENSION = "Invalid entity dimensions! Dimensions must be greater than zero.";
    private static final String NO_AVAILABLE_TILE = "No available tiles.";
    private final GenericFactory genericFactory;
    private final EnemyFactory enemyFactory;
    private final ItemFactory itemFactory;
    private final InteractableObjectFactory interactableObjectFactory;

    private final Random randomGenerator;

    /**
     * Constructs a NormalRoomStrategy.
     *
     * @param genericFactory            The generic entity factory.
     * @param enemyFactory              The enemy entity factory.
     * @param itemFactory               The item factory.
     * @param interactableObjectFactory The interactable object factory.
     * @param randomGenerator           The random number generator.
     */
    public NormalRoomStrategy(final GenericFactory genericFactory, final EnemyFactory enemyFactory,
                              final ItemFactory itemFactory,
                              final InteractableObjectFactory interactableObjectFactory,
                              final Random randomGenerator) {
        this.genericFactory = genericFactory;
        this.enemyFactory = enemyFactory;
        this.itemFactory = itemFactory;
        this.interactableObjectFactory = interactableObjectFactory;
        this.randomGenerator = new Random(randomGenerator.nextInt());
    }

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
    static boolean canAccommodate(final Pair<Integer, Integer> position, final Set<Pair<Integer, Integer>> occupiedTiles,
                                  final int entityWidth, final int entityHeight, final String entityName) {
        int startX = position.getLeft();
        int startY = position.getRight();

        for (int x = startX; x <= startX + entityWidth; x++) {
            for (int y = startY; y <= startY + entityHeight; y++) {
                if (!isTileOccupied(x, y, occupiedTiles)) {
                    System.out.println(entityName + ": cannot be accommodated");
                    return false;
                }
            }
        }

        System.out.println(entityName + ": can be accommodated");
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
    static boolean isTileOccupied(final int x, final int y, final Set<Pair<Integer, Integer>> occupiedTiles) {
        return occupiedTiles.contains(Pair.of(x, y));
    }


    /**
     * Generates entities for the normal room.
     *
     * @param entity         The main entity is the player of the game room.
     * @param availableTiles The set of available tiles within the room, where entities can be placed.
     * @param entities       The list of entities already present in the room.
     * @return A list of generated entities.
     */
    @Override
    public final List<Entity> generate(final Optional<Entity> entity, final Set<Pair<Integer, Integer>> availableTiles,
                                       final List<Entity> entities) {
        List<Entity> newListOfEntities = new ArrayList<>();

        //Place the player:
        generatePlayer(availableTiles, entities, newListOfEntities);

        //Place the enemies:
        generateEnemies(availableTiles, newListOfEntities);

        //Place coins:
        generateCoins(availableTiles, newListOfEntities);

        //Place heart:
        generateHearts(availableTiles, newListOfEntities);

        //Place gate:
        generateGate(availableTiles, newListOfEntities);


        return newListOfEntities;
    }

    /**
     * Generates the gate entity and places it in the room.
     *
     * @param availableTiles The set of available tiles where the gate can be placed.
     * @param entities       The list of entities to add the gate to.
     */
    private void generateGate(final Set<Pair<Integer, Integer>> availableTiles, final List<Entity> entities) {
        calculateNumEntities(availableTiles.size());
        var gateAvailableTiles = getRandomTileForGate(availableTiles);
        Entity gate = interactableObjectFactory.
                createGate(gateAvailableTiles.getLeft().doubleValue(),
                        gateAvailableTiles.getRight().doubleValue());
        entities.add(gate);
    }

    /**
     * Generates the player entity and places it in the room.
     *
     * @param availableTiles    The set of available tiles where the player can be placed.
     * @param entities          The list of entities already present in the room.
     * @param newListOfEntities The list of entities to add the player to.
     */
    private void generatePlayer(final Set<Pair<Integer, Integer>> availableTiles, final List<Entity> entities,
                                final List<Entity> newListOfEntities) {
        Optional<Entity> existingPlayer = findPlayerEntity(entities);

        existingPlayer.ifPresent(player -> {
            PositionComponent position = (PositionComponent) player.getComponent(PositionComponent.class);
            player.removeComponent(position);
            Pair<Integer, Integer> playerTiles = getRandomTile(availableTiles);
            player.addComponent(new PositionComponent(new Vector2D(playerTiles.getLeft().doubleValue(),
                    playerTiles.getRight().doubleValue()), 1));
            newListOfEntities.add(player);
        });

        if (existingPlayer.isEmpty()) {
            Entity newPlayer = createAndPlacePlayer(availableTiles);
            newListOfEntities.add(newPlayer);
        }
    }

    /**
     * Finds the player entity in the list of entities.
     *
     * @param entities The list of entities to search for the player.
     * @return An optional containing the player entity if found, otherwise an empty optional.
     */
    private Optional<Entity> findPlayerEntity(final List<Entity> entities) {
        return entities.stream()
                .filter(entity -> entity.hasComponent(PlayerComponent.class))
                .findFirst();
    }

    /**
     * Generates the enemies and places them in the room.
     *
     * @param availableTiles The set of available tiles where the enemies can be placed.
     * @param entities       The list of entities to add the enemies to.
     */
    private void generateEnemies(final Set<Pair<Integer, Integer>> availableTiles, final List<Entity> entities) {
        int numEnemies = calculateNumEnemies(availableTiles.size());
        generateZombies(numEnemies, entities, availableTiles);
        generateShooters(numEnemies, entities, availableTiles);
    }

    /**
     * Generates a coin item and places it in the room.
     *
     * @param availableTiles The set of available tiles where the coin can be placed.
     * @param entities       The list of entities to add the coin to.
     */
    private void generateCoins(final Set<Pair<Integer, Integer>> availableTiles, final List<Entity> entities) {
        var coinsAvailableTile = getRandomTile(availableTiles);
        entities.add(itemFactory.createCoin(coinsAvailableTile.getLeft().doubleValue(),
                coinsAvailableTile.getRight().doubleValue()));
    }

    /**
     * Generates a heart item and places it in the room.
     *
     * @param availableTiles The set of available tiles where the heart can be placed.
     * @param entities       The list of entities to add the heart to.
     */
    private void generateHearts(final Set<Pair<Integer, Integer>> availableTiles, final List<Entity> entities) {
        var heartsAvailableTile = getRandomTile(availableTiles);
        entities.add(itemFactory.createHeart(heartsAvailableTile.getLeft().doubleValue(),
                heartsAvailableTile.getRight().doubleValue()));
    }

    /**
     * Calculates the number of enemies to generate based on the number of available tiles.
     *
     * @param numberOfAvailableTiles The number of available tiles in the room.
     * @return The number of enemies to generate.
     */
    private int calculateNumEnemies(final int numberOfAvailableTiles) {
        int maxEnemies = Math.min(MAX_ENEMIES, numberOfAvailableTiles / ENEMY_DENSITY);
        return randomGenerator.nextInt(maxEnemies) + 1;
    }

    /**
     * Generates the specified number of zombie enemies and places them in the room.
     *
     * @param numZombies     The number of zombies to generate.
     * @param entities       The list of entities to add the generated zombies to.
     * @param availableTiles The set of available tiles where zombies can be placed.
     */
    private void generateZombies(final int numZombies, final List<Entity> entities,
                                 final Set<Pair<Integer, Integer>> availableTiles) {
        List<Entity> zombies = IntStream.range(0, numZombies)
                .mapToObj(i -> createZombie(availableTiles))
                .peek(zombie -> placeEntity(zombie, availableTiles))
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
    private void generateShooters(final int numShooters, final List<Entity> entities,
                                  final Set<Pair<Integer, Integer>> availableTiles) {
        List<Entity> shooters = IntStream.range(0, numShooters)
                .mapToObj(i -> createShooter(availableTiles))
                .peek(shooter -> placeEntity(shooter, availableTiles))
                .toList();

        entities.addAll(shooters);
    }

    /**
     * Creates a zombie enemy entity and assigns it a random position from the set of available tiles.
     *
     * @param availableTiles The set of available tiles where the enemy can be placed.
     * @return The created zombie enemy entity.
     */
    private Entity createZombie(final Set<Pair<Integer, Integer>> availableTiles) {
        Pair<Integer, Integer> randomCoordinates = getRandomTile(availableTiles);
        double x = randomCoordinates.getLeft().doubleValue();
        double y = randomCoordinates.getRight().doubleValue();
        return enemyFactory.createZombie(x, y);
    }


    /**
     * Creates a shooter enemy entity and assigns it a random position from the set of available tiles.
     *
     * @param availableTiles The set of available tiles where the enemy can be placed.
     * @return The created shooter enemy entity.
     */
    private Entity createShooter(final Set<Pair<Integer, Integer>> availableTiles) {
        Pair<Integer, Integer> randomCoordinates = getRandomTile(availableTiles);
        double x = randomCoordinates.getLeft().doubleValue();
        double y = randomCoordinates.getRight().doubleValue();
        return enemyFactory.createShooter(x, y);
    }

    /**
     * Creates a player entity and assigns it a random position from the set of available tiles.
     *
     * @param availableTiles The set of available tiles where the player can be placed.
     * @return The created player entity.
     */
    private Entity createAndPlacePlayer(final Set<Pair<Integer, Integer>> availableTiles) {
        Entity player = createPlayer(availableTiles);
        placeEntity(player, availableTiles);
        return player;
    }

    /**
     * Creates a player entity with a random position from the set of available tiles.
     *
     * @param availableTiles The set of available tiles where the player can be placed.
     * @return The created player entity.
     */
    private Entity createPlayer(final Set<Pair<Integer, Integer>> availableTiles) {
        validateAvailableTilesNotEmpty(availableTiles);
        Pair<Integer, Integer> randomCoordinates = getRandomTile(availableTiles);
        double x = randomCoordinates.getLeft().doubleValue();
        double y = randomCoordinates.getRight().doubleValue();
        return genericFactory.createPlayer(x, y);
    }

    /**
     * Validates that the set of available tiles is not empty.
     *
     * @param availableTiles The set of available tiles.
     * @throws NoAvailableTilesException if the set of available tiles is empty.
     */
    private void validateAvailableTilesNotEmpty(final Set<Pair<Integer, Integer>> availableTiles)
            throws NoAvailableTilesException {
        if (availableTiles.isEmpty()) {
            throw new NoAvailableTilesException(NO_AVAILABLE_TILE);
        }
    }

    /**
     * Assigns a random position from the set of available tiles to the specified entity.
     *
     * @param entity         The entity to place.
     * @param availableTiles The set of available tiles where the entity can be placed.
     */
    private void placeEntity(final Entity entity, final Set<Pair<Integer, Integer>> availableTiles) {
        PositionComponent positionComponent = (PositionComponent) entity.getComponent(PositionComponent.class);
        Pair<Integer, Integer> randomTile = getRandomTile(availableTiles);
        Vector2D position = new Vector2D(randomTile.getLeft(), randomTile.getRight());
        positionComponent.setPos(position);
    }

    /**
     * Retrieves a random tile from the set of available tiles.
     *
     * @param availableTiles The set of available tiles.
     * @return A random tile.
     */
    private Pair<Integer, Integer> getRandomTile(final Set<Pair<Integer, Integer>> availableTiles) {
        validateAvailableTilesNotEmpty(availableTiles);
        int randomIndex = ThreadLocalRandom.current().nextInt(availableTiles.size());
        return availableTiles.stream().skip(randomIndex).findFirst().orElseThrow();
    }


    /**
     * Calculates the number of entities that can be placed based on the available tiles.
     *
     * @param numAvailableTiles the number of available tiles.
     * @return the calculated number of entities.
     */
    private int calculateNumEntities(final int numAvailableTiles) {
        int maxNumOfEntities = numAvailableTiles / calculateRequiredTiles(GATE_WIDTH, GATE_HEIGHT);
        int totalEntityCount = Math.min(NUM_GATE_ENTITIES, maxNumOfEntities);
        return totalEntityCount > 0 ? totalEntityCount : 1;
    }


    /**
     * Calculates the number of tiles required to accommodate an entity with the specified dimensions.
     *
     * @param entityWidth  The width of the entity in tiles.
     * @param entityHeight The height of the entity in tiles.
     * @return The number of tiles required to accommodate the entity.
     */
    private int calculateRequiredTiles(final int entityWidth, final int entityHeight) {
        validateEntityDimensions(entityWidth, entityHeight);
        return entityWidth * entityHeight;
    }

    /**
     * Validates the dimensions of an entity.
     *
     * @param entityWidth  the width of the entity.
     * @param entityHeight the height of the entity.
     * @throws InvalidEntityDimensionsException if the entity dimensions are invalid (less than or equal to zero).
     */
    private void validateEntityDimensions(final int entityWidth, final int entityHeight) throws InvalidEntityDimensionsException {
        if (entityWidth <= 0 || entityHeight <= 0) {
            throw new InvalidEntityDimensionsException(INVALID_DIMENSION);
        }
    }

    /**
     * Retrieves a random tile from the set of available tiles that can accommodate a gate interactable object.
     *
     * @param availableTiles the set of available tiles to choose from.
     * @return a randomly selected tile that can accommodate a gate interactable object.
     */
    private Pair<Integer, Integer> getRandomTileForGate(final Set<Pair<Integer, Integer>> availableTiles) {
        validateAvailableTilesNotEmpty(availableTiles);
        List<Pair<Integer, Integer>> shuffledTiles = new ArrayList<>(availableTiles);
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
    private boolean canAccommodateTileForGate(final Pair<Integer, Integer> tile,
                                              final Set<Pair<Integer, Integer>> availableTiles) {
        validateAvailableTilesNotEmpty(availableTiles);
        return canAccommodate(tile, availableTiles, GATE_WIDTH, GATE_HEIGHT, "Gate");
    }
}
