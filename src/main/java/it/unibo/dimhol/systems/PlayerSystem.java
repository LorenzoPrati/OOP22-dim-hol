package it.unibo.dimhol.systems;

import it.unibo.dimhol.World;
import it.unibo.dimhol.components.AnimationComponent;
import it.unibo.dimhol.components.MovementComponent;
import it.unibo.dimhol.components.PlayerComponent;
import it.unibo.dimhol.entity.Entity;

/**
 * A system to handle player logic.
 */
public class PlayerSystem extends AbstractSystem {

    /**
     * Constructs a PlayerSystem. Iterates through entities that has {@link PlayerComponent}.
     *
     * @param w the world
     */
    public PlayerSystem(final World w) {
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
        var input = this.world.getInputListener();
        var player = (PlayerComponent) e.getComponent(PlayerComponent.class);
        var mov = (MovementComponent) e.getComponent(MovementComponent.class);
        var animation = (AnimationComponent) e.getComponent(AnimationComponent.class);

        player.getState().updateTime(dt);
        if (animation.isBlocking() && !animation.isCompleted()) {
            animation.setState(animation.getState());
            mov.setEnabled(false);
        }
        else {
            player.getState().transition(input).ifPresent(player::setState);
            player.getState().execute(input, e).forEach(this.world::notifyEvent);
            animation.setState(player.getState().getDesc());
        }


    }
}
