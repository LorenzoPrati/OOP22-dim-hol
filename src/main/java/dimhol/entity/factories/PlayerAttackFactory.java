package dimhol.entity.factories;

import dimhol.components.AttackComponent;
import dimhol.components.BodyComponent;
import dimhol.components.PositionComponent;
import dimhol.entity.Entity;
import dimhol.entity.EntityBuilder;
import dimhol.logic.collision.RectBodyShape;
import dimhol.logic.effects.HealthEffectFactory;
import dimhol.logic.effects.HealthEffectsFactoryImpl;
import org.locationtech.jts.math.Vector2D;

import java.util.List;

public class PlayerAttackFactory extends AbstractFactory {

    /*
     * Prima di chiamre questi metodi è importante setttare la posizione del
     * attacco usando AttackUtili.getAttackPosition(...)
     */

    public static final int PLAYER_MELEE_WIDTH = 1;

    public static final int PLAYER_MELEE_HEIGHT = 1;

    private final HealthEffectFactory healthEffectFactory = new HealthEffectsFactoryImpl();

    public Entity createPlayerMeleeAttack(final Vector2D pos, final Entity entity) {
        return new EntityBuilder()
                .add(new PositionComponent(pos, 0))
                .add(new BodyComponent(new RectBodyShape(PLAYER_MELEE_WIDTH, PLAYER_MELEE_HEIGHT), false))
                .add(new AttackComponent(entity, List.of(healthEffectFactory.decreasePlayerCurrentHealthEffect(1))))
                .build();
    }

}
