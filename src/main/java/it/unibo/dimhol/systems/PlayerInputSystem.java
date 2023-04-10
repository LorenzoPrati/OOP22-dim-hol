package it.unibo.dimhol.systems;

import it.unibo.dimhol.World;
import it.unibo.dimhol.components.Component;
import it.unibo.dimhol.components.MovementComponent;
import it.unibo.dimhol.entity.Entity;
import org.locationtech.jts.math.Vector2D;

/**
 * A system to handle player input.
 */
public class PlayerInputSystem extends AbstractSystem {

    public PlayerInputSystem(World w, Class<? extends Component>... comps) {
        super(w, comps);
    }

    @Override
    public void process(Entity e, long dt) {
        var mov = (MovementComponent) e.getComponent(MovementComponent.class);
        mov.setEnabled(false);
        mov.setDir(new Vector2D(0,0));
        var input = this.world.getInput();
        if (input.anyMoveKeyPressed()) {
            mov.setEnabled(true);
            if (input.isUp()) {
                mov.setDir(new Vector2D(0,-1));
            } else if (input.isDown()) {
                mov.setDir(new Vector2D(0,1));
            } else if (input.isLeft()) {
                mov.setDir(new Vector2D(-1,0));
            } else if (input.isRight()) {
                mov.setDir(new Vector2D(1,0));
            }
        }
    }
}
