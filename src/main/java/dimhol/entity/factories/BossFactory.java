package dimhol.entity.factories;

import dimhol.components.AIComponent;
import dimhol.components.AnimationComponent;
import dimhol.components.BodyComponent;
import dimhol.components.BossComponent;
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
public class BossFactory extends BaseFactory {

    /**
     * Boss's width.
     */
    private static final double BOSS_WIDTH = 4;
    /**
     * Boss's height.
     */
    private static final double BOSS_HEIGHT = 3;
    /**
     * Boss's speed.
     */
    private static final double BOSS_SPEED = 2;
    /**
     * Boss's health.
     */
    private static final int BOSS_HEALTH = 20;

    /**
     * Minion's speed.
     */
    private static final double MINIONS_SPEED = 1;
    /**
     * Minion's width.
     */
    private static final double MINIONS_WIDTH = 0.5;
    /**
     * Minion's speed.
     */
    private static final double MINIONS_HEIGHT = 0.5;
    /**
     * Minion's health.
     */
    private static final int MINIONS_HEALTH = 1;

    /**
     * Create a Boss Entity.
     *
     * @param x The x-coordinate of the Boss.
     * @param y The y-coordinate of the Boss.
     * @return The created Boss entity.
     */
    public Entity createBoss(final double x, final double y) {
        return new EntityBuilder()
                .add(new BossComponent())
                .add(new PositionComponent(new Vector2D(x, y), 0))
                .add(new MovementComponent(new Vector2D(0, 1), BOSS_SPEED, false))
                .add(new BodyComponent(new RectBodyShape(BOSS_WIDTH, BOSS_HEIGHT), true))
                .add(new HealthComponent(BOSS_HEALTH))
                .add(new AnimationComponent(getAnimationsMap().get("boss"), "walk"))
                .add(new AIComponent(new RoutineFactory().createBossRoutine()))
                .build();
    }

    /**
     * Create a minion Entity.
     *
     * @param x The x-coordinate of the Minions.
     * @param y The y-coordinate of the Minions.
     * @return The created Minion entity.
     */
    public Entity createMinion(final double x, final double y) {
        return new EntityBuilder()
                .add(new PositionComponent(new Vector2D(x, y), 0))
                .add(new MovementComponent(new Vector2D(0, 1), MINIONS_SPEED, false))
                .add(new BodyComponent(new RectBodyShape(MINIONS_WIDTH, MINIONS_HEIGHT), true))
                .add(new HealthComponent(MINIONS_HEALTH))
                .add(new AnimationComponent(getAnimationsMap().get("enemy"), "idle"))
                .add(new AIComponent(new RoutineFactory().createMinionRoutine()))
                .build();
    }
}
