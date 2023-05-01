package it.unibo.dimhol.gameLevels;

/**
 * The interface provides a blueprint for spawning rewards in a game level's room.
 * Implementing classes should create items or bonuses for the player to collect during gameplay.
 */
public interface RewardSpawner {
    /**
     * Spawn rewards in the room.
     */
    void spawnRewards();
}
