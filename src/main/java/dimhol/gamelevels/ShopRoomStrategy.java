package dimhol.gamelevels;

import dimhol.components.PositionComponent;
import dimhol.entity.Entity;
import dimhol.entity.factories.GenericFactory;
import org.apache.commons.lang3.tuple.Pair;
import org.locationtech.jts.math.Vector2D;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * This class represents a strategy for generating a shop room.
 * It implements the RoomStrategy interface.
 */
public class ShopRoomStrategy implements RoomStrategy {
    private final GenericFactory genericFactory;
    private final RandomWrapper random;

    /**
     * Constructs a ShopRoomStrategy object with the specified generic factory and random number generator.
     *
     * @param genericFactory The generic factory used for creating entities.
     * @param random         The random number generator used for generating random positions.
     */
    public ShopRoomStrategy(final GenericFactory genericFactory, final RandomWrapper random) {
        this.genericFactory = genericFactory;
        this.random = random;
    }

    /**
     * Finds a random free tile from the given set of free tiles.
     *
     * @param freeTiles The set of free tiles.
     * @param random    The random number generator.
     * @return A Pair representing the coordinates of the random free tile.
     * @throws IllegalStateException if no free tiles are available.
     */
    static Pair<Integer, Integer> findRandomFreeTiles(final Set<Pair<Integer, Integer>> freeTiles, final RandomWrapper random) {
        int randomIndex = random.nextInt(freeTiles.size());
        int currentIndex = 0;
        for (Pair<Integer, Integer> tile : freeTiles) {
            if (currentIndex == randomIndex) {
                return tile;
            }
            currentIndex++;
        }
        throw new IllegalStateException("No free tiles available");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Entity> generate(final Optional<Entity> entity, final Set<Pair<Integer, Integer>> freeTiles) {
        List<Entity> entities = new ArrayList<>();

        //Place the player:
        Entity player = createAndPlacePlayer(freeTiles);
        entities.add(player);

        //Place the shop-keeper:
        Entity shopKeeper = createShopKeeper(freeTiles);
        entities.add(shopKeeper);

        //Place coins:


        return entities;
    }

    /**
     * Creates a player entity and assigns it a random position from the set of free tiles.
     *
     * @param freeTiles The set of available tiles where the player can be placed.
     * @return The created player entity.
     */
    private Entity createAndPlacePlayer(final Set<Pair<Integer, Integer>> freeTiles) {
        Entity player = createPlayer(freeTiles);
        placeEntity(player, freeTiles);
        return player;
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
        PositionComponent positionComponent = (PositionComponent) entity.getComponent(PositionComponent.class);
        Pair<Integer, Integer> randomTile = getRandomTile(freeTiles);
        Vector2D position = new Vector2D(randomTile.getLeft(), randomTile.getRight());
        positionComponent.setPos(position);
    }

    /**
     * Retrieves a random tile from the set of free tiles.
     *
     * @param freeTiles The set of free tiles in the room.
     * @return A random tile represented as a Pair of coordinates.
     * @throws IllegalStateException if no free tiles are available.
     */
    private Pair<Integer, Integer> getRandomTile(final Set<Pair<Integer, Integer>> freeTiles) {
        return findRandomFreeTiles(freeTiles, random);
    }

    /**
     * Creates a shopkeeper entity with a random position from the set of free tiles.
     *
     * @param freeTiles The set of available tiles where the shopkeeper can be placed.
     * @return The created shopkeeper entity.
     */
    private Entity createShopKeeper(final Set<Pair<Integer, Integer>> freeTiles) {
        return genericFactory.createShopkeeper(getRandomTile(freeTiles).getLeft().doubleValue(),
                getRandomTile(freeTiles).getRight().doubleValue());
    }
}
