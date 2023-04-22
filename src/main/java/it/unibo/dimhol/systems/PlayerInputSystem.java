package it.unibo.dimhol.systems;

import it.unibo.dimhol.World;
import it.unibo.dimhol.components.AnimationComponent;
import it.unibo.dimhol.components.Component;
import it.unibo.dimhol.components.MovementComponent;
import it.unibo.dimhol.components.PlayerComponent;
import it.unibo.dimhol.entity.Entity;
import org.locationtech.jts.math.Vector2D;

/**
 * A system to handle player input.
 */
public class PlayerInputSystem extends AbstractSystem {

    public PlayerInputSystem(World w) {
        super(w, PlayerComponent.class);
    }

    @Override
    public void process(Entity e, long dt) {
        var mov = (MovementComponent) e.getComponent(MovementComponent.class);
        var an = (AnimationComponent) e.getComponent(AnimationComponent.class);
        var input = this.world.getInput();
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
