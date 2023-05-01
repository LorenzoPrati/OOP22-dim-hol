package it.unibo.dimhol.systems;

import java.util.ArrayList;
import java.util.Map;

import it.unibo.dimhol.World;
import it.unibo.dimhol.components.*;
import it.unibo.dimhol.entity.Entity;

public class RenderSystem extends AbstractSystem{
    private final World world;

    public RenderSystem(World w) {
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
       if (e.hasComponent(PlayerComponent.class)) {
           var health = (HealthComponent)e.getComponent(HealthComponent.class);
           this.world.getScene().getPlayerHUD().update(health.getCurrentHealth(), health.getMaxHealth());
       }
       this.world.getScene().toList(animationComp.getIndex(),getNumToUse(animationComp.getState(),
                       animationComp.getMap()), posComp.getPos().getX(), posComp.getPos().getY(),
               bodyComp.getBodyShape().getBoundingWidth(), bodyComp.getBodyShape().getBoundingHeight() );

    }
    
}
