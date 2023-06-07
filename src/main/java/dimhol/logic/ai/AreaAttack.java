package dimhol.logic.ai;

import dimhol.events.AddEntityEvent;
import dimhol.events.WorldEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * It is a strategy that performs an area attack around the boss.
 */
public final class AreaAttack extends AbstractAction {

    private final double bossAreaAttackDamage;

    /**
     * AreaAttack constructor.
     * @param areaAttackAggro the radius of the area in which the presence
     *                        of an enemy (the player) activates this strategy
     * @param areaAttackReloadTime The time to reload the area attack
     */
    public AreaAttack(final double areaAttackAggro, final double bossAreaAttackDamage, final double areaAttackReloadTime) {
        setAggroRay(areaAttackAggro);
        this.bossAreaAttackDamage = bossAreaAttackDamage;
        setWaitingTime(areaAttackReloadTime);
    }

    @Override
    public Optional<List<WorldEvent>> execute() {
        getMovComp().setEnabled(false);
        var direction = BehaviourUtil.getPlayerDirection(getPlayerCentralPos(), getEnemyCentralPos());
        getMovComp().setDir(direction);
        if (getAi().getCurrentTime() - getAi().getPrevTime() >= getWaitingTime()) {
            getAi().setPrevTime(getAi().getCurrentTime());
            return areaAttack();
        }
        return Optional.empty();
    }

    private Optional<List<WorldEvent>> areaAttack() {
        List<WorldEvent> events = new ArrayList<>();
        events.add(new AddEntityEvent(getAttackFactory().createAreaAttack(getEnemy())));
        return Optional.of(events);
    }
}
