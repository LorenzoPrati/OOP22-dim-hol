package it.unibo.dimhol.entity.factories;

import it.unibo.dimhol.components.*;
import it.unibo.dimhol.entity.Entity;
import it.unibo.dimhol.entity.EntityBuilder;
import it.unibo.dimhol.logic.collision.RectBodyShape;
import org.locationtech.jts.math.Vector2D;

/**
 * A factory to create various entities.
 */
public class GenericFactory extends AbstractFactory {

    private static final double PLAYER_SPEED = 3;
    private static final int PLAYER_HEALTH = 5;
    private static final double PLAYER_WIDTH = 1;
    private static final double PLAYER_HEIGHT = 1;

    public GenericFactory() {
        super();
    }

    public Entity createPlayer(final double x, final double y) {
        return new EntityBuilder().add(new PlayerComponent())
                .add(new PositionComponent(new Vector2D(x,y), 0))
                .add(new MovementComponent(new Vector2D(0,1), PLAYER_SPEED, false))
                .add(new BodyComponent(new RectBodyShape(PLAYER_WIDTH, PLAYER_HEIGHT), true))
                .add(new CoinPocketComponent(5))
                .add(new HealthComponent(PLAYER_HEALTH))
                .add(new InteractorComponent())
                .add(new AnimationComponent(map.get("player"),"idle down"))
                .build();
    }
}
