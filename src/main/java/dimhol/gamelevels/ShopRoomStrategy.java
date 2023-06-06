package dimhol.gamelevels;

import dimhol.entity.Entity;
import dimhol.entity.factories.EnemyFactory;
import dimhol.entity.factories.GenericFactory;
import dimhol.entity.factories.InteractableObjectFactory;
import dimhol.entity.factories.ItemFactory;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.IntStream;

/**
 * This class represents a strategy for generating a shop room.
 * It implements the RoomStrategy interface.
 */
public class ShopRoomStrategy extends AbstractRoomStrategy {
    private static final int NUM_ITEMS = 10;
    private static final int NUM_POWER_UP = 2;
    private static final int NUM_SHOP_KEEPER = 1;
    private static final int ENTITY_WIDTH = 1;
    private static final int ENTITY_HEIGHT = 1;
    private static final int NUM_INTERACTABLE = 1;
    private final GenericFactory genericFactory;
    private final InteractableObjectFactory interactableObjectFactory;

    /**
     * Constructs a ShopRoomStrategy object with the specified generic factory and random number generator.
     *
     * @param genericFactory            The generic factory used for creating generic entities.
     * @param enemyFactory              The enemy factory used for creating enemy entities.
     * @param itemFactory               The item factory used to create items in the room.
     * @param interactableObjectFactory The interactable object factory used to create interactable objects in the room.
     * @param random                    The random generator.
     */
    public ShopRoomStrategy(final GenericFactory genericFactory, final EnemyFactory enemyFactory, final ItemFactory itemFactory,
                            final InteractableObjectFactory interactableObjectFactory,
                            final Random random) {
        super(genericFactory, enemyFactory, itemFactory, interactableObjectFactory, random);
        this.interactableObjectFactory = new InteractableObjectFactory();
        this.genericFactory = new GenericFactory();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Entity> generate(final Optional<Entity> entity, final Set<Pair<Integer, Integer>> availableTiles,
                                 final List<Entity> entities) {

        final List<Entity> newListOfEntities = new ArrayList<>();

        //Place the player:
        generatePlayer(availableTiles, entities, newListOfEntities, ENTITY_WIDTH, ENTITY_HEIGHT);

        //Place the shop-keeper:
        generateShopKeeper(NUM_SHOP_KEEPER, availableTiles, entities);

        //Place coins:
        generateCoins(NUM_ITEMS, availableTiles, newListOfEntities);

        //Place heart:
        generateHearts(NUM_ITEMS, availableTiles, newListOfEntities);

        //Place heartPowerUp:
        generateHeartPowerUp(NUM_POWER_UP, availableTiles, newListOfEntities);

        //Place velocityPowerUp:
        generateVelocityPowerUp(NUM_POWER_UP, availableTiles, newListOfEntities);

        //Place gate:
        generateGate(NUM_INTERACTABLE, availableTiles, newListOfEntities);


        return newListOfEntities;
    }

    private void generateHeartPowerUp(final int numPowerUp,
                                      final Set<Pair<Integer, Integer>> availableTiles,
                                      final List<Entity> entities) {
        final List<Entity> heartPowerUp = IntStream.rangeClosed(1, numPowerUp)
                .mapToObj(i -> createHeartPowerUp(availableTiles))
                .peek(heartPower -> placeEntityAtRandomPosition(heartPower, availableTiles, ENTITY_WIDTH, ENTITY_HEIGHT))
                .toList();

        entities.addAll(heartPowerUp);
    }

    private Entity createHeartPowerUp(final Set<Pair<Integer, Integer>> availableTiles) {
        final Pair<Integer, Integer> randomCoordinates = getRandomTile(availableTiles);
        final double x = randomCoordinates.getLeft().doubleValue();
        final double y = randomCoordinates.getRight().doubleValue();
        return interactableObjectFactory.createShopHeart(x, y);
    }

    private void generateVelocityPowerUp(final int numPowerUp,
                                         final Set<Pair<Integer, Integer>> availableTiles,
                                         final List<Entity> entities) {
        final List<Entity> velocityPowerUp = IntStream.rangeClosed(1, numPowerUp)
                .mapToObj(i -> createVelocityPowerUp(availableTiles))
                .peek(velocityPower -> placeEntityAtRandomPosition(velocityPower, availableTiles, ENTITY_WIDTH, ENTITY_HEIGHT))
                .toList();

        entities.addAll(velocityPowerUp);
    }

    private Entity createVelocityPowerUp(final Set<Pair<Integer, Integer>> availableTiles) {
        final Pair<Integer, Integer> randomCoordinates = getRandomTile(availableTiles);
        final double x = randomCoordinates.getLeft().doubleValue();
        final double y = randomCoordinates.getRight().doubleValue();
        return interactableObjectFactory.createShopVelocity(x, y);
    }

    /**
     * Creates a coin shop-keeper entity and assigns it a random position from the set of available tiles.
     *
     * @param availableTiles The set of available tiles where the shop-keeper can be placed.
     * @return The created shop-keeper item entity.
     */
    private Entity createShopKeeper(final Set<Pair<Integer, Integer>> availableTiles) {
        final Pair<Integer, Integer> randomCoordinates = getRandomTile(availableTiles);
        final double x = randomCoordinates.getLeft().doubleValue();
        final double y = randomCoordinates.getRight().doubleValue();
        return genericFactory.createShopkeeper(x, y);
    }

    /**
     * Generates a heart item and places it in the room.
     *
     * @param numShopKeeper  The number of shop-keeper to generate.
     * @param availableTiles The set of available tiles where the hearts can be placed.
     * @param entities       The list of entities to add the hearts to.
     */
    private void generateShopKeeper(final int numShopKeeper, final Set<Pair<Integer, Integer>> availableTiles,
                                    final List<Entity> entities) {
        final List<Entity> shopKeepers = IntStream.range(0, numShopKeeper)
                .mapToObj(i -> createShopKeeper(availableTiles))
                .peek(keeper -> placeEntityAtRandomPosition(keeper, availableTiles, ENTITY_WIDTH, ENTITY_HEIGHT))
                .toList();

        entities.addAll(shopKeepers);
    }
}
