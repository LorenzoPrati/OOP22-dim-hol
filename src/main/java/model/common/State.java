package model.common;

import model.GameObject;
import model.dynamic.player.Player;

/**
 * An enum to represent all states of a game object
 */
public enum State {
    WALK_UP,
    WALK_DOWN,
    WALK_LEFT,
    WALK_RIGHT,

    ATTACK_UP,
    ATTACK_DOWN,
    ATTACK_LEFT,
    ATTACK_RIGHT,

    IDLE;

}
