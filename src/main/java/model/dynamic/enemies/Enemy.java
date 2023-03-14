package model.dynamic.enemies;

import model.dynamic.DynamicObject;
import model.dynamic.player.Player;

/**
 * Model the enemies
 */
public interface Enemy extends DynamicObject {

    /**
     * 
     * @param damage that the enemy suffers.
     */
    void takeDamage(int damage);

    /**
     * 
     * @return the fire power.
     */
    int getFirePower();

    /**
     * 
     * @return the player.
     */
    Player getPlayer(); 

}
