package dimhol;

import dimhol.components.AnimationComponent;
import dimhol.components.BodyComponent;
import dimhol.components.HealthComponent;
import dimhol.components.MovementComponent;
import dimhol.components.PlayerComponent;
import dimhol.components.PositionComponent;
import dimhol.entity.EntityBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.math.Vector2D;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test entities.
 */
final class EntityTest {

    private static final int NUM_COMP = 3;

    private EntityBuilder builder;

    @BeforeEach
    void initialize() {
        this.builder = new EntityBuilder();
    }

    @Test
    void testEntity() {
        /*
        Testing builder
         */
        final var sampleEntity = builder.add(new PositionComponent(new Vector2D(0, 0), 1))
                .add(new HealthComponent(1))
                .add(new MovementComponent(new Vector2D(0, 1), 1, false))
                .build();
        assertTrue(sampleEntity.hasComponent(PositionComponent.class));
        assertFalse(sampleEntity.hasComponent(BodyComponent.class));
        assertEquals(NUM_COMP, sampleEntity.getComponents().stream().count());
        /*
        Testing add/remove component
         */
        final var playerComponent = new PlayerComponent();
        sampleEntity.addComponent(playerComponent);
        assertTrue(sampleEntity.hasComponent(PlayerComponent.class));

        sampleEntity.removeComponent(playerComponent);
        assertFalse(sampleEntity.hasComponent(PlayerComponent.class));
        /*
        Family check
         */
        assertTrue(sampleEntity.hasFamily(Set.of(PositionComponent.class, HealthComponent.class)));
        assertFalse(sampleEntity.hasFamily(Set.of(PositionComponent.class, AnimationComponent.class)));
    }
}
