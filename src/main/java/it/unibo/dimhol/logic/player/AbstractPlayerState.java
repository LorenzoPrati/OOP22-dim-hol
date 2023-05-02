package it.unibo.dimhol.logic.player;

import it.unibo.dimhol.entity.Entity;
import it.unibo.dimhol.events.Event;
import it.unibo.dimhol.view.InputListener;

import java.util.List;
import java.util.Optional;

public abstract class AbstractPlayerState implements PlayerState{

    private String desc;

    public AbstractPlayerState() {
    }

    @Override
    public String getDesc() {
        return this.desc;
    }

    protected void setDesc(String desc) {
        this.desc = desc;
    }
}
