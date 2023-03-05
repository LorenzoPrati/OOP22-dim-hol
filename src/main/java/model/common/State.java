package model.common;

/**
 * An enum to represent all states of a game object
 */
public enum State {
    MOVING_UP,
    MOVING_DOWN,
    MOVING_LEFT,
    MOVING_RIGHT,

    ATTACKING_UP,
    ATTACKING_DOWN,
    ATTACKING_LEFT,
    ATTACKING_RIGHT,

    IDLE,
    TAKING_DAMAGE;
}
