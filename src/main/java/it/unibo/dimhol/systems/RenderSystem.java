package it.unibo.dimhol.systems;

import java.util.ArrayList;
import java.util.Map;

import it.unibo.dimhol.core.World;
import it.unibo.dimhol.core.WorldImpl;
import it.unibo.dimhol.components.AnimationComponent;
import it.unibo.dimhol.components.BodyComponent;
import it.unibo.dimhol.components.PositionComponent;
import it.unibo.dimhol.entity.Entity;

public class RenderSystem extends AbstractSystem{
    private final World world;

    public RenderSystem(WorldImpl w) {
        super(w, AnimationComponent.class);
        this.world = w;
    }

    private int getNumToUse(final String state, final Map<String,ArrayList<Integer>> map){
        return map.entrySet().stream()
        .filter(e -> e.getKey().equals(state))
        .map(e -> e.getValue())
        .findAny()
        .get()
        .get(1);
    }

    @Override
    public void process(Entity e, double dt) {
       var posComp = (PositionComponent)e.getComponent(PositionComponent.class);
       var animationComp = (AnimationComponent)e.getComponent(AnimationComponent.class);
       var bodyComp = (BodyComponent)e.getComponent(BodyComponent.class);
       this.world.getScene().toList(animationComp.getIndex(),getNumToUse(animationComp.getState(), 
        animationComp.getMap()), posComp.getPos().getX(), posComp.getPos().getY(), 
        bodyComp.getBodyShape().getBoundingWidth(), bodyComp.getBodyShape().getBoundingHeight() );
    }
    
}
