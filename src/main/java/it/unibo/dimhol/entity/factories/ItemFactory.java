package it.unibo.dimhol.entity.factories;

import it.unibo.dimhol.entity.Entity;
import it.unibo.dimhol.entity.EntityBuilder;
import org.locationtech.jts.math.Vector2D;
import it.unibo.dimhol.components.*;
import it.unibo.dimhol.logic.collision.RectBodyShape;
import it.unibo.dimhol.logic.effects.*;

public class ItemFactory extends AbstractFactory {
    private static final double W = 3;
    private static final double H = 3;
    private CoinPocketEffectFactory coinEffectFactory = new CoinPocketEffectFactoryImpl();
    private HealthEffectFactory healthEffectFactory = new HealthEffectsFactoryImpl();

    public ItemFactory(){
        super();
    }

    public Entity createHeart(final double x, final double y){
        return new EntityBuilder()
        .add(new PositionComponent(new Vector2D(x, y), 1))
        .add(new BodyComponent(new RectBodyShape(W,H), false))
        .add(new PickableComponent(healthEffectFactory.IncreasePlayerCurrentHealthEffect(1)))
        .add(new AnimationComponent(this.map.get("heart"), "idle"))
        .build();
    }

    public Entity createCoin(final double x, final double y){
        return new EntityBuilder()
        .add(new PositionComponent(new Vector2D(x,y), 1))
        .add(new BodyComponent(new RectBodyShape(W,H), false))
        .add(new PickableComponent(coinEffectFactory.IncreaseCoinPocketEffect(1)))
        .add(new AnimationComponent(this.map.get("coin"), "idle"))
        .build();
    }
    
}
