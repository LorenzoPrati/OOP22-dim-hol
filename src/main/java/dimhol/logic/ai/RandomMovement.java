package dimhol.logic.ai;

import dimhol.events.WorldEvent;
import org.locationtech.jts.math.Vector2D;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Random Movement is a strategy that moves randomly an AI.
 */
public final class RandomMovement extends AbstractAction {

    public RandomMovement(final double changeDirectionTime) {
        setWaitingTime(changeDirectionTime);
    }

    @Override
    public boolean canExecute() {
        return true;
    }

    @Override
    public Optional<List<WorldEvent>> execute() {
        getMovComp().setEnabled(true);
        final Vector2D[] directions = {new Vector2D(0, 1), new Vector2D(0, -1), new Vector2D(1, 0), new Vector2D(-1, 0)};
        if (reloadTimePassed()) {
            getMovComp().setDir(directions[ThreadLocalRandom.current().nextInt(directions.length)]);
        }
        return Optional.empty();
    }

}
