package dimhol.systems;

import dimhol.components.CollisionComponent;
import dimhol.components.InteractableComponent;
import dimhol.components.InteractorComponent;
import dimhol.core.WorldImpl;
import dimhol.entity.Entity;

public class InteractableSystem extends AbstractSystem {

    public InteractableSystem(WorldImpl w) {
        super(w, InteractableComponent.class, CollisionComponent.class); 
    }

    @Override
    public void process(Entity e, double dt) {
        var collisionComp = (CollisionComponent) e.getComponent(CollisionComponent.class);
        var interactableComp = (InteractableComponent) e.getComponent(InteractableComponent.class);
        for(var c: collisionComp.getCollided()){
            if(c.hasComponent(InteractorComponent.class)){ //TO DO modify with player component
                var interactComp = (InteractorComponent)c.getComponent(InteractorComponent.class);
                if(interactComp.isInteracting()){
                    interactableComp.applyEffect(c,world);
                }
            }
        }
    } 
}
