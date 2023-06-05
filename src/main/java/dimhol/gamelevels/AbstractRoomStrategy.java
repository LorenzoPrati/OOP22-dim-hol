package dimhol.gamelevels;

import dimhol.components.PlayerComponent;
import dimhol.components.PositionComponent;
import dimhol.entity.factories.GenericFactory;
import dimhol.entity.Entity;
import dimhol.entity.factories.InteractableObjectFactory;
import org.apache.commons.lang3.tuple.Pair;
import org.locationtech.jts.math.Vector2D;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public abstract class AbstractRoomStrategy implements RoomStrategy {
    private static final String NO_AVAILABLE_TILE = "No available tiles.";
    private static final String INVALID_DIMENSION = "Invalid entity dimensions! Dimensions must be greater than zero.";
    private static final int GATE_WIDTH = 3;
    private static final int GATE_HEIGHT = 3;
    private static final int NUM_GATE_ENTITIES = 1;
    private final GenericFactory genericFactory;
    private final InteractableObjectFactory interactableObjectFactory;

    protected AbstractRoomStrategy(final GenericFactory genericFactory, final InteractableObjectFactory interactableObjectFactory) {
        this.genericFactory = genericFactory;
        this.interactableObjectFactory = interactableObjectFactory;
    }

    /**
     * Validates that the set of available tiles is not empty.
     *
     * @param availableTiles The set of available tiles.
     * @throws NoAvailableTilesException if the set of available tiles is empty.
     */
    protected void validateAvailableTilesNotEmpty(final Set<Pair<Integer, Integer>> availableTiles)
            throws NoAvailableTilesException {
        if (availableTiles.isEmpty()) {
            throw new NoAvailableTilesException(NO_AVAILABLE_TILE);
        }
    }

    /**
     * Validates the dimensions of an entity.
     *
     * @param entityWidth  the width of the entity.
     * @param entityHeight the height of the entity.
     * @throws InvalidEntityDimensionsException if the entity dimensions are invalid (less than or equal to zero).
     */
    protected void validateEntityDimensions(final int entityWidth, final int entityHeight) throws InvalidEntityDimensionsException {
        if (entityWidth <= 0 || entityHeight <= 0) {
            throw new InvalidEntityDimensionsException(INVALID_DIMENSION);
        }
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
    protected boolean canAccomodate(final Pair<Integer, Integer> position, final Set<Pair<Integer, Integer>> occupiedTiles,
                                    final int entityWidth, final int entityHeight, final String entityName) {
        int startX = position.getLeft();
        int startY = position.getRight();

        for (int x = startX; x <= startX + entityWidth; x++) {
            for (int y = startY; y <= startY + entityHeight; y++) {
                if (isTileOccupied(x, y, occupiedTiles)) {
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
    protected boolean isTileOccupied(final int x, final int y, Set<Pair<Integer, Integer>> occupiedTiles) {
        return !occupiedTiles.contains(Pair.of(x, y));
    }

    /**
     * Generates the player entity and places it in the room.
     *
     * @param availableTiles    The set of available tiles where the player can be placed.
     * @param entities          The list of entities already present in the room.
     * @param newListOfEntities The list of entities to add the player to.
     */
    protected void generatePlayer(final Set<Pair<Integer, Integer>> availableTiles, final List<Entity> entities,
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
     */
    protected Pair<Integer, Integer> getRandomTile(final Set<Pair<Integer, Integer>> availableTiles) {
        validateAvailableTilesNotEmpty(availableTiles);
        int randomIndex = ThreadLocalRandom.current().nextInt(availableTiles.size());
        return availableTiles.stream().skip(randomIndex).findFirst().orElseThrow();
    }

    /**
     * Creates a player entity and assigns it a random position from the set of available tiles.
     *
     * @param availableTiles The set of available tiles where the player can be placed.
     * @return The created player entity.
     */
    protected Entity createAndPlacePlayer(final Set<Pair<Integer, Integer>> availableTiles) {
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
    protected Entity createPlayer(final Set<Pair<Integer, Integer>> availableTiles) {
        validateAvailableTilesNotEmpty(availableTiles);
        Pair<Integer, Integer> randomCoordinates = getRandomTile(availableTiles);
        double x = randomCoordinates.getLeft().doubleValue();
        double y = randomCoordinates.getRight().doubleValue();
        return genericFactory.createPlayer(x, y);
    }

    /**
     * Assigns a random position from the set of available tiles to the specified entity.
     *
     * @param entity         The entity to place.
     * @param availableTiles The set of available tiles where the entity can be placed.
     */
    protected void placeEntity(final Entity entity, final Set<Pair<Integer, Integer>> availableTiles) {
        PositionComponent positionComponent = (PositionComponent) entity.getComponent(PositionComponent.class);
        Pair<Integer, Integer> randomTile = getRandomTile(availableTiles);
        Vector2D position = new Vector2D(randomTile.getLeft(), randomTile.getRight());
        positionComponent.setPos(position);
    }

    /**
     * Generates the gate entity and places it in the room.
     *
     * @param availableTiles The set of available tiles where the gate can be placed.
     * @param entities       The list of entities to add the gate to.
     */
    protected void generateGate(final Set<Pair<Integer, Integer>> availableTiles, final List<Entity> entities) {
        calculateNumEntities(availableTiles.size());
        var gateAvailableTiles = getRandomTileForGate(availableTiles);
        Entity gate = interactableObjectFactory.
                createGate(gateAvailableTiles.getLeft().doubleValue(),
                        gateAvailableTiles.getRight().doubleValue());
        entities.add(gate);
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
     * Retrieves a random tile from the set of available tiles that can accommodate a gate interactable object.
     *
     * @param availableTiles the set of available tiles to choose from.
     * @return a randomly selected tile that can accommodate a gate interactable object.
     */
    protected Pair<Integer, Integer> getRandomTileForGate(final Set<Pair<Integer, Integer>> availableTiles) {
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
    protected boolean canAccommodateTileForGate(final Pair<Integer, Integer> tile,
                                                final Set<Pair<Integer, Integer>> availableTiles) {
        validateAvailableTilesNotEmpty(availableTiles);
        return canAccomodate(tile, availableTiles, GATE_WIDTH, GATE_HEIGHT, "Gate");
    }

}
