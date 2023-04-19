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

public class MeleeAttaackAction implements Action {

    private GenericFactory genericFactory = new GenericFactory();
    private BodyComponent playerBody;
    private BodyComponent enemyBody;
    private Vector2D playerCentralPos;
    private Vector2D enemyCentralPos;
    private PositionComponent playerPos;
    private PositionComponent enemyPos;

    @Override
    public boolean canExecute(Entity player, Entity enemy) {

        playerPos = (PositionComponent) player.getComponent(PositionComponent.class);
        playerBody = (BodyComponent) player.getComponent(BodyComponent.class);
        enemyPos = (PositionComponent) enemy.getComponent(PositionComponent.class);
        enemyBody = (BodyComponent) enemy.getComponent(BodyComponent.class);

        playerCentralPos = MathUtilities.getCentralPosition(playerPos, playerBody);
        enemyCentralPos = MathUtilities.getCentralPosition(enemyPos, enemyBody);

        // int angle = MathUtilities.getAngle(playerCentralPos, enemyCentralPos);
        double aggroRay = MathUtilities.getDistance(enemyPos.getPos(), enemyCentralPos);

        return MathUtilities.getDistance(playerCentralPos, enemyCentralPos) < aggroRay;
    }

    @Override
    public Optional<List<Event>> execute(Entity enemy) {
        var movComp = (MovementComponent) enemy.getComponent(MovementComponent.class);
        movComp.setEnabled(false);

        List<Event> swords = new ArrayList<>();
        var enemyHeight = enemyBody.getBs().getBoundingHeight();
        var enemyWidth = enemyBody.getBs().getBoundingWidth();

        double angle = MathUtilities.getAngle(playerCentralPos, enemyCentralPos);

        if (angle > -45 && angle < 45) {
            if (playerPos.getPos().getX() > enemyPos.getPos().getX()) {
                // right
                Entity sword = genericFactory.createMeleeAttack();
                sword.addComponent(new PositionComponent(new Vector2D((enemyPos.getPos().getX() +
                        enemyWidth), enemyPos.getPos().getY() + (enemyHeight / 2))));
                swords.add(new AddEntityEvent(sword));

            } else {
                //left
                Entity sword = genericFactory.createMeleeAttack();
                var bodyBullet = (BodyComponent) sword.getComponent(BodyComponent.class);
                sword.addComponent(new PositionComponent(new Vector2D((enemyPos.getPos().getX() -
                        bodyBullet.getBs().getBoundingWidth()), enemyPos.getPos().getY() + (enemyHeight / 2))));
                swords.add(new AddEntityEvent(sword));

            }
        } else if (angle > 45 && angle < 90 || angle < -45 && angle > -90) {
            if (playerPos.getPos().getY() > enemyPos.getPos().getY()) {
                //down
                Entity sword = genericFactory.createMeleeAttack();
                sword.addComponent(new PositionComponent(new Vector2D((enemyPos.getPos().getX() +
                        (enemyHeight / 2)), (enemyPos.getPos().getY() + enemyHeight))));
                swords.add(new AddEntityEvent(sword));

            } else {
                //up
                Entity sword = genericFactory.createMeleeAttack();
                var bodyBullet = (BodyComponent) sword.getComponent(BodyComponent.class);
                sword.addComponent(new PositionComponent(new Vector2D((enemyPos.getPos().getX() +
                        (enemyWidth / 2)), (enemyPos.getPos().getY() - bodyBullet.getBs().getBoundingHeight()))));
                swords.add(new AddEntityEvent(sword));

            }
        }
        return Optional.of(swords);
    }
}
