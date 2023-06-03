package dimhol.gamelevels;

import dimhol.components.PlayerComponent;
import dimhol.components.PositionComponent;
import dimhol.entity.Entity;
import dimhol.entity.factories.GenericFactory;
import dimhol.entity.factories.InteractableObjectFactory;
import dimhol.entity.factories.ItemFactory;
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
    private static final int NUM_ITEMS = 10;
    private static final int NUM_POWER_UP = 2;
    private final GenericFactory genericFactory;
    private final ItemFactory itemFactory;
    private final InteractableObjectFactory interactableObjectFactory;
    private final RandomWrapper random;

    /**
     * Constructs a ShopRoomStrategy object with the specified generic factory and random number generator.
     *
     * @param genericFactory            The generic factory used for creating entities.
     * @param itemFactory               The item factory used to create items in the room.
     * @param interactableObjectFactory The interactable object factory used to create interactable objects in the room.
     * @param random                    The random number generator used for generating random positions.
     */
    public ShopRoomStrategy(final GenericFactory genericFactory, final ItemFactory itemFactory,
                            final InteractableObjectFactory interactableObjectFactory,
                            final RandomWrapper random) {
        this.genericFactory = genericFactory;
        this.itemFactory = itemFactory;
        this.interactableObjectFactory = interactableObjectFactory;
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
    public List<Entity> generate(final Optional<Entity> entity, final Set<Pair<Integer, Integer>> freeTiles,
                                 final List<Entity> entities) {

        List<Entity> newListOfEntities = new ArrayList<>();

        //Place the player:
        generatePlayer(freeTiles, entities, newListOfEntities);

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
        int heartPowerUp = new RandomWrapper().nextInt(NUM_POWER_UP);
        for (int i = 0; i < heartPowerUp; i++) {
            var heartPowerUpFreeTiles = getRandomTile(freeTiles);
            entities.add(interactableObjectFactory.createShopHeart(heartPowerUpFreeTiles.getLeft().doubleValue(),
                    heartPowerUpFreeTiles.getRight().doubleValue()));
        }
    }

    private void generateVelocityPowerUp(final Set<Pair<Integer, Integer>> freeTiles, final List<Entity> entities) {
        int velocityPowerUp = new RandomWrapper().nextInt(NUM_POWER_UP);
        for (int i = 0; i < velocityPowerUp; i++) {
            var velocityPowerUpFreeTiles = getRandomTile(freeTiles);
            entities.add(interactableObjectFactory.createShopVelocity(velocityPowerUpFreeTiles.getLeft().doubleValue(),
                    velocityPowerUpFreeTiles.getRight().doubleValue()));
        }
    }

    private void generateCoins(final Set<Pair<Integer, Integer>> freeTiles, final List<Entity> entities) {
        int coins = new RandomWrapper().nextInt(NUM_ITEMS);
        for (int i = 0; i < coins; i++) {
            var coinsFreeTiles = getRandomTile(freeTiles);
            entities.add(itemFactory.createCoin(coinsFreeTiles.getLeft().doubleValue(),
                    coinsFreeTiles.getRight().doubleValue()));
        }
    }

    private void generateHearts(final Set<Pair<Integer, Integer>> freeTiles, final List<Entity> entities) {
        int hearts = new RandomWrapper().nextInt(NUM_ITEMS);
        for (int i = 0; i < hearts; i++) {
            var hearthsFreeTile = getRandomTile(freeTiles);
            entities.add(itemFactory.createHeart(hearthsFreeTile.getLeft().doubleValue(),
                    hearthsFreeTile.getRight().doubleValue()));

        }
    }

    private void generateGate(final Set<Pair<Integer, Integer>> freeTiles, final List<Entity> entities) {
        var gateFreeTile = getRandomTile(freeTiles);
        entities.add(interactableObjectFactory.createGate(gateFreeTile.getLeft().doubleValue(),
                gateFreeTile.getRight().doubleValue()));
    }

    /**
     * Creates a player entity and assigns it a random position from the set of free tiles.
     *
     * @param freeTiles The set of available tiles where the player can be placed.
     * @param entities The
     * @param newListOfEntities The
     */
    private void generatePlayer(final Set<Pair<Integer, Integer>> freeTiles, final List<Entity> entities,
                                final List<Entity> newListOfEntities) {
        Optional<Entity> existingEntity = entities.stream()
                .filter(entity -> entity.hasComponent(PlayerComponent.class))
                .findFirst();

        if (existingEntity.isPresent()) {
            var oldPlayer = existingEntity.get();
            var position = (PositionComponent) oldPlayer.getComponent(PositionComponent.class);
            oldPlayer.removeComponent(position);
            var playerTiles = getRandomTile(freeTiles);
            oldPlayer.addComponent(new PositionComponent(new Vector2D(playerTiles.getLeft().doubleValue(),
                    playerTiles.getRight().doubleValue()), 1));
            newListOfEntities.add(oldPlayer);
        } else {
            Entity player = createAndPlacePlayer(freeTiles);
            newListOfEntities.add(player);
        }
    }


    /**
     * Creates a player entity with a random position from the set of free tiles.
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
     * @param entities The
     */
    private void createShopKeeper(final Set<Pair<Integer, Integer>> freeTiles, final List<Entity> entities) {
        var shopKeeperFreeTiles = getRandomTile(freeTiles);
        entities.add(genericFactory.createShopkeeper(shopKeeperFreeTiles.getLeft().doubleValue(),
                shopKeeperFreeTiles.getRight().doubleValue()));
    }
}
