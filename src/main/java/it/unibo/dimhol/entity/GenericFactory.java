package it.unibo.dimhol.entity;

import it.unibo.dimhol.ai.RoutineFactory;
import org.locationtech.jts.math.Vector2D;

import it.unibo.dimhol.commons.shapes.RectBodyShape;
import it.unibo.dimhol.components.*;
import it.unibo.dimhol.effects.IncreaseCurrentHealthEffect;

import java.util.Optional;

/**
 * Implementation of a factory to create various entities.
 */
public class GenericFactory {

    private static final double BULLET_WIDTH = 10;
    private static final double BULLET_HEIGHT = 10;
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
                .add(new PositionComponent(setBulletPosition(dirX, dirY, entity)))
                .add(new MovementComponent(new Vector2D(dirX, dirY), 2, true))
                .add(new BodyComponent(new RectBodyShape(BULLET_WIDTH, BULLET_HEIGHT), false))
                .add(new VisualDebugComponent(3))
                .build();
    }

    private Vector2D setBulletPosition(final double dirX, final double dirY, final Entity entity) {
        PositionComponent entityPos = (PositionComponent) entity.getComponent(PositionComponent.class);
        BodyComponent entityBody = (BodyComponent) entity.getComponent(BodyComponent.class);
        var enemyHeight = entityBody.getBs().getBoundingHeight();
        var enemyWidth = entityBody.getBs().getBoundingWidth();

        double bulletX;
        double bulletY;

        if (dirX == 1) {
            bulletX = entityPos.getPos().getX() + enemyWidth;
            bulletY = entityPos.getPos().getY() + (enemyHeight / 2);
        } else if (dirX == -1) {
            bulletX = entityPos.getPos().getX() - BULLET_WIDTH;
            bulletY = entityPos.getPos().getY() + (enemyHeight / 2);
        } else if (dirY == 1) {
            bulletX = entityPos.getPos().getX() + (enemyHeight / 2);
            bulletY = entityPos.getPos().getY() + enemyHeight;
        } else {
            bulletX = entityPos.getPos().getX() + (enemyWidth / 2);
            bulletY = entityPos.getPos().getY() - BULLET_HEIGHT;
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

    public Entity createMeleeAttack() {
        return new EntityBuilder()
                .add(new PickableComponent(new IncreaseCurrentHealthEffect(1)))
                .add(new BodyComponent(new RectBodyShape(30, 30), false))
                .add(new VisualDebugComponent(4))
                .build();
    }
}
