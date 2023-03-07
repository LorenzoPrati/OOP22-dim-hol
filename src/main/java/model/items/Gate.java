package model.items;

import model.AbstractGameObject;
import model.dynamic.Player;

public class Gate extends AbstractGameObject implements Item{

    @Override
    public void applyEffect(Player p) {
        p.getRoom().setCleared();
    }

    @Override
    public void update(long dt) {
    }
    
}
