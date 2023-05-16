package dimhol.components;

import dimhol.logic.player.IdleState;
import dimhol.logic.player.State;

/**
 * Holds data about the player.
 */
public class PlayerComponent implements Component {

    private State state;

    private boolean blocked;

    public PlayerComponent() {
        this.state = new IdleState();
    }

    public State getState() {
        return this.state;
    }

    public void setState(final State state) {
        this.state = state;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }
}
