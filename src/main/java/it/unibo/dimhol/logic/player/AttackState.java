package it.unibo.dimhol.logic.player;

import it.unibo.dimhol.logic.util.DirectionUtil;
import it.unibo.dimhol.components.MovementComponent;
import it.unibo.dimhol.entity.Entity;
import it.unibo.dimhol.entity.GenericFactory;
import it.unibo.dimhol.events.AddEntityEvent;
import it.unibo.dimhol.events.Event;
import it.unibo.dimhol.view.InputListener;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class AttackState extends AbstractPlayerState {

    public AttackState() {
    }

    @Override
    public Optional<PlayerState> transition(InputListener input) {
        return input.isAttacking() ? Optional.empty() : Optional.of(new IdleState());
    }

    @Override
    public List<Event> update(InputListener input, Entity e, double dt) {
        var mov = (MovementComponent) e.getComponent(MovementComponent.class);
        mov.setEnabled(false);
        if (input.isSpecialAttack()) {
            this.setDesc("special " + DirectionUtil.getStringFromVec(mov.getDir()));
            return List.of(new AddEntityEvent(new GenericFactory()
                    .createPlayerMeleeAttack(mov.getDir().getX(), mov.getDir().getY(), e)));
        } else if (input.isNormalAttack()) {
            this.setDesc("normal " + DirectionUtil.getStringFromVec(mov.getDir()));
            return List.of(new AddEntityEvent(new GenericFactory()
                    .createPlayerMeleeAttack(mov.getDir().getX(), mov.getDir().getY(), e)));
        }
        return Collections.emptyList();
    }
}
