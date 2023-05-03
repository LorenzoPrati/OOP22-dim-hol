package it.unibo.dimhol.logic.ai;

import it.unibo.dimhol.components.AiComponent;
import it.unibo.dimhol.components.MovementComponent;
import it.unibo.dimhol.events.Event;
import org.locationtech.jts.math.Vector2D;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class RandomMovement extends AbstractAction {

    public RandomMovement(int aggroRay, int waitTime) {
        super(aggroRay, waitTime);
    }

    @Override
    public Optional<List<Event>> execute() {
        var movComp = (MovementComponent) entity.getComponent(MovementComponent.class);
        movComp.setEnabled(true);
        var aiComp = (AiComponent) entity.getComponent(AiComponent.class);
        Vector2D[] directions = {new Vector2D(0, 1), new Vector2D(0, -1), new Vector2D(1, 0), new Vector2D(-1, 0)};
        int randDirIndex = new Random().nextInt(directions.length);

        if (System.currentTimeMillis() - aiComp.getPrevMovTime() >= waitTime) {
            aiComp.setPrevMovTime(System.currentTimeMillis());
            movComp.setDir(directions[randDirIndex]);
        }

        return Optional.empty();
    }

}
