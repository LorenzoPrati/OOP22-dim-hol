package model.items;

import model.dynamic.Player;

/**
 * interface to model items
 */
public interface Item {

    /**
     * method that apply the item effect on the player
     * @param p
     */
    void applyEffect(Player p);
    
}
