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

public class MeleeAttackAction extends AbstractAction {

    private static final int RELOAD_GUN_TIME = 2000;
    private final GenericFactory genericFactory = new GenericFactory();
    private Vector2D playerCentralPos;
    private Vector2D enemyCentralPos;
    private PositionComponent playerPos;
    private PositionComponent enemyPos;
    private Entity enemy;
    private Entity player;

    @Override
    public boolean canExecute(Entity enemy) {
        this.enemy = enemy;
        this.player = super.getPlayer();


        playerPos = (PositionComponent) player.getComponent(PositionComponent.class);
        BodyComponent playerBody = (BodyComponent) player.getComponent(BodyComponent.class);
        enemyPos = (PositionComponent) enemy.getComponent(PositionComponent.class);
        BodyComponent enemyBody = (BodyComponent) enemy.getComponent(BodyComponent.class);

        playerCentralPos = MathUtilities.getCentralPosition(playerPos.getPos(), playerBody.getBodyShape());
        enemyCentralPos = MathUtilities.getCentralPosition(enemyPos.getPos(), enemyBody.getBodyShape());

        double aggroRay = (MathUtilities.getDistance(enemyPos.getPos(), enemyCentralPos))
                + (MathUtilities.getDistance(playerPos.getPos(), playerCentralPos));

        return MathUtilities.getDistance(playerCentralPos, enemyCentralPos) <= aggroRay;
    }

    @Override
    public Optional<List<Event>> execute(Entity enemy) {
        var aiComp = (AiComponent) enemy.getComponent(AiComponent.class);
        var movComp = (MovementComponent) enemy.getComponent(MovementComponent.class);
        movComp.setEnabled(false);

        if (System.currentTimeMillis() - aiComp.getPrevMovTime() >= RELOAD_GUN_TIME) {
            aiComp.setPrevMovTime(System.currentTimeMillis());
            return Optional.of(meleeAttack());
        } else {
            return Optional.empty();
        }

    }

    private List<Event> meleeAttack() {
        List<Event> attacks = new ArrayList<>();

        double angle = MathUtilities.getAngle(playerCentralPos, enemyCentralPos);

        switch (MathUtilities.getVisionZone(angle, playerPos.getPos(), enemyPos.getPos())) {
            case "right" -> attacks.add(new AddEntityEvent(genericFactory.createMeleeAttack(1, 0, enemy)));
            case "left" -> attacks.add(new AddEntityEvent(genericFactory.createMeleeAttack(-1, 0, enemy)));
            case "down" -> attacks.add(new AddEntityEvent(genericFactory.createMeleeAttack(0, 1, enemy)));
            case "up" -> attacks.add(new AddEntityEvent(genericFactory.createMeleeAttack(0, -1, enemy)));
        }

        return attacks;
    }
}
