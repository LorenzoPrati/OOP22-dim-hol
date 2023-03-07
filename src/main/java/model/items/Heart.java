package model.items;

import model.AbstractGameObject;
import model.dynamic.Player;

public class Heart extends AbstractGameObject implements Item {

    @Override
    public void applyEffect(Player p) {
        p.getHealthComponent().increaseOf(1);
    }

    @Override
    public void update(long dt) {
    }
    
}
