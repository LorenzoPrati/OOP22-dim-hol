package it.unibo.dimhol.gameLevels;

/**
 * The RoomStrategy interface is used to define the behavior of each different game room.
 */
public interface RoomStrategy {
    /**
     * Generate entities for the game room.
     * This method should be implemented to create the enemies, the player, game objects
     * and position them into the room.
     */
    void generate();

    /**
     * Spawn rewards in the room.
     * This method should be implemented to create items or bonuses for the player to collect during the gameplay.
     */
    void spawnRewards();
}

