package dimhol.logic.AI;

import dimhol.events.AddEntityEvent;
import dimhol.events.WorldEvent;

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
        var direction = BehviourUtil.getPlayerDirection(getPlayerCentralPos(), getEnemyCentralPos());
        getMovComp().setDir(direction);
        if (reloadTimePassed()) {
            return meleeAttack();
        }
        return Optional.empty();
    }

    private Optional<List<WorldEvent>> meleeAttack() {
        List<WorldEvent> attacks = new ArrayList<>();
        attacks.add(new AddEntityEvent(getAttackFactory().createMeleeAttack(getEnemy())));
        return Optional.of(attacks);
    }

}
