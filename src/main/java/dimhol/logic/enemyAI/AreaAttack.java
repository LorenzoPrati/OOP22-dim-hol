package dimhol.logic.enemyAI;

import dimhol.events.AddEntityEvent;
import dimhol.events.WorldEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * It is a strategy that performs an area attack around the boss.
 */
public final class AreaAttack extends AbstractAction {

    private final double areaAttackAggro;
    private final double areaAttackDamage;

    /**
     * AreaAttack constructor.
     * @param areaAttackAggro the radius of the area in which the presence
     *                        of an enemy (the player) activates this strategy
     * @param areaAttackDamage the damage inflicted by the area attack
     */
    public AreaAttack(final double areaAttackAggro, final double areaAttackDamage) {
        this.areaAttackAggro = areaAttackAggro;
        this.areaAttackDamage = areaAttackDamage;
    }

    @Override
    public Optional<List<WorldEvent>> execute() {
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
