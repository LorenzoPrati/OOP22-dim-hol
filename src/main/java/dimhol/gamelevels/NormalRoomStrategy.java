package dimhol.gamelevels;

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
    private final GenericFactory genericFactory;
    private final EnemyFactory enemyFactory;
    private final ItemFactory itemFactory;
    private final InteractableObjectFactory interactableObjectFactory;

    private final Random random;

    /**
     * Constructs a NormalRoomStrategy.
     *
     * @param genericFactory The generic entity factory.
     * @param enemyFactory   The enemy entity factory.
     * @param random         The random number generator.
     */
    public NormalRoomStrategy(final GenericFactory genericFactory, final EnemyFactory enemyFactory,
                              final ItemFactory itemFactory,
                              final InteractableObjectFactory interactableObjectFactory,
                              final Random random) {
        this.genericFactory = genericFactory;
        this.enemyFactory = enemyFactory;
        this.itemFactory = itemFactory;
        this.interactableObjectFactory = interactableObjectFactory;
        this.random = new Random(random.nextInt());
    }

    static Pair<Integer, Integer> findRandomFreeTiles(final Set<Pair<Integer, Integer>> freeTiles, final Random random) {
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
     * Generates entities for the normal room.
     *
     * @param entity    The main entity is the player of the game room.
     * @param freeTiles The set of available tiles within the room, where entities can be placed.
     * @return A list of generated entities.
     */
    @Override
    public final List<Entity> generate(final Optional<Entity> entity, final Set<Pair<Integer, Integer>> freeTiles) {

        List<Entity> entities = new ArrayList<>();

        //Place the player:
        generatePlayer(freeTiles, entities);

        //Place the enemies:
        generateEnemies(freeTiles, entities);

        System.out.println(freeTiles.size());

        //Place coins:
        generateCoins(freeTiles, entities);

        //Place heart:
        generateHearts(freeTiles, entities);

        //Place gate:
        generateGate(freeTiles, entities);


        return entities;
    }

    private void generateGate(Set<Pair<Integer, Integer>> freeTiles, List<Entity> entities) {
        calculateNumEntities(freeTiles.size());
        var gateFreeTiles = getRandomTileForGate(freeTiles);
        Entity gate = interactableObjectFactory.createGate(gateFreeTiles.getLeft().doubleValue(), gateFreeTiles.getRight().doubleValue());
        entities.add(gate);
    }

    private void generatePlayer(Set<Pair<Integer, Integer>> freeTiles, List<Entity> entities) {
        Entity player = createAndPlacePlayer(freeTiles);
        entities.add(player);
    }

    private void generateEnemies(Set<Pair<Integer, Integer>> freeTiles, List<Entity> entities) {
        int numEnemies = calculateNumEnemies(freeTiles.size());
        generateZombies(numEnemies, entities, freeTiles);
        generateShooters(numEnemies, entities, freeTiles);
    }

    private void generateCoins(Set<Pair<Integer, Integer>> freeTiles, List<Entity> entities) {
        var coinsFreeTiles = getRandomTile(freeTiles);
        entities.add(itemFactory.createCoin(coinsFreeTiles.getLeft().doubleValue(),
                coinsFreeTiles.getRight().doubleValue()));
    }

    private void generateHearts(Set<Pair<Integer, Integer>> freeTiles, List<Entity> entities) {
        var heartsFreeTile = getRandomTile(freeTiles);
        entities.add(itemFactory.createHeart(heartsFreeTile.getLeft().doubleValue(),
                heartsFreeTile.getRight().doubleValue()));
    }

    /**
     * Calculates the number of enemies to generate based on the number of free tiles.
     *
     * @param numFreeTiles The number of free tiles in the room.
     * @return The number of enemies to generate.
     */
    private int calculateNumEnemies(final int numFreeTiles) {
        int maxEnemies = Math.min(MAX_ENEMIES, numFreeTiles / ENEMY_DENSITY);
        return random.nextInt(maxEnemies) + 1;
    }

    /**
     * Generates the specified number of zombie enemies and places them in the room.
     *
     * @param numZombies The number of zombies to generate.
     * @param entities   The list of entities to add the generated zombies to.
     * @param freeTiles  The set of available tiles where zombies can be placed.
     */
    private void generateZombies(final int numZombies, final List<Entity> entities,
                                 final Set<Pair<Integer, Integer>> freeTiles) {
        IntStream.range(0, numZombies).mapToObj(i -> createZombie(freeTiles)).forEach(zombie -> {
            placeEntity(zombie, freeTiles);
            entities.add(zombie);
        });
    }

    /**
     * Generates the specified number of shooter enemies and places them in the room.
     *
     * @param numShooters The number of shooters to generate.
     * @param entities    The list of entities to add the generated shooters to.
     * @param freeTiles   The set of available tiles where shooters can be placed.
     */
    private void generateShooters(final int numShooters, final List<Entity> entities,
                                  final Set<Pair<Integer, Integer>> freeTiles) {
        IntStream.range(0, numShooters).mapToObj(i -> createShooter(freeTiles)).forEach(shooter -> {
            placeEntity(shooter, freeTiles);
            entities.add(shooter);
        });
    }

    /**
     * Creates a zombie enemy entity and assigns it a random position from the set of free tiles.
     *
     * @param freeTiles The set of available tiles where the enemy can be placed.
     * @return The created zombie enemy entity.
     */
    private Entity createZombie(final Set<Pair<Integer, Integer>> freeTiles) {
        return enemyFactory.createZombie(getRandomTile(freeTiles).getLeft().doubleValue(),
                getRandomTile(freeTiles).getRight().doubleValue());
    }

    /**
     * Creates a shooter enemy entity and assigns it a random position from the set of free tiles.
     *
     * @param freeTiles The set of available tiles where the enemy can be placed.
     * @return The created shooter enemy entity.
     */
    private Entity createShooter(final Set<Pair<Integer, Integer>> freeTiles) {
        return enemyFactory.createShooter(getRandomTile(freeTiles).getLeft().doubleValue(),
                getRandomTile(freeTiles).getRight().doubleValue());
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
     * Calculates the number of entities to generate based on the number of free tiles and the entity dimensions.
     *
     * @param numFreeTiles The number of free tiles in the room.
     * @throws IllegalArgumentException if the number of free tiles is less than the entities spawned or the entity dimensions are invalid.
     */
    private int calculateNumEntities(final int numFreeTiles) {
        if (numFreeTiles < calculateRequiredTiles(GATE_WIDTH, GATE_HEIGHT)) {
            throw new IllegalArgumentException("Not enough free tiles to spawn the gate obj with the specified dimensions!");
        }
        int maxNumOfEntities = numFreeTiles / calculateRequiredTiles(GATE_WIDTH, GATE_HEIGHT);
        int totalEntityCount = Math.min(NUM_GATE_ENTITIES, maxNumOfEntities);
        return totalEntityCount > 0 ? totalEntityCount : 1;
    }


    /**
     * Calculates the number of tiles required to accommodate an entity with the specified dimensions.
     *
     * @param width  The width of the entity.
     * @param height The height of the entity.
     * @return The number of tiles required.
     * @throws IllegalArgumentException if the entity dimensions are invalid.
     */
    private int calculateRequiredTiles(int width, int height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Invalid entity dimensions!");
        }
        return width * height;
    }

    /**
     * Retrieves a random tile that can accommodate the gate object dimensions.
     *
     * @param freeTiles The set of free tiles in the room.
     * @return A random tile that can accommodate the gate object.
     * @throws IllegalStateException if no free tiles can accommodate the gate object.
     */
    private Pair<Integer, Integer> getRandomTileForGate(Set<Pair<Integer, Integer>> freeTiles) {
        List<Pair<Integer, Integer>> shuffledTiles = new ArrayList<>(freeTiles);
        Collections.shuffle(shuffledTiles);
        return shuffledTiles.stream()
                .filter(tile -> canAccommodateGate(tile, freeTiles))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No free tiles can accommodate the gate interactable obj."));
    }

    static boolean canAccommodate(Pair<Integer, Integer> tile, Set<Pair<Integer, Integer>> freeTiles, int width, int height, String entityName) {
        int startX = tile.getLeft();
        int startY = tile.getRight();

        for (int x = startX; x < startX + width; x++) {
            for (int y = startY; y < startY + height; y++) {
                if (!freeTiles.contains(Pair.of(x, y))) {
                    return false;
                }
            }
        }
        System.out.println(entityName + ": can be accommodated");
        return true;
    }

    private boolean canAccommodateGate(Pair<Integer, Integer> tile, Set<Pair<Integer, Integer>> freeTiles) {
        return canAccommodate(tile, freeTiles, GATE_WIDTH, GATE_HEIGHT, "Gate");
    }
}
