package dimhol.entity.factories;

import dimhol.components.*;
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

    public ItemFactory(){
        super();
    }

    public Entity createHeart(final double x, final double y){
        return new EntityBuilder()
        .add(new PositionComponent(new Vector2D(x, y), 1))
        .add(new BodyComponent(new RectBodyShape(W,H), false))
        .add(new InteractableComponent(healthEffectFactory.increasePlayerCurrentHealthEffect(1),true))
        .add(new AnimationComponent(this.map.get("heart"), "idle"))
        .build();
    }

    public Entity createCoin(final double x, final double y){
        return new EntityBuilder()
        .add(new PositionComponent(new Vector2D(x,y), 1))
        .add(new BodyComponent(new RectBodyShape(W,H), false))
        .add(new InteractableComponent(coinEffectFactory.increaseCoinPocketEffect(1), false))
        .add(new AnimationComponent(this.map.get("coin"), "idle"))
        .build();
    }
    
}
