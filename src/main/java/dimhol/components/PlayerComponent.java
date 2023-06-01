package dimhol.components;

import dimhol.logic.player.states.IdleState;
import dimhol.logic.player.PlayerState;

/**
 * Holds data about the player.
 */
public class PlayerComponent implements Component {

    /**
     * The current state of the player.
     */
    private PlayerState state;

    /**
     * Constructs a player component. By default sets the state to idle.
     */
    public PlayerComponent() {
        this.state = new IdleState();
    }

    /**
     * Gets the current state.
     *
     * @return the state of the player
     */
    public PlayerState getState() {
        return this.state;
    }

    /**
     * Sets the player state.
     *
     * @param state the state to set.
     */
    public void setState(final PlayerState state) {
        this.state = state;
    }
}
