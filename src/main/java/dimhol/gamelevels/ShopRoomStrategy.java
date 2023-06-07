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
 */
public class ShopRoomStrategy extends AbstractRoomStrategy {
    private static final int NUM_ITEMS = 10;
    private static final int NUM_POWER_UP = 2;
    private static final int NUM_SHOP_KEEPER = 1;
    private static final int ENTITY_WIDTH = 1;
    private static final int ENTITY_HEIGHT = 1;
    private static final int NUM_INTERACTIVE = 1;
    private final GenericFactory genericFactory;
    private final InteractableObjectFactory interactableObjectFactory;

    /**
     * Constructs a ShopRoomStrategy object with the specified generic factory and random number generator.
     *
     * @param genericFactory            The generic factory used for creating generic entities.
     * @param enemyFactory              The enemy factory used for creating enemy entities.
     * @param itemFactory               The item factory used to create items in the room.
     * @param interactableObjectFactory The interactive object factory used to create interactive objects in the room.
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

        generatePlayer(availableTiles, entities, newListOfEntities, ENTITY_WIDTH, ENTITY_HEIGHT);

        generateShopKeeper(NUM_SHOP_KEEPER, availableTiles, newListOfEntities);

        generateCoins(NUM_ITEMS, availableTiles, newListOfEntities);

        generateHearts(NUM_ITEMS, availableTiles, newListOfEntities);

        generateHeartPowerUp(NUM_POWER_UP, availableTiles, newListOfEntities);

        generateVelocityPowerUp(NUM_POWER_UP, availableTiles, newListOfEntities);

        generateGate(NUM_INTERACTIVE, availableTiles, newListOfEntities);


        return newListOfEntities;
    }

    /**
     * Generates heart power-ups and adds them to the list of entities.
     *
     * @param numPowerUp     The number of heart power-ups to generate.
     * @param availableTiles The set of available tiles where the heart power-ups can be placed.
     * @param entities       The list of entities to add the heart power-ups to.
     */
    private void generateHeartPowerUp(final int numPowerUp,
                                      final Set<Pair<Integer, Integer>> availableTiles,
                                      final List<Entity> entities) {
        final List<Entity> heartPowerUp = IntStream.rangeClosed(1, numPowerUp)
                .mapToObj(i -> createHeartPowerUp(availableTiles))
                .peek(heartPower -> placeEntityAtRandomPosition(heartPower, availableTiles, ENTITY_WIDTH, ENTITY_HEIGHT))
                .toList();

        entities.addAll(heartPowerUp);
    }

    /**
     * Creates a heart power-up entity with a random position.
     *
     * @param availableTiles The set of available tiles where the heart power-up can be placed.
     * @return The created heart power-up entity.
     */
    private Entity createHeartPowerUp(final Set<Pair<Integer, Integer>> availableTiles) {
        final Pair<Integer, Integer> randomCoordinates = getRandomTile(availableTiles);
        final double x = randomCoordinates.getLeft().doubleValue();
        final double y = randomCoordinates.getRight().doubleValue();
        return interactableObjectFactory.createShopHeart(x, y);
    }

    /**
     * Generates velocity power-ups and adds them to the list of entities.
     *
     * @param numPowerUp     The number of velocity power-ups to generate.
     * @param availableTiles The set of available tiles where the velocity power-ups can be placed.
     * @param entities       The list of entities to add the velocity power-ups to.
     */
    private void generateVelocityPowerUp(final int numPowerUp,
                                         final Set<Pair<Integer, Integer>> availableTiles,
                                         final List<Entity> entities) {
        final List<Entity> velocityPowerUp = IntStream.rangeClosed(1, numPowerUp)
                .mapToObj(i -> createVelocityPowerUp(availableTiles))
                .peek(velocityPower -> placeEntityAtRandomPosition(velocityPower, availableTiles, ENTITY_WIDTH, ENTITY_HEIGHT))
                .toList();

        entities.addAll(velocityPowerUp);
    }

    /**
     * Creates a velocity power-up entity with a random position.
     *
     * @param availableTiles The set of available tiles where the velocity power-up can be placed.
     * @return The created velocity power-up entity.
     */
    private Entity createVelocityPowerUp(final Set<Pair<Integer, Integer>> availableTiles) {
        final Pair<Integer, Integer> randomCoordinates = getRandomTile(availableTiles);
        final double x = randomCoordinates.getLeft().doubleValue();
        final double y = randomCoordinates.getRight().doubleValue();
        return interactableObjectFactory.createShopVelocity(x, y);
    }

    /**
     * Creates a shop-keeper entity and assigns it a random position from the set of available tiles.
     *
     * @param availableTiles The set of available tiles where the shop-keeper can be placed.
     * @return The created shop-keeper entity.
     */
    private Entity createShopKeeper(final Set<Pair<Integer, Integer>> availableTiles) {
        final Pair<Integer, Integer> randomCoordinates = getRandomTile(availableTiles);
        final double x = randomCoordinates.getLeft().doubleValue();
        final double y = randomCoordinates.getRight().doubleValue();
        return genericFactory.createShopkeeper(x, y);
    }

    /**
     * Generates shop-keeper entities and places them in the room.
     *
     * @param numShopKeeper  The number of shop-keepers to generate.
     * @param availableTiles The set of available tiles where the shop-keepers can be placed.
     * @param entities       The list of entities to add the shop-keepers to.
     */
    private void generateShopKeeper(final int numShopKeeper, final Set<Pair<Integer, Integer>> availableTiles,
                                    final List<Entity> entities) {
        final List<Entity> shopKeepers = IntStream.range(0, numShopKeeper)
                .mapToObj(i -> createShopKeeper(availableTiles))
                .peek(shopkeeper -> placeEntityAtRandomPosition(shopkeeper, availableTiles, ENTITY_WIDTH, ENTITY_HEIGHT))
                .toList();

        entities.addAll(shopKeepers);
    }
}
