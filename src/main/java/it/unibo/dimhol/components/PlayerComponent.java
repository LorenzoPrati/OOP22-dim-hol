package it.unibo.dimhol.components;

import it.unibo.dimhol.logic.player.IdleState;
import it.unibo.dimhol.logic.player.PlayerState;

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

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }
}
