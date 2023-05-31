package dimhol.components;

import dimhol.logic.player.states.IdleState;
import dimhol.logic.player.PlayerState;

/**
 * Holds data about the player.
 */
public class PlayerComponent implements Component {

    private PlayerState state;

    private boolean blocked;

    public PlayerComponent() {
        this.state = new IdleState();
    }

    public PlayerState getState() {
        return this.state;
    }

    public void setState(final PlayerState state) {
        this.state = state;
    }
}
