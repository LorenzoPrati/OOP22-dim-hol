package it.unibo.dimhol.ai;

import it.unibo.dimhol.components.AiComponent;
import it.unibo.dimhol.components.BodyComponent;
import it.unibo.dimhol.components.MovementComponent;
import it.unibo.dimhol.components.PositionComponent;
import it.unibo.dimhol.entity.Entity;
import it.unibo.dimhol.entity.GenericFactory;
import it.unibo.dimhol.events.AddEntityEvent;
import it.unibo.dimhol.events.Event;

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
        var cH = enemyBody.getBs().getBoundingHeight() / 2;
        var cW = enemyBody.getBs().getBoundingWidth() / 2;

        AiMathUtil aiMathUtil = new AiMathUtil(playerPos.getPos().getX(), playerPos.getPos().getY(),
                enemyPos.getPos().getX(), enemyPos.getPos().getY());
        int angle = aiMathUtil.getAngle();

        if (angle > -45 && angle < 45) {
            if (playerPos.getPos().getX() > enemyPos.getPos().getX()) {
                bullets.add(new AddEntityEvent(ef.createBullet(enemyPos.getPos().getX() + cH,
                        enemyPos.getPos().getY() + cW, 1, 0)));
            } else {
                bullets.add(new AddEntityEvent(ef.createBullet(enemyPos.getPos().getX() + cH,
                        enemyPos.getPos().getY() + cW, -1, 0)));
            }
        } else if (angle > 45 && angle < 90 || angle < -45 && angle > -90 ) {
            if (playerPos.getPos().getY() > enemyPos.getPos().getY()) {
                bullets.add(new AddEntityEvent(ef.createBullet(enemyPos.getPos().getX() + cH,
                        enemyPos.getPos().getY() + cW, 0, 1)));
            } else {
                bullets.add(new AddEntityEvent(ef.createBullet(enemyPos.getPos().getX() + cH,
                        enemyPos.getPos().getY() + cW, 0, -1)));
            }
        }
        return bullets;
    }

}
