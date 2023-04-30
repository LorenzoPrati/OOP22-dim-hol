package it.unibo.dimhol.ai;

import it.unibo.dimhol.entity.Entity;

public abstract class AbstractAction implements Action {

    private Entity player;

    public void setPlayer(Entity player) {
        this.player = player;
    }

    public Entity getPlayer() {
        return player;
    }
}
