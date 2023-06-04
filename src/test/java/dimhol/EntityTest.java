package dimhol;

import dimhol.components.*;
import dimhol.entity.EntityBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.math.Vector2D;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test entities.
 */
public class EntityTest {

    private final static int NUM_COMP = 3;

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
        var sampleEntity = builder.add(new PositionComponent(new Vector2D(0,0),1))
                .add(new HealthComponent(1))
                .add(new MovementComponent(new Vector2D(0,1), 1, false))
                .build();
        assertTrue(sampleEntity.hasComponent(PositionComponent.class));
        assertFalse(sampleEntity.hasComponent(BodyComponent.class));
        assertEquals(NUM_COMP, sampleEntity.getComponents().stream().count());
        /*
        Testing add/remove component
         */
        var playerComponent = new PlayerComponent();
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
