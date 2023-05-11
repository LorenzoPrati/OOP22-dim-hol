package dimhol.logic.ai;

import dimhol.events.Event;
import org.locationtech.jts.math.Vector2D;

import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * Random Movement is a strategy that moves randomly an AI.
 */
public final class RandomMovement extends AbstractAction {

    /**
     * Random Movement constructor.
     * @param changeDirectionTime is the time that passes before the entity changes the direction.
     */
    public RandomMovement(final int changeDirectionTime) {
        setWaitingTime(changeDirectionTime);
    }

    @Override
    public Optional<List<Event>> execute() {
        getMovComp().setEnabled(true);
        Vector2D[] directions = {new Vector2D(0, 1), new Vector2D(0, -1), new Vector2D(1, 0), new Vector2D(-1, 0)};
        int randDirIndex = new Random().nextInt(directions.length);
        getAi().setPrevTime(getAi().getCurrentTime());
        getMovComp().setDir(directions[randDirIndex]);
        return Optional.empty();
    }

}
