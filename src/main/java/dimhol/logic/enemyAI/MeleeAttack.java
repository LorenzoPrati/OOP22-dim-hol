package dimhol.logic.enemyAI;

import dimhol.events.AddEntityEvent;
import dimhol.events.WorldEvent;
import org.locationtech.jts.math.Vector2D;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class MeleeAttack extends AbstractAction {

    public MeleeAttack(final double meleeAttackAggro, final double meleeReloadTime) {
        setAggroRay(meleeAttackAggro);
        setWaitingTime(meleeReloadTime);
    }

    @Override
    public Optional<List<WorldEvent>> execute() {
        getMovComp().setEnabled(false);
        if (getAi().getCurrentTime() - getAi().getPrevTime() >= getWaitingTime()) {
            getAi().setPrevTime(getAi().getCurrentTime());
            return meleeAttack();
        }
        return Optional.empty();
    }

    private Optional<List<WorldEvent>> meleeAttack() {
        List<WorldEvent> attacks = new ArrayList<>();
        attacks.add(new AddEntityEvent(getAttackFactory().createMeleeAttack(getEnemy())));
        return Optional.of(attacks);
    }

    double getMeleeRay(final Vector2D entityPos, final Vector2D enemyCentralPos,
                       final Vector2D playerPos, final Vector2D playerCentralPos) {
        return entityPos.distance(enemyCentralPos) + playerPos.distance(playerCentralPos);
    }
}
