package dimhol.gamelevels;

import dimhol.components.PositionComponent;
import dimhol.components.PowerComponent;
import dimhol.entity.Entity;
import dimhol.entity.factories.BossFactory;
import dimhol.entity.factories.EnemyFactory;
import dimhol.entity.factories.GenericFactory;
import org.apache.commons.lang3.tuple.Pair;
import org.locationtech.jts.math.Vector2D;

import java.util.*;
import java.util.stream.IntStream;

/**
 * BossRoomStrategy is responsible for generating entities for a boss room in the game.
 * It generates the player, boss, and enemy waves for the room.
 */
public class BossRoomStrategy implements RoomStrategy {

    public static final int POWER = 10;
    private final GenericFactory genericFactory;
    private final EnemyFactory enemyFactory;
    private final BossFactory bossFactory;
    private final Random random;

    public BossRoomStrategy(final GenericFactory genericFactory, final EnemyFactory enemyFactory,final BossFactory bossFactory, final Random random) {
        this.genericFactory = genericFactory;
        this.enemyFactory = enemyFactory;
        this.bossFactory = bossFactory;
        this.random = random;
    }

    @Override
    public List<Entity> generate(Optional<Entity> entity, Set<Pair<Integer, Integer>> freeTiles) {
        List<Entity> entities = new ArrayList<>();

        //Place the player:
        Entity player = createAndPlacePlayer(freeTiles);
        entities.add(player);

        //Place the boss:
        int numBoss = calculateNumEnemies(freeTiles.size());
        generateBoss(numBoss, entities, freeTiles);

        //Generate enemy waves:
        generateEnemyWaves(3, entities, freeTiles);

        return entities;
    }


    /**
     * Generates enemy waves and places them in the room.
     *
     * @param numWaves   The number of enemy waves to generate.
     * @param entities   The list of entities to add the generated enemies to.
     * @param freeTiles  The set of available tiles where enemies can be placed.
     */
    private void generateEnemyWaves(int numWaves, List<Entity> entities, Set<Pair<Integer, Integer>> freeTiles) {
        IntStream.rangeClosed(1, numWaves).forEach(wave -> {
            int numEnemies = calculateNumEnemies(freeTiles.size());
            generateEnemies(numEnemies, entities, freeTiles, wave);
        });
    }

    /**
     * Generates the specified number of enemies and places them in the room.
     *
     * @param numEnemies The number of enemies to generate.
     * @param entities   The list of entities to add the generated enemies to.
     * @param freeTiles  The set of available tiles where enemies can be placed.
     * @param wave       The wave number of enemies being generated.
     */
    private void generateEnemies(int numEnemies, List<Entity> entities, Set<Pair<Integer, Integer>> freeTiles, int wave) {
        generateZombie(numEnemies, entities, freeTiles, wave);
//        generateShooter(numEnemies, entities, freeTiles, wave);
    }

    /**
     * Generates the specified number of zombie enemies and places them in the room.
     *
     * @param numEnemies The number of zombies to generate.
     * @param entities   The list of entities to add the generated zombies to.
     * @param freeTiles  The set of available tiles where zombies can be placed.
     * @param wave       The wave number of the enemies being generated.
     */
    private void generateZombie(int numEnemies, List<Entity> entities, Set<Pair<Integer, Integer>> freeTiles, int wave) {
        IntStream.rangeClosed(0, numEnemies).mapToObj(i -> createZombie(freeTiles, wave)).forEach(enemy -> {
            placeEntity(enemy, freeTiles);
            entities.add(enemy);
        });
    }
//    /**
//     *
//     * @param numEnemies
//     * @param entities
//     * @param freeTiles
//     * @param wave
//     */
//    private void generateShooter(int numEnemies, List<Entity> entities, Set<Pair<Integer, Integer>> freeTiles, int wave) {
//        IntStream.rangeClosed(0, numEnemies).mapToObj(i -> createShooter(freeTiles, wave)).forEach(enemy -> {
//            placeEntity(enemy, freeTiles);
//            entities.add(enemy);
//        });
//    }

    /**
     * Creates a zombie enemy entity and assigns it a random position from the set of free tiles.
     *
     * @param freeTiles The set of available tiles where the enemy can be placed.
     * @param wave      The wave number of the enemy being created.
     * @return The created zombie entity.
     */
    private Entity createZombie(Set<Pair<Integer, Integer>> freeTiles, int wave) {
        // Customize enemy creation based on wave number
        Entity zombie = enemyFactory.createZombie(getRandomTile(freeTiles).getLeft().doubleValue(),
                getRandomTile(freeTiles).getRight().doubleValue());
        setEnemyPower(zombie, wave);
        return zombie;
    }

//    /**
//     * Creates a shooter enemy entity and assigns it a random position from the set of free tiles.
//     *
//     * @param freeTiles The set of available tiles where the enemy can be placed.
//     * @param wave      The wave number of the enemy being created.
//     * @return The created shooter entity.
//     */
//    private Entity createShooter(Set<Pair<Integer, Integer>> freeTiles, int wave) {
//        // Customize enemy creation based on wave number
//        Entity shooter = enemyFactory.createZombie(getRandomTile(freeTiles).getLeft().doubleValue(),
//                getRandomTile(freeTiles).getRight().doubleValue());
//        setEnemyPower(shooter, wave);
//        return shooter;
//    }

    /**
     * Sets the power level of an enemy based on the wave number.
     *
     * @param enemy The enemy entity.
     * @param wave  The wave number.
     */
    private void setEnemyPower(Entity enemy, int wave) {
        double enemyPower = calculateEnemyPower(wave);
        PowerComponent powerComponent = new PowerComponent(enemyPower);
        enemy.addComponent(powerComponent);
    }

    /**
     * Calculates the power level of an enemy based on the wave number.
     *
     * @param wave The wave number.
     * @return The power level of the enemy.
     */
    private double calculateEnemyPower(int wave) {
        // Customize enemy power calculation based on wave number
        return wave * POWER;
    }

    /**
     * Creates a boss entity and assigns it a random position from the set of free tiles.
     *
     * @param freeTiles The set of available tiles where the boss can be placed.
     * @return The created boss entity.
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
        int randomIndex = random.nextInt(freeTiles.size());
        Iterator<Pair<Integer, Integer>> iterator = freeTiles.iterator();
        IntStream.range(0, randomIndex).forEach(i -> iterator.next());
        return iterator.next();
    }

    /**
     * Generates the specified number of boss entities and places them in the room.
     *
     * @param numBoss    The number of boss entities to generate.
     * @param entities   The list of entities to add the generated bosses to.
     * @param freeTiles  The set of available tiles where bosses can be placed.
     */
    private void generateBoss(int numBoss, List<Entity> entities, Set<Pair<Integer, Integer>> freeTiles) {
        IntStream.range(0, numBoss).mapToObj(i -> createBoss(freeTiles)).forEach(boss -> {
            placeEntity(boss, freeTiles);
            entities.add(boss);
        });
    }

    /**
     * Creates a boss entity and assigns it a random position from the set of free tiles.
     *
     * @param freeTiles The set of available tiles where the boss can be placed.
     * @return The created boss entity.
     */
    private Entity createBoss(Set<Pair<Integer, Integer>> freeTiles) {
        return bossFactory.createBoss(getRandomTile(freeTiles).getLeft().doubleValue(),
                getRandomTile(freeTiles).getRight().doubleValue());
    }

    /**
     * Calculates the number of enemies to generate based on the number of free tiles.
     *
     * @param numFreeTiles The number of free tiles in the room.
     * @return The number of enemies to generate.
     */
    private int calculateNumEnemies(final int numFreeTiles) {
        int maxEnemies = Math.min(1, numFreeTiles / 100);
        return random.nextInt(maxEnemies) + 1;
    }
}
