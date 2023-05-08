package dimhol.entity.factories;

import dimhol.components.*;
import dimhol.entity.Entity;
import dimhol.entity.EntityBuilder;
import dimhol.logic.ai.RoutineFactory;
import dimhol.logic.collision.RectBodyShape;
import org.locationtech.jts.math.Vector2D;

public class EnemyFactory extends AbstractFactory {

    /**
     * Zombie width.
     */
    public static final double ZOMBIE_WIDTH = 1;
    /**
     * Zombie height.
     */
    public static final double ZOMBIE_HEIGHT = 1;
    /**
     * Zombie speed.
     */
    public static final double ZOMBIE_SPEED = 2;
    /**
     * Zombie health.
     */
    public static final int ZOMBIE_HEALTH = 2;

    /**
     * Shooter width.
     */
    public static final double SHOOTER_WIDTH = 1;
    /**
     * Zombie height.
     */
    public static final double SHOOTER_HEIGHT = 1;
    /**
     * Zombie speed.
     */
    public static final double SHOOTER_SPEED = 2;
    /**
     * Zombie health.
     */
    public static final int SHOOTER_HEALTH = 2;

    /**
     * Create a Zombie Entity that follows and attack you is you are in his aggro zone.
     * @param x coordinate
     * @param y coordinate
     * @return zombie entity
     */
    public Entity createZombie(final double x, final double y) {
        return new EntityBuilder()
                .add(new PositionComponent(new Vector2D(x, y), 0))
                .add(new MovementComponent(new Vector2D(0, 1), ZOMBIE_SPEED, false))
                .add(new BodyComponent(new RectBodyShape(ZOMBIE_WIDTH, ZOMBIE_HEIGHT), true))
                .add(new HealthComponent(ZOMBIE_HEALTH))
                .add(new AnimationComponent(map.get("enemy"), "idle"))
                .add(new AiComponent(new RoutineFactory().createZombieRoutine()))
                .build();
    }

    /**
     * Create a Shooter Entity that shoots you is you are in his aggro zone.
     * @param x coordinate
     * @param y coordinate
     * @return zombie entity
     */
    public Entity createShooter(final double x, final double y) {
        return new EntityBuilder()
                .add(new PositionComponent(new Vector2D(x, y), 0))
                .add(new MovementComponent(new Vector2D(0, 1), SHOOTER_SPEED, false))
                .add(new BodyComponent(new RectBodyShape(SHOOTER_WIDTH, SHOOTER_HEIGHT), true))
                .add(new HealthComponent(SHOOTER_HEALTH))
                .add(new AnimationComponent(map.get("enemy"), "idle"))
                .add(new AiComponent(new RoutineFactory().createShooterRoutine()))
                .build();
    }
}
