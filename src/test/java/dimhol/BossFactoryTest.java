package dimhol;

import dimhol.components.AIComponent;
import dimhol.components.AnimationComponent;
import dimhol.components.BodyComponent;
import dimhol.components.BossComponent;
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
 * Testing the generation with all its components for the boss entity.
 */
final class BossFactoryTest {
    private BossFactory bossFactory;
    private static final double TEST_NUMBER = 0.0001;

    private static final double COORDINATE_X = 10.0;
    private static final double COORDINATE_Y = 20.0;
    private static final double BOSS_WIDTH = 4;
    private static final double BOSS_HEIGHT = 3;
    private static final double BOSS_SPEED = 1.5;
    private static final double BOSS_HEALTH = 20;
    private static final String BOSS_STATUS = "walk";

    /**
     *
     */
    @BeforeEach
    void setup() {
        bossFactory = new BossFactory();
    }

    /**
     * Tests the boss creation.
     */
    @Test
    void testCreateBoss() {
        final double x = COORDINATE_X;
        final double y = COORDINATE_Y;

        final Entity boss = bossFactory.createBoss(x, y);

        // Check Boss component
        Assertions.assertNotNull(boss.getComponent(BossComponent.class));

        // Check Position component
        final PositionComponent position = (PositionComponent) boss.getComponent(PositionComponent.class);
        Assertions.assertEquals(new Vector2D(x, y), position.getPos());

        // Check Movement component
        final MovementComponent movement = (MovementComponent) boss.getComponent(MovementComponent.class);
        Assertions.assertEquals(new Vector2D(0, 1), movement.getDir());
        Assertions.assertEquals(BOSS_SPEED, movement.getSpeed(), TEST_NUMBER);
        Assertions.assertFalse(movement.isEnabled());

        // Check Body component
        final BodyComponent body = (BodyComponent) boss.getComponent(BodyComponent.class);
        Assertions.assertEquals(BOSS_WIDTH, body.getBodyShape().getBoundingWidth(), TEST_NUMBER);
        Assertions.assertEquals(BOSS_HEIGHT, body.getBodyShape().getBoundingHeight(), TEST_NUMBER);
        Assertions.assertTrue(body.isSolid());

        // Check Health component
        final HealthComponent health = (HealthComponent) boss.getComponent(HealthComponent.class);
        Assertions.assertEquals(BOSS_HEALTH, health.getMaxHealth());

        // Check Animation component
        final AnimationComponent animation = (AnimationComponent) boss.getComponent(AnimationComponent.class);
        Assertions.assertNotNull(animation.getMap().get(BOSS_STATUS));

        // Check AI component
        final AIComponent ai = (AIComponent) boss.getComponent(AIComponent.class);
        Assertions.assertNotNull(ai.getRoutine());
    }
}
