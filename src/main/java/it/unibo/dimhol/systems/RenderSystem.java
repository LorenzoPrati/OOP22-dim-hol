package it.unibo.dimhol.systems;

import it.unibo.dimhol.World;
import it.unibo.dimhol.components.AnimationComponent;
import it.unibo.dimhol.components.PositionComponent;
import it.unibo.dimhol.entity.Entity;

public class RenderSystem extends AbstractSystem{
    private final World world;

    public RenderSystem(World w) {
        super(w, AnimationComponent.class);
        this.world = w;
    }

    @Override
    public void process(Entity e) {
       var pos = (PositionComponent)e.getComponent(PositionComponent.class);
       var info = (AnimationComponent)e.getComponent(AnimationComponent.class);
       this.world.getScene().toList(info.getIndex(),info.getNumToUse(), pos.getPos().getX(), pos.getPos().getY());
    }
    
}
