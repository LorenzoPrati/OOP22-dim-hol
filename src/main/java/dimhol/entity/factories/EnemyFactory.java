package dimhol.entity.factories;

import dimhol.components.*;
import dimhol.entity.Entity;
import dimhol.entity.EntityBuilder;
import dimhol.logic.ai.RoutineFactory;
import dimhol.logic.collision.RectBodyShape;
import org.locationtech.jts.math.Vector2D;

public class EnemyFactory extends AbstractFactory {

    public static final double ZOMBIE_WIDTH = 1;
    public static final double ZOMBIE_HEIGHT = 1;
    public static final double ZOMBIE_SPEED = 2;
    public static final int ZOMBIE_HEALTH = 2;

    public EnemyFactory() {
        super();
    }

    public Entity createZombie(final double x, final double y) {
        return new EntityBuilder().add(new PlayerComponent())
                .add(new PositionComponent(new Vector2D(x,y), 0))
                .add(new MovementComponent(new Vector2D(0,1), ZOMBIE_SPEED, false))
                .add(new BodyComponent(new RectBodyShape(ZOMBIE_WIDTH, ZOMBIE_HEIGHT), true))
                .add(new HealthComponent(ZOMBIE_HEALTH))
                .add(new AnimationComponent(map.get("player"),"idle down"))
                .add(new AiComponent(new RoutineFactory().createZombieRoutine()))
                .build();
    }
}
