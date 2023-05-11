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

    /**
     * Distance Attack constructor.
     * @param distanceAttackAggro is the radius of the area in which the presence
     *                           of an enemy (the player) activates this strategy
     * @param distanceAttackReloadTime is the cooldown time of the ranged attac
     */
    public DistanceAttack(final int distanceAttackAggro, final int distanceAttackReloadTime) {
        setAggroRay(distanceAttackAggro);
        setWaitingTime(distanceAttackReloadTime);
    }

    @Override
    public Optional<List<Event>> execute() {
        var movComp = (MovementComponent) getEnemy().getComponent(MovementComponent.class);
        var aiComp = (AiComponent) getEnemy().getComponent(AiComponent.class);
        movComp.setEnabled(false);
        aiComp.setPrevTime(aiComp.getCurrentTime());
        return distanceAttack();
    }

    private Optional<List<Event>> distanceAttack() {
        List<Event> attacks = new ArrayList<>();
        var dir = AttackUtil.getPlayerDirection(getPlayerCentralPos(), getEnemyCentralPos());
        var pos = AttackUtil.getAttackPos(dir, getEnemyCentralPos(), getEnemyBody().getBodyShape(),
                AttackFactory.MELEE_WIDTH, AttackFactory.MELEE_HEIGHT);

        attacks.add(new AddEntityEvent(getAttackFactory().createDistanceAttack(pos, dir, getEnemy())));
        return Optional.of(attacks);
    }
}
