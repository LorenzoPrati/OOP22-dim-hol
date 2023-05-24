package dimhol.logic.player;

import dimhol.components.*;
import dimhol.entity.Entity;
import dimhol.entity.EntityBuilder;
import dimhol.entity.factories.AbstractAttackFactory;
import dimhol.logic.collision.RectBodyShape;
import dimhol.logic.util.DirectionUtil;

import java.util.function.Predicate;

public class PlayerAttackFactory extends AbstractAttackFactory {

    public static final int PLAYER_MELEE_WIDTH = 1;

    public static final int PLAYER_MELEE_HEIGHT = 1;

    private static final int PLAYER_MELEE_DAMAGE = 1;

    public static final int PLAYER_LITTLE_BULLET_WIDTH = 1;

    public static final int PLAYER_LITTLE_BULLET_HEIGHT = 1;

    public static final int PLAYER_LITTLE_BULLET_SPEED = 3;

    private static final int PLAYER_LITTLE_BULLET_DAMAGE = 1;

    public static final int PLAYER_BIG_BULLET_WIDTH = 1;

    public static final int PLAYER_BIG_BULLET_HEIGHT = 1;

    public static final int PLAYER_BIG_BULLET_SPEED = 3;

    private static final int PLAYER_BIG_BULLET_DAMAGE = 1;

    private Predicate<Entity> checkEnemy = entity -> entity.hasComponent(AiComponent.class);

    public Entity createMeleeAttack(final Entity entity) {
        return new EntityBuilder()
                .add(new PositionComponent(getAttackPos(entity, PLAYER_MELEE_WIDTH, PLAYER_MELEE_HEIGHT), 0))
                .add(new BodyComponent(new RectBodyShape(PLAYER_MELEE_WIDTH, PLAYER_MELEE_HEIGHT), false))
                .add(new MeleeAttackComponent(PLAYER_MELEE_DAMAGE, checkEnemy))
                .build();
    }

    public Entity createLittleBulletAttack(final Entity entity) {
        return new EntityBuilder()
                .add(new PositionComponent(getAttackPos(entity, PLAYER_LITTLE_BULLET_WIDTH, PLAYER_LITTLE_BULLET_HEIGHT), 0))
                .add(new MovementComponent(getDirection(entity), PLAYER_LITTLE_BULLET_SPEED, true))
                .add(new BodyComponent(new RectBodyShape(PLAYER_LITTLE_BULLET_WIDTH, PLAYER_LITTLE_BULLET_HEIGHT), false))
                .add(new AnimationComponent(map.get("bullet"), DirectionUtil.getStringFromVec(getDirection(entity))))
                .add(new MeleeAttackComponent(PLAYER_LITTLE_BULLET_DAMAGE, checkEnemy))
                .build();
    }

    public Entity createBigBulletAttack(final Entity entity) {
        return new EntityBuilder()
                .add(new PositionComponent(getAttackPos(entity, PLAYER_BIG_BULLET_WIDTH, PLAYER_LITTLE_BULLET_HEIGHT), 0))
                .add(new MovementComponent(getDirection(entity), PLAYER_BIG_BULLET_SPEED, true))
                .add(new BodyComponent(new RectBodyShape(PLAYER_BIG_BULLET_WIDTH, PLAYER_BIG_BULLET_HEIGHT), false))
                .add(new AnimationComponent(map.get("bullet"), DirectionUtil.getStringFromVec(getDirection(entity))))
                .add(new AttackComponent(PLAYER_BIG_BULLET_DAMAGE, checkEnemy))
                .add(new BulletTagComponent())
                .build();
    }

}
