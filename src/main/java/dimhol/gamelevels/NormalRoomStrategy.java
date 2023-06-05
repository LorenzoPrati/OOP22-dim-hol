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
 * The strategy to generate a normal room in the game.
 */
public class NormalRoomStrategy extends AbstractRoomStrategy {
    private static final int ENEMY_DENSITY = 100;
    private static final int MAX_ENEMIES = 10;
    private static final int MAX_ITEMS = 5;
    private final EnemyFactory enemyFactory;
    private final ItemFactory itemFactory;

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
        super(genericFactory, interactableObjectFactory);
        this.enemyFactory = enemyFactory;
        this.itemFactory = itemFactory;
        this.randomGenerator = new Random(randomGenerator.nextInt());
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

        //Place the items:
        generateItems(availableTiles, newListOfEntities);

        //Place gate:
        generateGate(availableTiles, newListOfEntities);


        return newListOfEntities;
    }

    /**
     * Generates the enemies and places them in the room.
     *
     * @param availableTiles The set of available tiles where the enemies can be placed.
     * @param entities       The list of entities to add the enemies to.
     */
    private void generateEnemies(final Set<Pair<Integer, Integer>> availableTiles, final List<Entity> entities) {
        int numEnemies = calculateNumEnemies(availableTiles.size());
        generateZombies(numEnemies, availableTiles, entities);
        generateShooters(numEnemies, availableTiles, entities);
    }

    /**
     * Generates the items and places them in the room.
     *
     * @param availableTiles The set of available tiles where the items can be placed.
     * @param entities       The list of entities to add the items to.
     */
    private void generateItems(final Set<Pair<Integer, Integer>> availableTiles, final List<Entity> entities) {
        int numItems = calculateNumItems(availableTiles.size());
        generateCoins(numItems, availableTiles, entities);
        generateHearts(numItems, availableTiles, entities);

    }

    /**
     * Calculates the number of items to generate based on the number of available tiles.
     *
     * @param numberOfAvailableTiles The number of available tiles in the room.
     * @return The number of items to generate.
     */
    private int calculateNumItems(final int numberOfAvailableTiles) {
        int maxItems = Math.min(MAX_ITEMS, numberOfAvailableTiles);
        return randomGenerator.nextInt(maxItems + 1);
    }

    /**
     * Generates a coin item and places it in the room.
     *
     * @param numCoins       The number of coins to generate.
     * @param availableTiles The set of available tiles where the coins can be placed.
     * @param entities       The list of entities to add the coins to.
     */
    private void generateCoins(final int numCoins, final Set<Pair<Integer, Integer>> availableTiles,
                               final List<Entity> entities) {
        List<Entity> coins = IntStream.range(0, numCoins)
                .mapToObj(i -> createCoins(availableTiles))
                .peek(coin -> placeEntity(coin, availableTiles))
                .toList();

        entities.addAll(coins);
    }

    /**
     * Creates a coin item entity and assigns it a random position from the set of available tiles.
     *
     * @param availableTiles The set of available tiles where the coin can be placed.
     * @return The created coin item entity.
     */
    private Entity createCoins(final Set<Pair<Integer, Integer>> availableTiles) {
        Pair<Integer, Integer> randomCoordinates = getRandomTile(availableTiles);
        double x = randomCoordinates.getLeft().doubleValue();
        double y = randomCoordinates.getRight().doubleValue();
        return itemFactory.createCoin(x, y);
    }


    /**
     * Generates a heart item and places it in the room.
     *
     * @param numHeart       The number of hearts to generate.
     * @param availableTiles The set of available tiles where the hearts can be placed.
     * @param entities       The list of entities to add the hearts to.
     */
    private void generateHearts(final int numHeart, final Set<Pair<Integer, Integer>> availableTiles,
                                final List<Entity> entities) {
        List<Entity> coins = IntStream.range(0, numHeart)
                .mapToObj(i -> createHearts(availableTiles))
                .peek(coin -> placeEntity(coin, availableTiles))
                .toList();

        entities.addAll(coins);
    }

    /**
     * Creates a heart item entity and assigns it a random position from the set of available tiles.
     *
     * @param availableTiles The set of available tiles where the heart can be placed.
     * @return The created heart item entity.
     */
    private Entity createHearts(final Set<Pair<Integer, Integer>> availableTiles) {
        Pair<Integer, Integer> randomCoordinates = getRandomTile(availableTiles);
        double x = randomCoordinates.getLeft().doubleValue();
        double y = randomCoordinates.getRight().doubleValue();
        return itemFactory.createHeart(x, y);
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
    private void generateZombies(final int numZombies, final Set<Pair<Integer, Integer>> availableTiles,
                                 final List<Entity> entities) {
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
    private void generateShooters(final int numShooters, final Set<Pair<Integer, Integer>> availableTiles,
                                  final List<Entity> entities) {
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
}
