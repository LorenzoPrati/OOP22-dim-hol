package dimhol.logic.ai;

import dimhol.components.MovementComponent;
import dimhol.events.Event;

import java.util.List;
import java.util.Optional;

public class BasicMovement extends AbstractAction {

    @Override
    public Optional<List<Event>> execute() {
        var movComp = (MovementComponent) enemy.getComponent(MovementComponent.class);
        movComp.setEnabled(false);
        System.out.println("Sto eseguendo il basic mov senza paramentri");
        return Optional.empty();
    }

}
