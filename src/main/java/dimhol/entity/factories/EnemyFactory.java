package dimhol.entity.factories;

import dimhol.components.PositionComponent;
import dimhol.components.BodyComponent;
import dimhol.components.AiComponent;
import dimhol.components.MovementComponent;
import dimhol.components.HealthComponent;
import dimhol.components.AnimationComponent;
import dimhol.entity.Entity;
import dimhol.entity.EntityBuilder;
import dimhol.logic.enemyAI.RoutineFactory;
import dimhol.logic.collision.RectBodyShape;
import org.locationtech.jts.math.Vector2D;

/**
 * It's an enemy factory.
 */
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
     * Mins infos:
     */
    private static final double MINS_SPEED = 1;
    private static final double MINS_WIDTH = 0.5;
    private static final double MINS_HEIGHT = 0.5;
    private static final int MINS_HEALTH = 1;

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

    /**
     * Create a minion Entity that hits you is you are in his aggro zone.
     * @param x coordinate
     * @param y coordinate
     * @return minion entity
     */
    public Entity createMinion(final double x, final double y) {
        return new EntityBuilder()
                .add(new PositionComponent(new Vector2D(x,y), 0))
                .add(new MovementComponent(new Vector2D(0,1), MINS_SPEED, false ))
                .add(new BodyComponent(new RectBodyShape(MINS_WIDTH, MINS_HEIGHT), true))
                .add(new AnimationComponent(map.get("enemy"), "idle"))
                .add(new AiComponent(new RoutineFactory().createMinsRuotine()))
                .build();
    }


}
