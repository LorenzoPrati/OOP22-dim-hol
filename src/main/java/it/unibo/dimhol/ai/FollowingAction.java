package it.unibo.dimhol.ai;

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

        AiMathUtil aiMathUtil = new AiMathUtil(playerPos.getPos().getX(), playerPos.getPos().getY(),
                enemyPos.getPos().getX(), enemyPos.getPos().getY());
        int angle = aiMathUtil.getAngle();

        if (angle > -45 && angle < 45) {
            if (playerPos.getPos().getX() > enemyPos.getPos().getX()) {
                movComp.setDir(new Vector2D(1, 0));
            } else {
                movComp.setDir(new Vector2D(-1, 0));
            }
        } else if (angle > 45 && angle < 90 || angle < -45 && angle > -90) {
            if (playerPos.getPos().getY() > enemyPos.getPos().getY()) {
                movComp.setDir(new Vector2D(0, 1));
            } else {
                movComp.setDir(new Vector2D(0, -1));
            }
        }

        return Optional.empty();
    }
}
