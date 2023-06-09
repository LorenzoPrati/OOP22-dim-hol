package dimhol.logic.ai.boss;

import dimhol.components.HealthComponent;
import dimhol.events.AddEntityEvent;
import dimhol.events.WorldEvent;
import dimhol.logic.ai.AbstractAction;
import dimhol.logic.ai.BehaviourUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Strategy that performs a boss melee attack within the ranged area of the boss.
 */
public final class BossMeleeAttackAction extends AbstractAction {

    private static final int THRESHOLD_ACTIVATION = 10;

    /**
     * Constructs an BossMeleeAttackAction.
     *
     * @param areaAttackAggro      the radius of the area in which the presence
     *                             of an enemy (the player) activates this strategy
     * @param areaAttackReloadTime the time to reload the area attack
     */
    public BossMeleeAttackAction(final double areaAttackAggro, final double areaAttackReloadTime) {
        setAggroRay(areaAttackAggro);
        setWaitingTime(areaAttackReloadTime);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<List<WorldEvent>> execute() {
        getMovComp().setEnabled(false);
        final var direction = BehaviourUtil.getPlayerDirection(getPlayerCentralPos(), getEnemyCentralPos());
        getMovComp().setDir(direction);
        if (getAi().getCurrentTime() - getAi().getPrevTime() >= getWaitingTime()) {
            getAi().setPrevTime(getAi().getCurrentTime());
            return performBossMeleeAttack();
        }
        return Optional.empty();
    }

    @Override
    public boolean canExecute() {
        final var bossHealth = (HealthComponent) getEnemy().getComponent(HealthComponent.class);
        return bossHealth.getCurrentHealth() <= THRESHOLD_ACTIVATION && super.canExecute();
    }

    private Optional<List<WorldEvent>> performBossMeleeAttack() {
        final List<WorldEvent> events = new ArrayList<>();
        events.add(new AddEntityEvent(getAttackFactory().createBossMeleeAttack(getEnemy())));
        return Optional.of(events);
    }
}
