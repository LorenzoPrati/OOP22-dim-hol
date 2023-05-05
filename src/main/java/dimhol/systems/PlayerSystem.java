package dimhol.systems;

import dimhol.core.WorldImpl;
import dimhol.entity.Entity;
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
        var mov = (MovementComponent) e.getComponent(MovementComponent.class);
        var animation = (AnimationComponent) e.getComponent(AnimationComponent.class);

        player.getState().updateTime(dt);
        if (animation.isBlocking() && !animation.isCompleted()) {
            animation.setState(animation.getState());
        }
        else {
            var newState = player.getState().transition(input);
            if (newState.isPresent()) {
                player.getState().exit(e);
                player.setState(newState.get());
            }
            player.getState().execute(input, e).forEach(this.world::notifyEvent);
            animation.setState(player.getState().getDesc() + " " + DirectionUtil.getStringFromVec(mov.getDir()));
        }
    }
}
