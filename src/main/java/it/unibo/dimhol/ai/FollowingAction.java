package it.unibo.dimhol.ai;

import it.unibo.dimhol.components.AiComponent;
import it.unibo.dimhol.components.MovementComponent;
import it.unibo.dimhol.components.PositionComponent;
import it.unibo.dimhol.entity.Entity;
import it.unibo.dimhol.events.Event;
import org.locationtech.jts.math.Vector2D;

import java.util.List;
import java.util.Optional;

public class FollowingAction implements Action {

    private static final int AGGRO_RAY = 300;
    private PositionComponent playerPos;
    private PositionComponent enemyPos;

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

        movComp.setEnabled(true);


        return Optional.empty();
    }
}
