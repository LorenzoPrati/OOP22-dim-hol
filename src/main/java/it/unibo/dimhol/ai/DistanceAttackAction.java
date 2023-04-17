package it.unibo.dimhol.ai;

import it.unibo.dimhol.components.AiComponent;
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

    private static final int RELOAD_GUN_TIME = 3000;
    private static final int AGGRO_RAY = 300;
    private PositionComponent playerPos;
    private PositionComponent enemyPos;
    private final GenericFactory ef = new GenericFactory();

    @Override
    public boolean canExecute(Entity player, Entity enemy) {
        playerPos = (PositionComponent) player.getComponent(PositionComponent.class);
        enemyPos = (PositionComponent) enemy.getComponent(PositionComponent.class);

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
            return Optional.of(shoot(movComp));
        } else {
            return Optional.empty();
        }
    }

    private List<Event> shoot(MovementComponent movComp) {
        List<Event> bullets = new ArrayList<>();
        /*
        if(enemyPos.getPos().angle(playerPos.getPos()) > 45 && enemyPos.getPos().angle(playerPos.getPos()) < 135) {

        }
        */
        bullets.add(new AddEntityEvent(ef.createBullet(enemyPos.getPos().getX(), enemyPos.getPos().getY(), 1, 0)));
        bullets.add(new AddEntityEvent(ef.createBullet(enemyPos.getPos().getX(), enemyPos.getPos().getY(), -1, 0)));
        return bullets;
    }

}
