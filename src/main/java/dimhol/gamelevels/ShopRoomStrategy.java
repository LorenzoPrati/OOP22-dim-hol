package dimhol.gamelevels;

import dimhol.entity.Entity;
import dimhol.entity.factories.GenericFactory;
import dimhol.entity.factories.InteractableObjectFactory;
import dimhol.entity.factories.ItemFactory;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

/**
 * This class represents a strategy for generating a shop room.
 * It implements the RoomStrategy interface.
 */
public class ShopRoomStrategy extends AbstractRoomStrategy {
    private static final int NUM_ITEMS = 10;
    private static final int NUM_POWER_UP = 2;
    private static final int ENTITY_WIDTH = 1;
    private static final int ENTITY_HEIGHT = 1;
    private final GenericFactory genericFactory;
    private final ItemFactory itemFactory;
    private final InteractableObjectFactory interactableObjectFactory;

    /**
     * Constructs a ShopRoomStrategy object with the specified generic factory and random number generator.
     *
     * @param genericFactory            The generic factory used for creating entities.
     * @param itemFactory               The item factory used to create items in the room.
     * @param interactableObjectFactory The interactable object factory used to create interactable objects in the room.
     */
    public ShopRoomStrategy(final GenericFactory genericFactory, final ItemFactory itemFactory,
                            final InteractableObjectFactory interactableObjectFactory) {
        super(genericFactory, interactableObjectFactory);
        this.itemFactory = itemFactory;
        this.interactableObjectFactory = new InteractableObjectFactory();
        this.genericFactory = new GenericFactory();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Entity> generate(final Optional<Entity> entity, final Set<Pair<Integer, Integer>> freeTiles,
                                 final List<Entity> entities) {

        List<Entity> newListOfEntities = new ArrayList<>();

        //Place the player:
        generatePlayer(freeTiles, entities, newListOfEntities, ENTITY_WIDTH, ENTITY_HEIGHT);

        //Place the shop-keeper:
        createShopKeeper(freeTiles, newListOfEntities);

        //Place coins:
        generateCoins(freeTiles, newListOfEntities);

        //Place heart:
        generateHearts(freeTiles, newListOfEntities);

        //Place heartPowerUp:
        generateHeartPowerUp(freeTiles, newListOfEntities);

        //Place velocityPowerUp:
        generateVelocityPowerUp(freeTiles, newListOfEntities);

        //Place gate:
        generateGate(freeTiles, newListOfEntities);


        return newListOfEntities;
    }

    private void generateHeartPowerUp(final Set<Pair<Integer, Integer>> freeTiles, final List<Entity> entities) {
        int heartPowerUp = new Random().nextInt(NUM_POWER_UP);
        for (int i = 0; i < heartPowerUp; i++) {
            var heartPowerUpFreeTiles = getRandomTile(freeTiles);
            entities.add(interactableObjectFactory.createShopHeart(heartPowerUpFreeTiles.getLeft().doubleValue(),
                    heartPowerUpFreeTiles.getRight().doubleValue()));
        }
    }

    private void generateVelocityPowerUp(final Set<Pair<Integer, Integer>> freeTiles, final List<Entity> entities) {
        int velocityPowerUp = new Random().nextInt(NUM_POWER_UP);
        for (int i = 0; i < velocityPowerUp; i++) {
            var velocityPowerUpFreeTiles = getRandomTile(freeTiles);
            entities.add(interactableObjectFactory.createShopVelocity(velocityPowerUpFreeTiles.getLeft().doubleValue(),
                    velocityPowerUpFreeTiles.getRight().doubleValue()));
        }
    }

    private void generateCoins(final Set<Pair<Integer, Integer>> freeTiles, final List<Entity> entities) {
        int coins = new Random().nextInt(NUM_ITEMS);
        for (int i = 0; i < coins; i++) {
            var coinsFreeTiles = getRandomTile(freeTiles);
            entities.add(itemFactory.createCoin(coinsFreeTiles.getLeft().doubleValue(),
                    coinsFreeTiles.getRight().doubleValue()));
        }
    }

    private void generateHearts(final Set<Pair<Integer, Integer>> freeTiles, final List<Entity> entities) {
        int hearts = new Random().nextInt(NUM_ITEMS);
        for (int i = 0; i < hearts; i++) {
            var hearthsFreeTile = getRandomTile(freeTiles);
            entities.add(itemFactory.createHeart(hearthsFreeTile.getLeft().doubleValue(),
                    hearthsFreeTile.getRight().doubleValue()));

        }
    }

    /**
     * Creates a shopkeeper entity with a random position from the set of free tiles.
     *
     * @param freeTiles The set of available tiles where the shopkeeper can be placed.
     * @param entities  The
     */
    private void createShopKeeper(final Set<Pair<Integer, Integer>> freeTiles, final List<Entity> entities) {
        var shopKeeperFreeTiles = getRandomTile(freeTiles);
        entities.add(genericFactory.createShopkeeper(shopKeeperFreeTiles.getLeft().doubleValue(),
                shopKeeperFreeTiles.getRight().doubleValue()));
    }
}
