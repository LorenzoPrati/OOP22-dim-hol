package dimhol;

import dimhol.components.AIComponent;
import dimhol.components.AnimationComponent;
import dimhol.components.BodyComponent;
import dimhol.components.HealthComponent;
import dimhol.components.MovementComponent;
import dimhol.components.PositionComponent;
import dimhol.entity.Entity;
import dimhol.entity.factories.BossFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.math.Vector2D;

/**
 * Testing the generation with all its components for the minion entities.
 */
final class MinionsTest {

    private BossFactory bossFactory;
    private static final double TEST_NUMBER = 0.0001;

    private static final double COORDINATE_X = 10.0;
    private static final double COORDINATE_Y = 20.0;
    private static final double MINIONS_DIMENSION = 0.5;
    private static final double MINIONS_SPEED = 1;
    private static final double MINIONS_HEALTH = 1;
    private static final String MINIONS_STATUS = "idle";


    /**
     * Sets up the test fixture before each test method is run.
     */
    @BeforeEach
    void setup() {
        bossFactory = new BossFactory();
    }

    /**
     * Tests the creation of a minion.
     */
    @Test
    void testCreateMinion() {
        final double x = COORDINATE_X;
        final double y = COORDINATE_Y;

        final Entity minion = bossFactory.createMinion(x, y);

        // Check Position component
        final PositionComponent position = (PositionComponent) minion.getComponent(PositionComponent.class);
        Assertions.assertEquals(new Vector2D(x, y), position.getPos());

        // Check Movement component
        final MovementComponent movement = (MovementComponent) minion.getComponent(MovementComponent.class);
        Assertions.assertEquals(new Vector2D(0, 1), movement.getDir());
        Assertions.assertEquals(MINIONS_SPEED, movement.getSpeed(), TEST_NUMBER);
        Assertions.assertFalse(movement.isEnabled());

        // Check Body component
        final BodyComponent body = (BodyComponent) minion.getComponent(BodyComponent.class);
        Assertions.assertEquals(MINIONS_DIMENSION, body.getBodyShape().getBoundingWidth(), TEST_NUMBER);
        Assertions.assertEquals(MINIONS_DIMENSION, body.getBodyShape().getBoundingHeight(), TEST_NUMBER);
        Assertions.assertTrue(body.isSolid());

        // Check Health component
        final HealthComponent health = (HealthComponent) minion.getComponent(HealthComponent.class);
        Assertions.assertEquals(MINIONS_HEALTH, health.getMaxHealth());

        // Check Animation component
        final AnimationComponent animation = (AnimationComponent) minion.getComponent(AnimationComponent.class);
        Assertions.assertNotNull(animation.getMap().get(MINIONS_STATUS));

        // Check AI component
        final AIComponent ai = (AIComponent) minion.getComponent(AIComponent.class);
        Assertions.assertNotNull(ai.getRoutine());
    }

}
