package dimhol.gamelevels;

import dimhol.components.PositionComponent;
import dimhol.entity.Entity;
import dimhol.entity.factories.EnemyFactory;
import dimhol.entity.factories.GenericFactory;
import org.apache.commons.lang3.tuple.Pair;
import org.locationtech.jts.math.Vector2D;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * The strategy to generate a normal room in the game.
 */
public class NormalRoomStrategy implements RoomStrategy {
    private final GenericFactory genericFactory;
    private final EnemyFactory enemyFactory;
    private final Random random;

    private static final int ENEMY_DENSITY = 100;

    /**
     * Constructs a NormalRoomStrategy.
     *
     * @param genericFactory The generic entity factory.
     * @param enemyFactory The enemy entity factory.
     */
    public NormalRoomStrategy(final GenericFactory genericFactory, final EnemyFactory enemyFactory) {
        this.genericFactory = genericFactory;
        this.enemyFactory = enemyFactory;
        this.random = new Random();
    }

    /**
     * Generates entities for the normal room.
     *
     * @param entity The main entity is the player of the game room.
     * @param freeTiles The set of available tiles within the room, where entities can be placed.
     * @return A list of generated entities.
     */
    @Override
    public final List<Entity> generate(final Entity entity, final Set<Pair<Integer, Integer>> freeTiles) {

        List<Entity> entities = new ArrayList<>();

        //Place the player:
        Entity player = genericFactory.createPlayer(getRandomTile(freeTiles).getLeft().doubleValue(),
                getRandomTile(freeTiles).getRight().doubleValue());
        placePlayer(player, freeTiles);
        entities.add(player);

        //Place the enemies:
        int numEnemies = (freeTiles.size() / ENEMY_DENSITY);
        for (int i = 0; i < numEnemies; i++) {
            Entity enemy = enemyFactory.createZombie(getRandomTile(freeTiles).getLeft().doubleValue(),
                    getRandomTile(freeTiles).getRight().doubleValue());
            placeEnemy(enemy, freeTiles);
            entities.add(enemy);
        }
        return entities;
    }

    /**
     * Places an enemy entity in a random free tile.
     *
     * @param entity The enemy entity to be placed.
     * @param freeTiles The set of free tiles in the room.
     */
    private void placeEnemy(final Entity entity, final Set<Pair<Integer, Integer>> freeTiles) {
        var enemyPosition = (PositionComponent) entity.getComponent(PositionComponent.class);
        var freeRandomTile = freeTiles.stream().toList().get(random.nextInt(freeTiles.size()));
        enemyPosition.setPos(new Vector2D(freeRandomTile.getLeft(), freeRandomTile.getRight()));
    }

    /**
     * Places the player entity in a random free tile.
     *
     * @param entity The entity player to be placed.
     * @param freeTiles The set of free tiles in the room.
     */
    private void placePlayer(final Entity entity, final Set<Pair<Integer, Integer>> freeTiles) {
        var playerPosition = (PositionComponent) entity.getComponent(PositionComponent.class);
        var freeRandomTile = freeTiles.stream().toList().get(random.nextInt(freeTiles.size()));
        playerPosition.setPos(new Vector2D(freeRandomTile.getLeft(), freeRandomTile.getRight()));
    }

    /**
     * Retrieves a random tile from the set of free tiles.
     *
     * @param freeTiles The set of free tiles in the room.
     * @return A random tile represented as a Pair of coordinates.
     */
    private Pair<Integer, Integer> getRandomTile(final Set<Pair<Integer, Integer>> freeTiles) {
        List<Pair<Integer, Integer>> tiles = new ArrayList<>(freeTiles);
        return tiles.get(random.nextInt(tiles.size()));
    }
}
