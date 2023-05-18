package dimhol.gamelevels;

import dimhol.components.PositionComponent;
import dimhol.entity.Entity;
import dimhol.entity.factories.EnemyFactory;
import dimhol.entity.factories.GenericFactory;
import org.apache.commons.lang3.tuple.Pair;
import org.locationtech.jts.math.Vector2D;

import java.util.*;
import java.util.stream.IntStream;

/**
 * The strategy to generate a normal room in the game.
 */
public class NormalRoomStrategy implements RoomStrategy {
    private final GenericFactory genericFactory;
    private final EnemyFactory enemyFactory;
    private final Random random;
    private static final int ENEMY_DENSITY = 50;
    private static final int MAX_ENEMIES = 10;

    /**
     * Constructs a NormalRoomStrategy.
     *
     * @param genericFactory The generic entity factory.
     * @param enemyFactory The enemy entity factory.
     * @param random The random number generator.
     */
    public NormalRoomStrategy(final GenericFactory genericFactory, final EnemyFactory enemyFactory, Random random) {
        this.genericFactory = genericFactory;
        this.enemyFactory = enemyFactory;
        this.random = random;
    }

    /**
     * Generates entities for the normal room.
     *
     * @param entity The main entity is the player of the game room.
     * @param freeTiles The set of available tiles within the room, where entities can be placed.
     * @return A list of generated entities.
     */
    @Override
    public final List<Entity> generate(final Optional<Entity> entity, final Set<Pair<Integer, Integer>> freeTiles) {

        List<Entity> entities = new ArrayList<>();

        //Place the player:
        Entity player = createAndPlacePlayer(freeTiles);
        entities.add(player);

        //Place the enemies:
        int numEnemies = calculateNumEnemies(freeTiles.size());
        generateZombies(numEnemies, entities, freeTiles);
        generateShooters(numEnemies, entities, freeTiles);

        //Place coins:


        return entities;
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
    private void generateZombies(int numZombies, List<Entity> entities, Set<Pair<Integer, Integer>> freeTiles) {
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
    private void generateShooters(int numShooters, List<Entity> entities, Set<Pair<Integer, Integer>> freeTiles) {
        IntStream.range(0, numShooters).mapToObj(i -> createShooter(freeTiles)).forEach(shooter -> {
            placeEntity(shooter, freeTiles);
            entities.add(shooter);
        });
    }

    /**
     * Generates the specified number of enemies and places them in the room.
     *
     * @param numEnemies The number of enemies to generate.
     * @param entities The list of entities to add the generated enemies to.
     * @param freeTiles The set of available tiles where enemies can be placed.
     */
    private void generateEnemies(final int numEnemies, final List<Entity> entities, final Set<Pair<Integer, Integer>> freeTiles) {
        IntStream.rangeClosed(0, numEnemies).forEach(i -> {
            Entity zombie = createZombie(freeTiles);
            Entity shooter = createShooter(freeTiles);
            placeEntity(zombie, freeTiles);
            placeEntity(shooter, freeTiles);
            entities.add(zombie);
            entities.add(shooter);
        });
    }

    /**
     * Creates a zombie enemy entity and assigns it a random position from the set of free tiles.
     *
     * @param freeTiles The set of available tiles where the enemy can be placed.
     * @return The created zombie enemy entity.
     */
    private Entity createZombie(Set<Pair<Integer, Integer>> freeTiles) {
        return enemyFactory.createZombie(getRandomTile(freeTiles).getLeft().doubleValue(),
                getRandomTile(freeTiles).getRight().doubleValue());
    }

    /**
     * Creates a shooter enemy entity and assigns it a random position from the set of free tiles.
     *
     * @param freeTiles The set of available tiles where the enemy can be placed.
     * @return The created shooter enemy entity.
     */
    private Entity createShooter(Set<Pair<Integer, Integer>> freeTiles) {
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
}
