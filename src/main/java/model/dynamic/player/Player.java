package model.dynamic.player;

import model.dynamic.DynamicObject;

/**
 * An interface to model the Player.
 */
public interface Player extends DynamicObject { 

    int getCoins();

    Health getHealth();

    void takeDamage(int amount);

    int getFirePower();
    
}
