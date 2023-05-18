package dimhol.gamelevels;

import dimhol.components.PositionComponent;
import dimhol.entity.Entity;
import dimhol.entity.factories.GenericFactory;
import org.apache.commons.lang3.tuple.Pair;
import org.locationtech.jts.math.Vector2D;

import java.util.*;

/**
 *
 */
public class ShopRoomStrategy implements RoomStrategy {
    private final GenericFactory genericFactory;
    private final Random random;

    /**
     *
     */
    public ShopRoomStrategy(final GenericFactory genericFactory, final Random random) {
        this.genericFactory = genericFactory;
        this.random = random;
    }

    @Override
    public List<Entity> generate(Optional<Entity> entity, Set<Pair<Integer, Integer>> freeTiles) {
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
    private Entity createPlayer(Set<Pair<Integer, Integer>> freeTiles) {
        return genericFactory.createPlayer(getRandomTile(freeTiles).getLeft().doubleValue(),
                getRandomTile(freeTiles).getRight().doubleValue());
    }

    /**
     * Assigns a random position from the set of free tiles to the specified entity.
     *
     * @param entity The entity to place.
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

    static Pair<Integer, Integer> findRandomFreeTiles(Set<Pair<Integer, Integer>> freeTiles, Random random) {
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
    private Entity createShopKeeper(Set<Pair<Integer, Integer>> freeTiles) {
        return genericFactory.createShopkeeper(getRandomTile(freeTiles).getLeft().doubleValue(),
                getRandomTile(freeTiles).getRight().doubleValue());
    }
}
