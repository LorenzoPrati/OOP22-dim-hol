package dimhol.logic.AI;

import dimhol.events.AddEntityEvent;
import dimhol.events.WorldEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * It is a strategy that summons minions to assist the boss.
 */
public final class SummonMinions extends AbstractAction {

    private final int numMinions;

    /**
     * SummonMinions constructor.
     * @param numMinions the number of minions to summon
     */
    public SummonMinions(final int summonsReloadTime, final int numMinions) {
        setWaitingTime(summonsReloadTime);
        this.numMinions = numMinions;
    }

    @Override
    public Optional<List<WorldEvent>> execute() {
        getMovComp().setEnabled(false);
        var direction = AttackUtil.getPlayerDirection(getPlayerCentralPos(), getEnemyCentralPos());
        getMovComp().setDir(direction);
        if (getAi().getCurrentTime() - getAi().getPrevTime() >= getWaitingTime()) {
            getAi().setPrevTime(getAi().getCurrentTime());
            return summonMinions();
        }
        return Optional.empty();
    }

    private Optional<List<WorldEvent>> summonMinions() {
        List<WorldEvent> events = new ArrayList<>();
        for (int i = 0; i < numMinions; i++) {
            events.add(new AddEntityEvent(getAttackFactory().createSummoningMinions(getEnemy())));
        }
        return Optional.of(events);
    }
}
