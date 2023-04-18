package it.unibo.dimhol.ai;

import it.unibo.dimhol.components.AiComponent;
import it.unibo.dimhol.components.BodyComponent;
import it.unibo.dimhol.components.MovementComponent;
import it.unibo.dimhol.components.PositionComponent;
import it.unibo.dimhol.entity.Entity;
import it.unibo.dimhol.entity.GenericFactory;
import it.unibo.dimhol.events.AddEntityEvent;
import it.unibo.dimhol.events.Event;
import org.locationtech.jts.math.Vector2D;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DistanceAttackAction implements Action {

    private static final int RELOAD_GUN_TIME = 2000;
    private static final int AGGRO_RAY = 300;
    private PositionComponent playerPos;
    private PositionComponent enemyPos;
    private BodyComponent enemyBody;
    private final GenericFactory ef = new GenericFactory();

    @Override
    public boolean canExecute(Entity player, Entity enemy) {
        playerPos = (PositionComponent) player.getComponent(PositionComponent.class);
        enemyPos = (PositionComponent) enemy.getComponent(PositionComponent.class);
        enemyBody = (BodyComponent) enemy.getComponent(BodyComponent.class);

        var distance = playerPos.getPos().distance(enemyPos.getPos());
        return distance < AGGRO_RAY;
    }

    @Override
    public Optional<List<Event>> execute(Entity enemy) {
        var movComp = (MovementComponent) enemy.getComponent(MovementComponent.class);
        var aiComp = (AiComponent) enemy.getComponent(AiComponent.class);

        movComp.setEnabled(false);
        if (System.currentTimeMillis() - aiComp.getPrevMovTime() >= RELOAD_GUN_TIME) {
            aiComp.setPrevMovTime(System.currentTimeMillis());
            return Optional.of(shoot());
        } else {
            return Optional.empty();
        }
    }

    private List<Event> shoot() {
        List<Event> bullets = new ArrayList<>();
        var enemyHeight = enemyBody.getBs().getBoundingHeight();
        var enemyWidth = enemyBody.getBs().getBoundingWidth();

        AiMathUtil aiMathUtil = new AiMathUtil(playerPos.getPos().getX(), playerPos.getPos().getY(),
                enemyPos.getPos().getX(), enemyPos.getPos().getY());
        int angle = aiMathUtil.getAngle();

        if (angle > -45 && angle < 45) {
            if (playerPos.getPos().getX() > enemyPos.getPos().getX()) {

                Entity bullet = ef.createBullet(1, 0);
                bullet.addComponent(new PositionComponent(new Vector2D((enemyPos.getPos().getX() +
                        enemyWidth), enemyPos.getPos().getY() + (enemyHeight / 2))));
                bullets.add(new AddEntityEvent(bullet));

            } else {

                Entity bullet = ef.createBullet(-1, 0);
                var bodyBullet = (BodyComponent) bullet.getComponent(BodyComponent.class);
                bullet.addComponent(new PositionComponent(new Vector2D((enemyPos.getPos().getX() -
                        bodyBullet.getBs().getBoundingWidth()), enemyPos.getPos().getY() + (enemyHeight / 2))));
                bullets.add(new AddEntityEvent(bullet));

            }
        } else if (angle > 45 && angle < 90 || angle < -45 && angle > -90 ) {
            if (playerPos.getPos().getY() > enemyPos.getPos().getY()) {

                Entity bullet = ef.createBullet(0, 1);
                bullet.addComponent(new PositionComponent(new Vector2D((enemyPos.getPos().getX() +
                        (enemyHeight / 2)), (enemyPos.getPos().getY() + enemyHeight))));
                bullets.add(new AddEntityEvent(bullet));

            } else {

                Entity bullet = ef.createBullet(0, -1);
                var bodyBullet = (BodyComponent) bullet.getComponent(BodyComponent.class);
                bullet.addComponent(new PositionComponent(new Vector2D((enemyPos.getPos().getX() +
                        (enemyWidth / 2)), (enemyPos.getPos().getY() - bodyBullet.getBs().getBoundingHeight()))));
                bullets.add(new AddEntityEvent(bullet));

            }
        }
        return bullets;
    }

}
