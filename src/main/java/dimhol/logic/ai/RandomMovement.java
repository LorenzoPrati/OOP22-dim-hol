package dimhol.logic.ai;

import dimhol.components.AiComponent;
import dimhol.events.Event;
import dimhol.components.MovementComponent;
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
        var movComp = (MovementComponent) getEnemy().getComponent(MovementComponent.class);
        movComp.setEnabled(true);
        var aiComp = (AiComponent) getEnemy().getComponent(AiComponent.class);
        Vector2D[] directions = {new Vector2D(0, 1), new Vector2D(0, -1), new Vector2D(1, 0), new Vector2D(-1, 0)};
        int randDirIndex = new Random().nextInt(directions.length);
        aiComp.setPrevTime(aiComp.getCurrentTime());
        movComp.setDir(directions[randDirIndex]);
        return Optional.empty();
    }

}
