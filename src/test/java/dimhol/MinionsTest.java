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

public class MinionsTest {

    private BossFactory bossFactory;

    @BeforeEach
    public void setup() {
        bossFactory = new BossFactory();
    }

    @Test
    public void testCreateMinion() {
        double x = 10.0;
        double y = 20.0;

        Entity minion = bossFactory.createMinion(x, y);

        // Check Position component
        PositionComponent position = (PositionComponent) minion.getComponent(PositionComponent.class);
        Assertions.assertEquals(new Vector2D(x, y), position.getPos());

        // Check Movement component
        MovementComponent movement = (MovementComponent) minion.getComponent(MovementComponent.class);
        Assertions.assertEquals(new Vector2D(0, 1), movement.getDir());
        Assertions.assertEquals(1, movement.getSpeed(), 0.0001);
        Assertions.assertFalse(movement.isEnabled());

        // Check Body component
        BodyComponent body = (BodyComponent) minion.getComponent(BodyComponent.class);
        Assertions.assertEquals(0.5, body.getBodyShape().getBoundingWidth(), 0.0001);
        Assertions.assertEquals(0.5, body.getBodyShape().getBoundingHeight(), 0.0001);
        Assertions.assertTrue(body.isSolid());

        // Check Health component
        HealthComponent health = (HealthComponent) minion.getComponent(HealthComponent.class);
        Assertions.assertEquals(1, health.getMaxHealth());

        // Check Animation component
        AnimationComponent animation = (AnimationComponent) minion.getComponent(AnimationComponent.class);
        Assertions.assertNotNull(animation.getMap().get("idle"));

        // Check AI component
        AIComponent ai = (AIComponent) minion.getComponent(AIComponent.class);
        Assertions.assertNotNull(ai.getRoutine());
    }

}
