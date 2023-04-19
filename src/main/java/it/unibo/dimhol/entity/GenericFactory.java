package it.unibo.dimhol.entity;

import it.unibo.dimhol.ai.MathUtilities;
import it.unibo.dimhol.ai.RoutineFactory;
import org.locationtech.jts.math.Vector2D;

import it.unibo.dimhol.commons.shapes.RectBodyShape;
import it.unibo.dimhol.components.*;
import it.unibo.dimhol.effects.IncreaseCurrentHealthEffect;

/**
 * Implementation of a factory to create various entities.
 */
public class GenericFactory {

    private static final double BULLET_WIDTH = 10;
    private static final double BULLET_HEIGHT = 10;
    private static final double MELEE_WIDTH = 30;
    private static final double MELEE_HEIGHT = 30;
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
                .add(new HealthComponent(10))
                .add(new VisualDebugComponent(0))
                .build();
    }

    public Entity createObstacle(final double x, final double y) {
        return new EntityBuilder().add(new PositionComponent(new Vector2D(100,100)))
                .add(new BodyComponent(new RectBodyShape(W, H), true))
                .add(new VisualDebugComponent(1))
                .build();
    }

    public Entity createHeart(final double x, final double y){
        return new EntityBuilder().add(new HeartComponent())
            .add(new PositionComponent(new Vector2D(x, y)))
            .add(new BodyComponent(new RectBodyShape(W,H), false))
            .add(new PickableComponent(new IncreaseCurrentHealthEffect(1)))
            .add(new VisualDebugComponent(2))
            .build();
    }

    public Entity createZombieEnemy(final double x, final double y) {
        return new EntityBuilder()
                .add(new AiComponent(new RoutineFactory().createZombieRoutine()))
                .add(new PositionComponent(new Vector2D(x, y)))
                .add(new MovementComponent(new Vector2D(0,0), ENEMY_SPEED, true))
                .add(new BodyComponent(new RectBodyShape(W, H), true))
                .add(new VisualDebugComponent(1))
                .build();
    }

    public Entity createBullet(final double dirX, final double dirY, final Entity entity) {
        return new EntityBuilder()
                .add(new PickableComponent(new IncreaseCurrentHealthEffect(1)))
                .add(new PositionComponent(setWeaponPosition(dirX, dirY, entity, BULLET_WIDTH, BULLET_HEIGHT)))
                .add(new MovementComponent(new Vector2D(dirX, dirY), 2, true))
                .add(new BodyComponent(new RectBodyShape(BULLET_WIDTH, BULLET_HEIGHT), false))
                .add(new VisualDebugComponent(3))
                .build();
    }

    private Vector2D setWeaponPosition(final double dirX, final double dirY, final Entity entity,
                                       final double weaponWidth, final double weaponHeight) {

        PositionComponent entityPos = (PositionComponent) entity.getComponent(PositionComponent.class);
        BodyComponent entityBody = (BodyComponent) entity.getComponent(BodyComponent.class);
        var enemyHeight = entityBody.getBs().getBoundingHeight();
        var enemyWidth = entityBody.getBs().getBoundingWidth();
        var centralEntityPos = MathUtilities.getCentralPosition(entityPos, entityBody);
        double bulletX;
        double bulletY;

        if (dirX == 1) {
            bulletX = centralEntityPos.getX() + (enemyWidth / 2);
            bulletY = centralEntityPos.getY();
        } else if (dirX == -1) {
            bulletX = centralEntityPos.getX() - (enemyWidth / 2) - weaponWidth;
            bulletY = centralEntityPos.getY();
        } else if (dirY == 1) {
            bulletX = centralEntityPos.getX();
            bulletY = centralEntityPos.getY() + (enemyHeight / 2);
        } else {
            bulletX = centralEntityPos.getX();
            bulletY = centralEntityPos.getY() - (enemyHeight / 2) - weaponHeight;
        }

        return new Vector2D(bulletX, bulletY);
    }

    public Entity createShooterEnemy(final double x, final double y) {
        return new EntityBuilder()
                .add(new AiComponent(new RoutineFactory().createShooterRoutine()))
                .add(new PositionComponent(new Vector2D(x, y)))
                .add(new MovementComponent(new Vector2D(0,0), ENEMY_SPEED, true))
                .add(new BodyComponent(new RectBodyShape(W, H), true))
                .add(new VisualDebugComponent(1))
                .build();
    }


    public Entity createMeleeAttack(final double dirX, final double dirY, final Entity entity) {
        return new EntityBuilder()
                .add(new PickableComponent(new IncreaseCurrentHealthEffect(1)))
                .add(new PositionComponent(setWeaponPosition(dirX, dirY, entity, MELEE_WIDTH, MELEE_HEIGHT)))
                .add(new BodyComponent(new RectBodyShape(MELEE_WIDTH, MELEE_HEIGHT), false))
                .add(new VisualDebugComponent(4))
                .build();
    }

}
