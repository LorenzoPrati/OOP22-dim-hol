package dimhol.entity.factories;

import dimhol.components.AiComponent;
import dimhol.components.AnimationComponent;
import dimhol.components.BodyComponent;
import dimhol.components.HealthComponent;
import dimhol.components.MovementComponent;
import dimhol.components.PositionComponent;
import dimhol.entity.Entity;
import dimhol.entity.EntityBuilder;
import dimhol.logic.ai.RoutineFactory;
import dimhol.logic.collision.RectBodyShape;
import org.locationtech.jts.math.Vector2D;

/**
 * The BossFactory class is responsible for creating Boss entities in the game.
 */
public class BossFactory extends AbstractFactory {

    /**
     * The width of the Boss.
     */
    public static final double BOSS_WIDTH = 4;
    /**
     * The height of the Boss.
     */
    public static final double BOSS_HEIGHT = 3;
    /**
     * The speed of the Boss.
     */
    public static final double BOSS_SPEED = 3;
    /**
     * The health of the Boss.
     */
    public static final int BOSS_HEALTH = 10;

    /**
     * Create a Boss Entity.
     *
     * @param x The x-coordinate of the Boss.
     * @param y The y-coordinate of the Boss.
     * @return The created Boss entity.
     */
    public Entity createBoss(final double x, final double y) {
        // Add components to the entity builder
        return new EntityBuilder()
                .add(new PositionComponent(new Vector2D(x, y), 0))
                .add(new MovementComponent(new Vector2D(0, 1), BOSS_SPEED, false))
                .add(new BodyComponent(new RectBodyShape(BOSS_WIDTH, BOSS_HEIGHT), true))
                .add(new HealthComponent(BOSS_HEALTH))
                .add(new AnimationComponent(map.get("boss"), "walk"))
                .add(new AiComponent(new RoutineFactory().createBossRoutine()))
                .build();
    }
}
