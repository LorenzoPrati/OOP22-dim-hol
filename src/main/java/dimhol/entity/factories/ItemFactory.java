package dimhol.entity.factories;

import dimhol.components.*;
import dimhol.core.World;
import dimhol.entity.Entity;
import dimhol.entity.EntityBuilder;
import org.locationtech.jts.math.Vector2D;
import dimhol.logic.collision.RectBodyShape;
import dimhol.logic.effects.*;

public class ItemFactory extends AbstractFactory {
    private static final double W = 1;
    private static final double H = 1;
    private CoinPocketEffectFactory coinEffectFactory = new CoinPocketEffectFactoryImpl();
    private HealthEffectFactory healthEffectFactory = new HealthEffectsFactoryImpl();
    protected World world;

    public ItemFactory(World w){
        super();
        this.world = w;
    }

    public Entity createHeart(final double x, final double y){
        return new EntityBuilder()
        .add(new PositionComponent(new Vector2D(x, y), 1))
        .add(new BodyComponent(new RectBodyShape(W,H), false))
        .add(new InteractableComponent(healthEffectFactory.increasePlayerCurrentHealthEffect(1),true, null))
        .add(new AnimationComponent(this.map.get("heart"), "idle"))
        .build();
    }

    public Entity createCoin(final double x, final double y){
        return new EntityBuilder()
        .add(new PositionComponent(new Vector2D(x,y), 1))
        .add(new BodyComponent(new RectBodyShape(W,H), false))
        .add(new InteractableComponent(coinEffectFactory.increaseCoinPocketEffect(1), false, null))
        .add(new AnimationComponent(this.map.get("coin"), "idle"))
        .build();
    }

    public Entity createGate(final double x,final double y){
        return new EntityBuilder()
        .add(new PositionComponent(new Vector2D(x,y), 1))
        .add(new BodyComponent(new RectBodyShape(W, H), false))
        .add(new InteractableComponent(new GateEffect(this.world), false, null)) // TO DO add gate effect
        //.add(new AnimationComponent(this.map.get("gate"), "idle")) //TO DO add sprites 
        .build();
    }
    
}
