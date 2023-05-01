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
     * Place the player entity at the specified position.
     */
    void placePlayer();
}