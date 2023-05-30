package dimhol.logic.enemyAI;

import dimhol.components.PositionComponent;
import dimhol.entity.factories.BossFactory;
import dimhol.events.AddEntityEvent;
import dimhol.events.WorldEvent;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.math.Vector2D;

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
    public SummonMinions(final int numMinions) {
        this.numMinions = numMinions;
    }

    @Override
    public Optional<List<WorldEvent>> execute() {
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
