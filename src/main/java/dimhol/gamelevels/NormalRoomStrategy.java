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
    private final int gateWidth;  // Width of the gate obj in tiles
    private final int gateHeight; // Height of the gate obj in tiles

    /**
     * Constructs a NormalRoomStrategy.
     *
     * @param genericFactory The generic entity factory used to create generic entities in the room.
     * @param enemyFactory The enemy entity factory used to create enemy entities in the room.
     * @param itemFactory The item factory used to create items in the room.
     * @param interactableObjectFactory The interactable object factory used to create interactable objects in the room.
     * @param random The random number generator used for generating random values.
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
        this.gateWidth = 3;
        this.gateHeight = 3;
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

    private void generateGate(final Set<Pair<Integer, Integer>> freeTiles, final List<Entity> entities) {
        calculateNumEntities(freeTiles.size());
        var gateFreeTiles = getRandomTileForGate(freeTiles);
        Entity gate = interactableObjectFactory.createGate(gateFreeTiles.getLeft().doubleValue(),
                                                            gateFreeTiles.getRight().doubleValue());
        entities.add(gate);
    }

    private void generatePlayer(final Set<Pair<Integer, Integer>> freeTiles, final List<Entity> entities) {
        Entity player = createAndPlacePlayer(freeTiles);
        entities.add(player);
    }

    private void generateEnemies(final Set<Pair<Integer, Integer>> freeTiles, final List<Entity> entities) {
        int numEnemies = calculateNumEnemies(freeTiles.size());
        generateZombies(numEnemies, entities, freeTiles);
        generateShooters(numEnemies, entities, freeTiles);
    }

    private void generateCoins(final Set<Pair<Integer, Integer>> freeTiles, final List<Entity> entities) {
        var coinsFreeTiles = getRandomTile(freeTiles);
        entities.add(itemFactory.createCoin(coinsFreeTiles.getLeft().doubleValue(),
                coinsFreeTiles.getRight().doubleValue()));
    }

    private void generateHearts(final Set<Pair<Integer, Integer>> freeTiles, final List<Entity> entities) {
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
     * Generates the specified number of enemies and places them in the room.
     *
     * @param numEnemies The number of enemies to generate.
     * @param entities   The list of entities to add the generated enemies to.
     * @param freeTiles  The set of available tiles where enemies can be placed.
     */
//    private void generateEnemies(final int numEnemies, final List<Entity> entities,
//                                 final Set<Pair<Integer, Integer>> freeTiles) {
//        IntStream.rangeClosed(0, numEnemies).forEach(i -> {
//            Entity zombie = createZombie(freeTiles);
//            Entity shooter = createShooter(freeTiles);
//            placeEntity(zombie, freeTiles);
//            placeEntity(shooter, freeTiles);
//            entities.add(zombie);
//            entities.add(shooter);
//        });
//    }

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
     * Places the entity at a random position with specified dimensions within the set of free tiles.
     *
     * @param entity       The entity to place.
     * @param freeTiles    The set of available tiles where the entity can be placed.
     * @param width        The width of the entity.
     * @param height       The height of the entity.
     */
//    private void placeEntityWithDimension(Entity entity, Set<Pair<Integer, Integer>> freeTiles, int width, int height) {
//        List<Pair<Integer, Integer>> validTiles = findValidTilesWithDimension(freeTiles, width, height);
//        if (!validTiles.isEmpty()) {
//            Pair<Integer, Integer> randomTile = validTiles.get(new Random().nextInt(validTiles.size()));
//            PositionComponent positionComponent = (PositionComponent) entity.getComponent(PositionComponent.class);
//            Vector2D position = new Vector2D(randomTile.getLeft(), randomTile.getRight());
//            positionComponent.setPos(position);
//        }
//    }

    /**
     * Finds valid tiles with the specified dimensions within the set of free tiles.
     *
     * @param freeTiles The set of available tiles where the entity can be placed.
     * @param width     The width of the entity.
     * @param height    The height of the entity.
     * @return A list of valid tiles with the specified dimensions.
     */
//    private List<Pair<Integer, Integer>> findValidTilesWithDimension(Set<Pair<Integer, Integer>> freeTiles,
//    int width, int height) {
//        List<Pair<Integer, Integer>> validTiles = new ArrayList<>();
//        for (Pair<Integer, Integer> tile : freeTiles) {
//            int tileX = tile.getLeft();
//            int tileY = tile.getRight();
//            boolean isValid = true;
//            // Check if the dimensions of the entity fit within the current tile and its neighboring tiles
//            for (int i = 0; i < width; i++) {
//                for (int j = 0; j < height; j++) {
//                    Pair<Integer, Integer> currentTile = Pair.of(tileX + i, tileY + j);
//                    if (!freeTiles.contains(currentTile)) {
//                        isValid = false;
//                        break;
//                    }
//                }
//                if (!isValid) {
//                    break;
//                }
//            }
//            if (isValid) {
//                validTiles.add(tile);
//            }
//        }
//        return validTiles;
//    }


    /**
     * Calculates the number of entities to generate based on the number of free tiles and the entity dimensions.
     *
     * @param numFreeTiles The number of free tiles in the room.
     * @return The number of entities to generate.
     * @throws IllegalArgumentException if the number of free tiles is less than the entities spawned
     * or the entity dimensions are invalid.
     */
    private int calculateNumEntities(final int numFreeTiles) {
        if (numFreeTiles < calculateRequiredTiles(GATE_WIDTH, GATE_HEIGHT)) {
            throw new IllegalArgumentException("Not enough free tiles to spawn the gate obj with the specified dimensions!");
        }
        int maxNumOfEntities = numFreeTiles / calculateRequiredTiles(GATE_WIDTH, GATE_HEIGHT);
        int numEntities = Math.min(NUM_GATE_ENTITIES, maxNumOfEntities);
        return numEntities > 0 ? numEntities : 1;
    }


    /**
     * Calculates the number of tiles required to accommodate an entity with the specified dimensions.
     *
     * @param width  The width of the entity.
     * @param height The height of the entity.
     * @return The number of tiles required.
     * @throws IllegalArgumentException if the entity dimensions are invalid.
     */
    private int calculateRequiredTiles(final int width, final int height) {
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
    private Pair<Integer, Integer> getRandomTileForGate(final Set<Pair<Integer, Integer>> freeTiles) {
        List<Pair<Integer, Integer>> shuffledTiles = new ArrayList<>(freeTiles);
        Collections.shuffle(shuffledTiles);
        return shuffledTiles.stream()
                .filter(tile -> canAccommodateGate(tile, freeTiles))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No free tiles can accommodate the gate interactable obj."));
    }

    /**
     * Checks if a given tile can accommodate the gate object dimensions.
     *
     * @param tile      The tile to check.
     * @param freeTiles The set of free tiles in the room.
     * @return True if the tile can accommodate the gate object, false otherwise.
     */
    private boolean canAccommodateGate(final Pair<Integer, Integer> tile, final Set<Pair<Integer, Integer>> freeTiles) {
        int startX = tile.getLeft();
        int startY = tile.getRight();

        for (int x = startX; x < startX + gateWidth; x++) {
            for (int y = startY; y < startY + gateHeight; y++) {
                if (!freeTiles.contains(Pair.of(x, y))) {
                    return false;
                }
            }
        }
        System.out.println("Gate: can be accommodated");
        return true;
    }

    /**
     * Retrieves the list of tiles occupied by the gate object, based on its starting position.
     *
     * @param startPos The starting position of the gate object.
     * @return The list of tiles occupied by the gate object.
     */
//    private List<Pair<Integer, Integer>> getGateTiles(Pair<Integer, Integer> startPos) {
//        List<Pair<Integer, Integer>> gateTiles = new ArrayList<>();
//        int startX = startPos.getLeft();
//        int startY = startPos.getRight();
//
//        for (int x = startX; x < startX + gateWidth; x++) {
//            for (int y = startY; y < startY + gateHeight; y++) {
//                gateTiles.add(Pair.of(x, y));
//            }
//        }
//
//        return gateTiles;
//    }

    /**
     * Calculates the center position of a group of tiles.
     *
     * @param tiles The group of tiles.
     * @return The center position as a Vector2D.
     */
//    private Vector2D getTileCenterPosition(List<Pair<Integer, Integer>> tiles) {
//        double totalX = 0;
//        double totalY = 0;
//
//        for (Pair<Integer, Integer> tile : tiles) {
//            totalX += tile.getLeft();
//            totalY += tile.getRight();
//        }
//
//        double centerX = totalX / tiles.size() + 0.5;
//        double centerY = totalY / tiles.size() + 0.5;
//
//        return new Vector2D(centerX, centerY);
//    }

    /**
     * Calculates the total number of tiles occupied by the gate object.
     *
     * @return The total number of tiles occupied by the gate object.
     */
//    private int calculateTotalTilesForGate() {
//        return gateWidth * gateHeight;
//    }
}
