package model.dynamic;

import java.lang.ModuleLayer.Controller;
import java.util.LinkedList;

public class Player extends AbstractDynamicObject {

    private HealthComponent healthComponent;
    private int coins;

    @Override
    public void update(long dt) {
        if(this.isMoving()) {
            this.moveBasedOnDirection(dt);
            if (this.isCollidingWithWalls()) {
                this.bounceBack();
            }
            for (var o : this.getRoom().getRoomObjects()) {
                if (this.getCollisionBox().overlaps(o.getCollisionBox())) {
                    switch (o.getType()) {
                        case HEARTH:
                            //hearth effect
                            this.getRoom().removeObject(o);
                            break;
                        case COIN:
                            //coin effect
                            this.getRoom().removeObject(o);
                            break;
                        case GATE:
                            //gate effect
                            this.getRoom().removeObject(o);
                            break;
                    }
                    //this.getRoom().removeObject(o);
                }
            }
            
        }
        
    }
    
}
