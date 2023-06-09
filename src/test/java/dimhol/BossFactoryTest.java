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

public class BossFactoryTest {
    private BossFactory bossFactory;

    @BeforeEach
    public void setup() {
        bossFactory = new BossFactory();
    }

    @Test
    public void testCreateBoss() {
        double x = 10.0;
        double y = 20.0;

        Entity boss = bossFactory.createBoss(x, y);

        // Check Boss component
        Assertions.assertNotNull(boss.getComponent(BossComponent.class));

        // Check Position component
        PositionComponent position = (PositionComponent) boss.getComponent(PositionComponent.class);
        Assertions.assertEquals(new Vector2D(x, y), position.getPos());

        // Check Movement component
        MovementComponent movement = (MovementComponent) boss.getComponent(MovementComponent.class);
        Assertions.assertEquals(new Vector2D(0, 1), movement.getDir());
        Assertions.assertEquals(2, movement.getSpeed(), 0.0001);
        Assertions.assertFalse(movement.isEnabled());

        // Check Body component
        BodyComponent body = (BodyComponent) boss.getComponent(BodyComponent.class);
        Assertions.assertEquals(4, body.getBodyShape().getBoundingWidth(), 0.0001);
        Assertions.assertEquals(3, body.getBodyShape().getBoundingHeight(), 0.0001);
        Assertions.assertTrue(body.isSolid());

        // Check Health component
        HealthComponent health = (HealthComponent) boss.getComponent(HealthComponent.class);
        Assertions.assertEquals(20, health.getMaxHealth());

        // Check Animation component
        AnimationComponent animation = (AnimationComponent) boss.getComponent(AnimationComponent.class);
        Assertions.assertNotNull(animation.getMap().get("walk"));

        // Check AI component
        AIComponent ai = (AIComponent) boss.getComponent(AIComponent.class);
        Assertions.assertNotNull(ai.getRoutine());
    }
}
