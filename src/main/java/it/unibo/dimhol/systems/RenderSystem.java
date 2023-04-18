package it.unibo.dimhol.systems;

import it.unibo.dimhol.World;
import it.unibo.dimhol.components.Component;
import it.unibo.dimhol.components.InfoAnimationComponent;
import it.unibo.dimhol.components.PositionComponent;
import it.unibo.dimhol.components.VisualDebugComponent;
import it.unibo.dimhol.entity.Entity;

public class RenderSystem extends AbstractSystem{
    private final World world;

    public RenderSystem(World w) {
        super(w, VisualDebugComponent.class);
        this.world = w;
    }

    @Override
    public void process(Entity e, long dt) {
       var pos = (PositionComponent)e.getComponent(PositionComponent.class);
       var info = (InfoAnimationComponent)e.getComponent(InfoAnimationComponent.class);
       this.world.getScene().toList(info.getType(), info.getState(), info.getIndex(), pos.getPos().getX(), pos.getPos().getY());
    }
    
}
