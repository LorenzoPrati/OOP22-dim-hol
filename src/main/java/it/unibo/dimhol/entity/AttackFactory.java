package it.unibo.dimhol.entity;

import it.unibo.dimhol.components.*;
import it.unibo.dimhol.logic.collision.RectBodyShape;
import it.unibo.dimhol.logic.effects.DecreasePlayerCurrentHealthEffect;
import it.unibo.dimhol.logic.util.DirectionUtil;
import org.locationtech.jts.math.Vector2D;

import java.util.List;

public class AttackFactory extends AbstractFactory {

    public static final double BULLET_WIDTH = 0.5;
    public static final double BULLET_HEIGHT = 0.5;
    private static final double BULLET_SPEED = 3;

    public static final double MELEE_WIDTH = 1;
    public static final double MELEE_HEIGHT = 1;

    public AttackFactory() {
        super();
    }

    public Entity createMeleeAttack(final Vector2D pos, final Entity entity) {
        return new EntityBuilder()
                .add(new PositionComponent(pos, 0))
                .add(new BodyComponent(new RectBodyShape(MELEE_WIDTH, MELEE_HEIGHT), false))
                .add(new AttackComponent(entity, List.of(new DecreasePlayerCurrentHealthEffect(1))))
                //.add(new AnimationComponent(map.get("heart"), "idle"))
                .build();
    }

    public Entity createDistanceAttack(final Vector2D pos, final Vector2D dir, Entity entity) {
        return new EntityBuilder()
                .add(new PositionComponent(pos, 0))
                .add(new MovementComponent(dir, BULLET_SPEED, true))
                .add(new BodyComponent(new RectBodyShape(BULLET_WIDTH, BULLET_HEIGHT), false))
                .add(new AnimationComponent(map.get("bullet"), DirectionUtil.getStringFromVec(dir)))
                .add(new AttackComponent(entity, List.of(new DecreasePlayerCurrentHealthEffect(1))))
                .build();
    }
}
