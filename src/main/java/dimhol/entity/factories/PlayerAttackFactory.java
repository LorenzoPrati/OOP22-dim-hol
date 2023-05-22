package dimhol.entity.factories;

import dimhol.components.*;
import dimhol.entity.Entity;
import dimhol.entity.EntityBuilder;
import dimhol.logic.collision.RectBodyShape;
import dimhol.logic.effects.HealthEffectFactory;
import dimhol.logic.effects.HealthEffectsFactoryImpl;
import dimhol.logic.util.DirectionUtil;
import org.locationtech.jts.math.Vector2D;

import java.util.List;

public class PlayerAttackFactory extends AbstractAttackFactory {

    /*
     * Prima di chiamre questi metodi è importante setttare la posizione del
     * attacco usando AttackUtili.getAttackPosition(...)
     */

    public static final int MELEE_WIDTH = 1;

    public static final int MELEE_HEIGHT = 1;

    public static final int BULLET_WIDTH = 1;

    public static final int BULLET_HEIGHT = 1;

    public static final int BULLET_SPEED = 3;

    private final HealthEffectFactory healthEffectFactory = new HealthEffectsFactoryImpl();

    public Entity createAttack(final Entity entity) {
        return new EntityBuilder()
                .add(new PositionComponent(getAttackPos(entity, MELEE_WIDTH, MELEE_HEIGHT), 0))
                .add(new BodyComponent(new RectBodyShape(MELEE_WIDTH, MELEE_HEIGHT), false))
                .add(new AttackComponent(entity, List.of(healthEffectFactory.decreasePlayerCurrentHealthEffect(1))))
                .build();
    }

    public Entity createDistanceAttack(final Entity entity) {
        return new EntityBuilder()
                .add(new PositionComponent(getAttackPos(entity, BULLET_WIDTH, BULLET_HEIGHT), 0))
                .add(new MovementComponent(getDirection(entity), BULLET_SPEED, true))
                .add(new BodyComponent(new RectBodyShape(BULLET_WIDTH, BULLET_HEIGHT), false))
                .add(new AnimationComponent(map.get("bullet"), DirectionUtil.getStringFromVec(getDirection(entity))))
                .add(new AttackComponent(entity, List.of(healthEffectFactory.decreasePlayerCurrentHealthEffect(1))))
                .build();
    }

}
