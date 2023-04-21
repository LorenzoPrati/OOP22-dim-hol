package it.unibo.dimhol.GameLevels;

/**
 * An interface for each different game room.
 */
public interface Room {
    /**
     * Type room: Normal Room
     */
    void normalRoom();

    /**
     * Type room: Shop Room
     */
    void ShopRoom();

    /**
     * Type room: Boss Room
     */
    void BossRoom();
}

