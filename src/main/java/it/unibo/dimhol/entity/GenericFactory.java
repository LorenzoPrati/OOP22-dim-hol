package it.unibo.dimhol.entity;

import it.unibo.dimhol.ai.RoutineFactory;
import org.locationtech.jts.math.Vector2D;

import it.unibo.dimhol.commons.shapes.RectBodyShape;
import it.unibo.dimhol.components.*;

/**
 * Implementation of a factory to create various entities.
 */
public class GenericFactory {

    private static final double PLAYER_SPEED = 6;
    private static final double ENEMY_SPEED = 3;
    private static final int W = 60;
    private static final int H = 60;

    public GenericFactory() {
    }

    public Entity createPlayer(final double x, final double y) {
        return new EntityBuilder().add(new PlayerComponent())
                .add(new PositionComponent(new Vector2D(x,y)))
                .add(new MovementComponent(new Vector2D(0,0),PLAYER_SPEED, false))
                .add(new BodyComponent(new RectBodyShape(W,H), true))
                .add(new VisualDebugComponent(0))
                .add(new InfoAnimationComponent("player", "walking up"))
                .build();
    }

    /*public Entity createObstacle(final double x, final double y) {
        return new EntityBuilder().add(new PositionComponent(new Vector2D(100,100)))
                .add(new BodyComponent(new RectBodyShape(W, H), true))
                .add(new VisualDebugComponent(2))
                .add(new InfoAnimationComponent("coin", "idle"))
                .build();
    }*/

    /*public Entity createZombieEnemy(final double x, final double y) {
        return new EntityBuilder()
                .add(new AiComponent(new RoutineFactory().createZombieRoutine()))
                .add(new PositionComponent(new Vector2D(x,y)))
                .add(new MovementComponent(new Vector2D(0,0), ENEMY_SPEED, false))
                .add(new BodyComponent(new RectBodyShape(W,H), true))
                .add(new InfoAnimationComponent("enemy", "walking up"))
                .add(new VisualDebugComponent(1))
                .build();
    }*/
}
