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

public class PlayerAttackFactory extends AbstractFactory {

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

    public Entity createAttack(final Vector2D pos, final Entity entity) {
        return new EntityBuilder()
                .add(new PositionComponent(pos, 0))
                .add(new BodyComponent(new RectBodyShape(MELEE_WIDTH, MELEE_HEIGHT), false))
                .add(new AttackComponent(entity, List.of(healthEffectFactory.decreasePlayerCurrentHealthEffect(1))))
                .build();
    }

    public Entity createDistanceAttack(final Vector2D pos, final Vector2D dir, final Entity entity) {
        return new EntityBuilder()
                .add(new PositionComponent(pos, 0))
                .add(new MovementComponent(dir, BULLET_SPEED, true))
                .add(new BodyComponent(new RectBodyShape(BULLET_WIDTH, BULLET_HEIGHT), false))
                .add(new AnimationComponent(map.get("bullet"), DirectionUtil.getStringFromVec(dir)))
                .add(new AttackComponent(entity, List.of(healthEffectFactory.decreasePlayerCurrentHealthEffect(1))))
                .build();
    }

}
