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
    private final GenericFactory genericFactory = new GenericFactory();
    private PositionComponent playerPos;
    private PositionComponent enemyPos;
    private Vector2D playerCentralPos;
    private Vector2D enemyCentralPos;
    private Entity enemy;

    @Override
    public boolean canExecute(Entity player, Entity enemy) {
        this.enemy = enemy;

        playerPos = (PositionComponent) player.getComponent(PositionComponent.class);
        BodyComponent playerBody = (BodyComponent) player.getComponent(BodyComponent.class);
        enemyPos = (PositionComponent) enemy.getComponent(PositionComponent.class);
        BodyComponent enemyBody = (BodyComponent) enemy.getComponent(BodyComponent.class);

        playerCentralPos = MathUtilities.getCentralPosition(playerPos.getPos(), playerBody.getBs());
        enemyCentralPos = MathUtilities.getCentralPosition(enemyPos.getPos(), enemyBody.getBs());

        return MathUtilities.getDistance(playerCentralPos, enemyCentralPos) <= AGGRO_RAY;

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

        double angle = MathUtilities.getAngle(playerCentralPos, enemyCentralPos);

        switch (MathUtilities.getVisionZone(angle, playerPos.getPos(), enemyPos.getPos())) {
            case 1 -> bullets.add(new AddEntityEvent(genericFactory.createBullet(1, 0, enemy)));
            case 2 -> bullets.add(new AddEntityEvent(genericFactory.createBullet(-1, 0, enemy)));
            case 3 -> bullets.add(new AddEntityEvent(genericFactory.createBullet(0, 1, enemy)));
            case 4 -> bullets.add(new AddEntityEvent(genericFactory.createBullet(0, -1, enemy)));
        }

        return bullets;
    }

}
