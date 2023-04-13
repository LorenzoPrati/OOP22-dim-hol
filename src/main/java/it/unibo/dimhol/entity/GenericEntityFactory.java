package it.unibo.dimhol.entity;

import org.locationtech.jts.math.Vector2D;

import it.unibo.dimhol.commons.shapes.RectBodyShape;
import it.unibo.dimhol.components.*;

/**
 * Implementation of a factory to create various entities.
 */
public class GenericEntityFactory {

    private static final double PLAYER_SPEED = 6;
    private static final int W = 60;
    private static final int H = 60;

    public GenericEntityFactory() {
    }

    public Entity createPlayer(final double x, final double y) {
        return new EntityBuilder().add(new PlayerComponent())
                .add(new PositionComponent(new Vector2D(x,y)))
                .add(new MovementComponent(new Vector2D(0,0),PLAYER_SPEED, false))
                .add(new BodyComponent(new RectBodyShape(W,H)))
                .add(new VisualDebugComponent(0))
                .build();
    }
}
