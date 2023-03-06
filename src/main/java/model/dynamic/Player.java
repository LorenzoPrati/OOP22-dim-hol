package model.dynamic;

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
            //check other collisions
            
        }
    }
    
}
