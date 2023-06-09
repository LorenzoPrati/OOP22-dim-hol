package dimhol;

import dimhol.components.AIComponent;
import dimhol.components.PositionComponent;
import dimhol.entity.Entity;
import dimhol.entity.factories.EnemyFactory;
import dimhol.entity.factories.GenericFactory;
import dimhol.events.WorldEvent;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.math.Vector2D;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class test AI behavior.
 */
final class AITest {

    /**
     * Melee reload time.
     */
    public static final double MELEE_RELOAD_TIME = 2;
    /**
     * Player position x.
     */
    public static final int PLAYER_POS_X = 20;
    /**
     * Player position y.
     */
    public static final int PLAYER_POS_Y = 20;

    private final AIComponent zombieAI;
    private final PositionComponent playerPos;
    private final PositionComponent zombiePos;
    private final Entity zombie;
    private final Entity player;

    /**
     * AITest constructor.
     */
    AITest() {
        final GenericFactory genericFactory = new GenericFactory();
        final EnemyFactory enemyFactory = new EnemyFactory();
        this.zombie = enemyFactory.createZombie(PLAYER_POS_X, PLAYER_POS_Y);
        this.player = genericFactory.createPlayer(0, 0);
        this.zombieAI = (AIComponent) zombie.getComponent(AIComponent.class);
        this.playerPos = (PositionComponent) player.getComponent(PositionComponent.class);
        this.zombiePos = (PositionComponent) zombie.getComponent(PositionComponent.class);
    }

    @Test
    void testAIBehaviorChanges() {

        final var zombieBehaviour = zombieAI.getRoutine();

        /*
         * Zombie move random.
         */
        final var randomMovement = zombieBehaviour.get(2);
        randomMovement.setPlayer(player);
        randomMovement.setEnemy(zombie);
        assertTrue(randomMovement.canExecute());

        /*
         * Zombie process a melee attack.
         */
        this.zombiePos.setPos(new Vector2D(1, 2));
        this.playerPos.setPos(new Vector2D(2, 1));
        final var meleeAttack = zombieBehaviour.get(0);
        meleeAttack.setEnemy(zombie);
        meleeAttack.setPlayer(player);
        assertTrue(meleeAttack.canExecute());
    }

    @Test
    void testShooterGeneration() {
        assertTrue(zombie.hasComponent(AIComponent.class));
    }

    @Test
    void testAttackSpawn() {

        this.zombiePos.setPos(new Vector2D(2, 1));
        this.playerPos.setPos(new Vector2D(1, 1));

        final var zombieBehaviour = zombieAI.getRoutine();
        final var meleeAttack = zombieBehaviour.get(0);
        meleeAttack.setEnemy(zombie);
        meleeAttack.setPlayer(player);
        zombieAI.setPrevTime(1);
        zombieAI.updateTime(10);
        assertTrue(zombieAI.getCurrentTime() - zombieAI.getPrevTime() > MELEE_RELOAD_TIME);
        final Optional<List<WorldEvent>> addAttackEvent = meleeAttack.execute();
        assertTrue(addAttackEvent.isPresent());
        assertEquals(addAttackEvent.get().size(), 1);
    }
}
