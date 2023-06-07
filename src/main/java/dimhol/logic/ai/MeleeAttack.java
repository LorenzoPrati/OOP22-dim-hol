package dimhol.logic.ai;

import dimhol.events.AddEntityEvent;
import dimhol.events.WorldEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This class creates an attack near the entity.
 */
public final class MeleeAttack extends AbstractAction {

    /**
     * Construct a MeleeAttack.
     * @param meleeAttackAggro
     * @param meleeReloadTime
     */
    public MeleeAttack(final double meleeAttackAggro, final double meleeReloadTime) {
        setAggroRay(meleeAttackAggro);
        setWaitingTime(meleeReloadTime);
    }

    @Override
    public Optional<List<WorldEvent>> execute() {
        getMovComp().setEnabled(false);
        final var direction = BehaviourUtil.getPlayerDirection(getPlayerCentralPos(), getEnemyCentralPos());
        getMovComp().setDir(direction);
        if (reloadTimePassed()) {
            return meleeAttack();
        }
        return Optional.empty();
    }

    private Optional<List<WorldEvent>> meleeAttack() {
        final List<WorldEvent> attacks = new ArrayList<>();
        attacks.add(new AddEntityEvent(getAttackFactory().createMeleeAttack(getEnemy())));
        return Optional.of(attacks);
    }

}
