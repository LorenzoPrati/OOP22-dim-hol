package it.unibo.dimhol.logic.player;

import it.unibo.dimhol.core.Input;
import it.unibo.dimhol.logic.util.DirectionUtil;
import it.unibo.dimhol.components.MovementComponent;
import it.unibo.dimhol.entity.Entity;
import it.unibo.dimhol.entity.GenericFactory;
import it.unibo.dimhol.events.AddEntityEvent;
import it.unibo.dimhol.events.Event;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class CombatState extends AbstractPlayerState {

    public CombatState() {
    }

    @Override
    public Optional<PlayerState> transition(Input input) {
        return input.isMeele() ? Optional.empty() : Optional.of(new IdleState());
    }

    @Override
    public List<Event> execute(Input input, Entity e) {
        var mov = (MovementComponent) e.getComponent(MovementComponent.class);
        mov.setEnabled(false);
        if (input.isShooting()) {
            this.setDesc("shoot " + DirectionUtil.getStringFromVec(mov.getDir()));
            return List.of(new AddEntityEvent(new GenericFactory()
                    .createPlayerBullet(mov.getDir().getX(), mov.getDir().getY(), e)));
        }
        else if (input.isSpecialMeele()) {
            this.setDesc("special " + DirectionUtil.getStringFromVec(mov.getDir()));
            return List.of(new AddEntityEvent(new GenericFactory()
                    .createPlayerMeleeAttack(mov.getDir().getX(), mov.getDir().getY(), e)));
        } else if (input.isNormalMeele()) {
            this.setDesc("normal " + DirectionUtil.getStringFromVec(mov.getDir()));
            return List.of(new AddEntityEvent(new GenericFactory()
                    .createPlayerMeleeAttack(mov.getDir().getX(), mov.getDir().getY(), e)));
        }
        return Collections.emptyList();
    }

    @Override
    public void updateTime(double dt) {

    }
}
