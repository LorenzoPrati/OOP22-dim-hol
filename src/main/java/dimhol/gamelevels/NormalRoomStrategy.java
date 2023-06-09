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

/**
 * The strategy to generate a normal room in the game.
 */
public class NormalRoomStrategy extends AbstractRoomStrategy {
    private static final int ENTITY_WIDTH = 1;
    private static final int ENTITY_HEIGHT = 1;
    private static final int NUM_INTERACTIVE = 1;

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
        super(genericFactory, enemyFactory, itemFactory, interactableObjectFactory, randomGenerator);
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
    public List<Entity> generate(final Optional<Entity> entity, final Set<Pair<Integer, Integer>> availableTiles,
                                       final List<Entity> entities) {
        final List<Entity> newListOfEntities = new ArrayList<>();

        //Place the player:
        generatePlayer(availableTiles, entities, newListOfEntities, ENTITY_WIDTH, ENTITY_HEIGHT);

        //Place the enemies:
        generateEnemies(availableTiles, newListOfEntities);

        //Place the items:
        generateItems(availableTiles, newListOfEntities);

        //Place gate:
        generateGate(NUM_INTERACTIVE, availableTiles, newListOfEntities);

        return newListOfEntities;
    }
}
