package dimhol.logic.player;

import dimhol.core.Input;
import dimhol.entity.Entity;
import dimhol.events.Event;
import dimhol.components.MovementComponent;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class FireballState extends AbstractPlayerState{

    public FireballState() {
        super("shoot");
    }

    @Override
    public Optional<PlayerState> transition(Input input) {
        return Optional.of(new IdleState());
    }

    @Override
    public List<Event> execute(Input input, Entity e) {
        var mov = (MovementComponent) e.getComponent(MovementComponent.class);
//        return List.of(new AddEntityEvent(new GenericFactory().
//        createPlayerBullet(mov.getDir().getX(), mov.getDir().getY(), e)));
        return Collections.emptyList();
    }

    @Override
    public void updateTime(double dt) {

    }

    @Override
    public void exit(Entity e) {

    }
}
