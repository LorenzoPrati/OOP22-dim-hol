package dimhol.systems;

import dimhol.core.World;
import dimhol.entity.Entity;
import dimhol.events.AddEntityEvent;
import dimhol.components.PlayerComponent;

/**
 * A system to handle player logic.
 */
public class PlayerInputSystem extends AbstractSystem {

    /**
     * Constructs a PlayerInputSystem. Iterates through entities that has {@link PlayerComponent}.
     */
    public PlayerInputSystem() {
        super(PlayerComponent.class);
    }

    /**
     * Handle player logic.
     *
     * @param entity  the entity to process
     * @param dt
     */
    @Override
    public void process(final Entity entity, final double dt, final World world) {
        var input = world.getInput();
        var player = (PlayerComponent) entity.getComponent(PlayerComponent.class);

        var currState = player.getState();
        currState.update(dt, entity);
        if (currState.canTransition()) {
            var newState = currState.transition(input);
            newState.ifPresent(s -> {
                currState.exit();
                player.setState(s);
                s.entry(entity);
            });
            player.getState().execute(input).forEach(e -> world.notifyEvent(new AddEntityEvent(e)));
        }
        player.getState().updateAnimation();
    }
}
