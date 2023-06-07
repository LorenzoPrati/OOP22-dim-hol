package dimhol.systems;

import dimhol.core.World;
import dimhol.entity.Entity;
import dimhol.events.AddEntityEvent;
import dimhol.components.PlayerComponent;

/**
 * A system to handle player input.
 */
public class PlayerInputSystem extends AbstractSystem {

    /**
     * Constructs a PlayerInputSystem. Iterates through entities that has {@link PlayerComponent}.
     */
    public PlayerInputSystem() {
        super(PlayerComponent.class);
    }

    /**
     * Handle player input logic.
     *
     * @param entity the entity to process
     * @param deltaTime the delta time
     */
    @Override
    protected void process(final Entity entity, final double deltaTime, final World world) {
        final var input = world.getInput();
        final var player = (PlayerComponent) entity.getComponent(PlayerComponent.class);

        final var currState = player.getState();
        currState.update(deltaTime, entity);
        if (currState.canTransition()) {
            final var newState = currState.transition(input);
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
