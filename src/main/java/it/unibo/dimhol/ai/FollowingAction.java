package it.unibo.dimhol.ai;

import it.unibo.dimhol.components.BodyComponent;
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
    private Vector2D playerCentralPos;
    private Vector2D enemyCentralPos;

    @Override
    public boolean canExecute(Entity player, Entity enemy) {

        playerPos = (PositionComponent) player.getComponent(PositionComponent.class);
        BodyComponent playerBody = (BodyComponent) player.getComponent(BodyComponent.class);
        enemyPos = (PositionComponent) enemy.getComponent(PositionComponent.class);
        BodyComponent enemyBody = (BodyComponent) enemy.getComponent(BodyComponent.class);

        playerCentralPos = MathUtilities.getCentralPosition(playerPos.getPos(), playerBody.getBodyShape());
        enemyCentralPos = MathUtilities.getCentralPosition(enemyPos.getPos(), enemyBody.getBodyShape());

        return MathUtilities.getDistance(playerCentralPos, enemyCentralPos) < AGGRO_RAY;

    }

    @Override
    public Optional<List<Event>> execute(Entity enemy) {

        var movComp = (MovementComponent) enemy.getComponent(MovementComponent.class);
        movComp.setEnabled(true);
        double angle = MathUtilities.getAngle(playerCentralPos, enemyCentralPos);

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
