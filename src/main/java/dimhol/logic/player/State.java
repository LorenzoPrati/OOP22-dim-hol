package dimhol.logic.player;

import dimhol.core.Input;
import dimhol.entity.Entity;

import java.util.Optional;

public interface State {

    void entry(Entity e);

    void update(double dt, Entity entity);

    boolean canTransition();

    Optional<State> transition(Input input);

    void execute(Input input);

    void exit();

    void updateAnimation();

}
