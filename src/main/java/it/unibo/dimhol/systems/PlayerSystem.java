package it.unibo.dimhol.systems;

import it.unibo.dimhol.World;
import it.unibo.dimhol.commons.DirectionUtil;
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
     * @param e  the entity to process
     * @param dt
     */
    @Override
    public void process(final Entity e, double dt) {
        var input = this.world.getInputListener();
        var mov = (MovementComponent) e.getComponent(MovementComponent.class);
        var animation = (AnimationComponent) e.getComponent(AnimationComponent.class);
        var player = (PlayerComponent) e.getComponent(PlayerComponent.class);

        /*
            Block input reaction if player is blocked executing an action
         */
//        if (animation.isBlocking() && !animation.isCompleted()) {
//            animation.setState(animation.getState());
//            mov.setEnabled(false);
//            return;
//        }

        if (input.isNormalAttack()) {
            mov.setEnabled(false);
            animation.setState("normal" + " " + DirectionUtil.getStringFromVec(mov.getDir()));
            //animation.setCompleted(false);
            /*
            spawn normal attack
             */
        } else if (input.isShooting()) {
            mov.setEnabled(false);
            animation.setState("shoot" + " " + DirectionUtil.getStringFromVec(mov.getDir()));
            //animation.setCompleted(false)
            /*
             * spawn bullet
             */
        } else if (input.isSpecialAttack()) {
            mov.setEnabled(false);
            animation.setState("special" + " " + DirectionUtil.getStringFromVec(mov.getDir()));
            //animation.setCompleted(false)
            /*
             * spawn special attack
             */
        } else {
            mov.setEnabled(input.isMoving());
            if (input.isUp()) {
                mov.setDir(new Vector2D(0, -1));
            } else if (input.isDown()) {
                mov.setDir(new Vector2D(0, 1));
            } else if (input.isLeft()) {
                mov.setDir(new Vector2D(-1, 0));
            } else if (input.isRight()) {
                mov.setDir(new Vector2D(1, 0));
            }
            animation.setState((input.isMoving() ? "walk" : "idle")
                    + " " + DirectionUtil.getStringFromVec(mov.getDir()));
        }
    }
}
