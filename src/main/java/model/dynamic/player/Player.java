package model.dynamic.player;

/**
 * An interface to model the Player
 */
public interface Player { 

    int getCoins();

    Health getHealth();

    void takeDamage(int amount);

    int getFirePower();
    
}
