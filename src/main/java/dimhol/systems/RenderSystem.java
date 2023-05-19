package dimhol.systems;

import java.util.ArrayList;
import java.util.Map;

import dimhol.components.*;
import dimhol.core.World;
import dimhol.core.WorldImpl;
import dimhol.entity.Entity;

public class RenderSystem extends AbstractSystem{
    private final World world;

    public RenderSystem(WorldImpl w) {
        super(w, AnimationComponent.class);
        this.world = w;
    }

    private int getNumToUse(final String state, final Map<String,ArrayList<Integer>> map){
        return map.entrySet().stream()
        .filter(e -> e.getKey().equals(state))
        .map(Map.Entry::getValue)
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
           var healthComp = (HealthComponent) e.getComponent(HealthComponent.class);
           var coinPocket = (CoinPocketComponent) e.getComponent(CoinPocketComponent.class);
           this.world.getScene().getHUD().updatePlayerHUD(healthComp.getCurrentHealth(), healthComp.getMaxHealth(), coinPocket.getCurrentAmount());
       } else if (e.hasComponent(AiComponent.class)) {
           var healthComp = (HealthComponent) e.getComponent(HealthComponent.class);
           this.world.getScene().getHUD().updateEnemiesHUD(healthComp.getCurrentHealth(), healthComp.getMaxHealth(), posComp.getPos(), bodyComp.getBodyShape());
       }
       this.world.getScene().toList(animationComp.getIndex(),getNumToUse(animationComp.getState(), 
            animationComp.getMap()), posComp.getPos().getX(), posComp.getPos().getY(), 
            bodyComp.getBodyShape().getBoundingWidth(), bodyComp.getBodyShape().getBoundingHeight() );
    }
    
}
