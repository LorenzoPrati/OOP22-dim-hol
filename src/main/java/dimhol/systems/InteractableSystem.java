package dimhol.systems;

import dimhol.components.CollisionComponent;
import dimhol.components.InteractableComponent;
import dimhol.components.InteractorComponent;
import dimhol.core.World;
import dimhol.entity.Entity;

public class InteractableSystem extends AbstractSystem {

    public InteractableSystem() {
        super(InteractableComponent.class, CollisionComponent.class); 
    }

    @Override
    public void process(final Entity entity, final double dt, final World world ) {
        var collisionComp = (CollisionComponent) entity.getComponent(CollisionComponent.class);
        var interactableComp = (InteractableComponent) entity.getComponent(InteractableComponent.class);
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
