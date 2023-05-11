package dimhol.logic.ai;

import dimhol.components.AiComponent;
import dimhol.components.MovementComponent;
import dimhol.events.AddEntityEvent;
import dimhol.events.Event;
import dimhol.entity.factories.AttackFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * It is a strategy that creates bullets by aiming at the player.
 */
public final class DistanceAttack extends AbstractAction {

    public DistanceAttack(int distanceAttackAggro, int distanceAttackReloadTime) {
        this.aggroRay = distanceAttackAggro;
        this.waitingTime = distanceAttackReloadTime;
    }

    @Override
    public Optional<List<Event>> execute() {
        var movComp = (MovementComponent) enemy.getComponent(MovementComponent.class);
        var aiComp = (AiComponent) enemy.getComponent(AiComponent.class);
        movComp.setEnabled(false);
        aiComp.setPrevTime(aiComp.getCurrentTime());
        return distanceAttack();
    }

    private Optional<List<Event>> distanceAttack() {
        List<Event> attacks = new ArrayList<>();
        var dir = AttackUtil.getPlayerDirection(playerCentralPos, enemyCentralPos);
        var pos = AttackUtil.getAttackPos(dir, enemyCentralPos, enemyBody.getBodyShape(),
                AttackFactory.MELEE_WIDTH, AttackFactory.MELEE_HEIGHT);

        attacks.add(new AddEntityEvent(attackFactory.createDistanceAttack(pos, dir, enemy)));
        return Optional.of(attacks);
    }
}
