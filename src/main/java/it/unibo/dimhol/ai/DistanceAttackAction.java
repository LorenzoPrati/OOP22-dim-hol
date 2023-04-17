package it.unibo.dimhol.ai;

import it.unibo.dimhol.components.AiComponent;
import it.unibo.dimhol.components.MovementComponent;
import it.unibo.dimhol.components.PositionComponent;
import it.unibo.dimhol.entity.Entity;
import it.unibo.dimhol.entity.GenericFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DistanceAttackAction implements Action {

    private static final int RELOAD_GUN_TIME = 1000;
    private final int aggroRay;
    private PositionComponent playerPos;
    private PositionComponent enemyPos;
    private final GenericFactory entityFactory = new GenericFactory();

    public DistanceAttackAction(int aggroRay) {
        this.aggroRay = aggroRay;
    }

    @Override
    public boolean canExecute(Entity player, Entity enemy) {
        playerPos = (PositionComponent) player.getComponent(PositionComponent.class);
        enemyPos = (PositionComponent) enemy.getComponent(PositionComponent.class);

        var distance = playerPos.getPos().distance(enemyPos.getPos());
        return distance <= aggroRay;
    }

    @Override
    public Optional<List<Entity>> execute(Entity enemy) {
        var movComp = (MovementComponent) enemy.getComponent(MovementComponent.class);
        var aiComp = (AiComponent) enemy.getComponent(AiComponent.class);

        if (System.currentTimeMillis() - aiComp.getPrevMovTime() >= RELOAD_GUN_TIME) {
            return Optional.of(shoot(movComp));
        } else {
            return Optional.empty();
        }
    }

    private List<Entity> shoot(MovementComponent movComp) {
        List<Entity> bullets = new ArrayList<>();
        if(enemyPos.getPos().angle(playerPos.getPos()) > 45 && enemyPos.getPos().angle(playerPos.getPos()) < 135) {
            // bullets.add(entityFactory.createBullet());
        }

        return bullets;
    }

}
