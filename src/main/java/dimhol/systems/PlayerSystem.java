package dimhol.systems;

import dimhol.core.WorldImpl;
import dimhol.entity.Entity;
import dimhol.events.AddEntityEvent;
import dimhol.logic.util.DirectionUtil;
import dimhol.components.AnimationComponent;
import dimhol.components.MovementComponent;
import dimhol.components.PlayerComponent;

/**
 * A system to handle player logic.
 */
public class PlayerSystem extends AbstractSystem {

    /**
     * Constructs a PlayerSystem. Iterates through entities that has {@link PlayerComponent}.
     *
     * @param w the world
     */
    public PlayerSystem(final WorldImpl w) {
        super(w, PlayerComponent.class);
    }

    /**
     * Handle player logic.
     *
     * @param e  the entity to process
     * @param dt
     */
    @Override
    public void process(final Entity e, double dt) {
        var input = this.world.getInput();
        var player = (PlayerComponent) e.getComponent(PlayerComponent.class);

        var currState = player.getState();
        currState.update(dt, e);
        if (currState.canTransition()) {
            var newState = currState.transition(input);
            newState.ifPresent(s -> {
                currState.exit();
                player.setState(s);
                s.entry(e);
            });
            player.getState().execute(input).forEach(en -> world.notifyEvent(new AddEntityEvent(en)));
        }
        player.getState().updateAnimation();
    }
}
