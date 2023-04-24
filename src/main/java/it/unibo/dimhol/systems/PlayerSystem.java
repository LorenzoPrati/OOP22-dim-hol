package it.unibo.dimhol.systems;

import it.unibo.dimhol.World;
import it.unibo.dimhol.components.AnimationComponent;
import it.unibo.dimhol.components.MovementComponent;
import it.unibo.dimhol.components.PlayerComponent;
import it.unibo.dimhol.entity.Entity;
import org.locationtech.jts.math.Vector2D;

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
     * @param e the entity to process
     */
    @Override
    public void process(final Entity e) {
        var mov = (MovementComponent) e.getComponent(MovementComponent.class);
        var an = (AnimationComponent) e.getComponent(AnimationComponent.class);
        var input = this.world.getInputListener();
        if (input.anyMoveKeyPressed()) {
            mov.setEnabled(true);
            if (input.isUp()) {
                mov.setDir(new Vector2D(0,-1));
                an.setState("walking up");
            } else if (input.isDown()) {
                mov.setDir(new Vector2D(0,1));
                an.setState("walking down");
            } else if (input.isLeft()) {
                mov.setDir(new Vector2D(-1,0));
                an.setState("walking left");
            } else if (input.isRight()) {
                mov.setDir(new Vector2D(1,0));
                an.setState("walking right");
            }
        } else {
            mov.setEnabled(false);
            if (mov.getDir().equals(new Vector2D(0,-1))) {
                an.setState("idle up");
            } else if (mov.getDir().equals(new Vector2D(0,1))) {
                an.setState("idle down");
            } else if (mov.getDir().equals(new Vector2D(-1,0))) {
                an.setState("idle left");
            } else if (mov.getDir().equals(new Vector2D(1,0))) {
                an.setState("idle right");
            }
        }
    }
}
