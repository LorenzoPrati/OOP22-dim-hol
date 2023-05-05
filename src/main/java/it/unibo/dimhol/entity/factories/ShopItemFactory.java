package it.unibo.dimhol.entity.factories;

import it.unibo.dimhol.entity.Entity;
import it.unibo.dimhol.entity.EntityBuilder;
import org.locationtech.jts.math.Vector2D;
import it.unibo.dimhol.components.*;
import it.unibo.dimhol.logic.collision.RectBodyShape;
import it.unibo.dimhol.logic.effects.*;

public class ShopItemFactory extends AbstractFactory {
    private static final double W = 3;
    private static final double H = 3;
    private static final int MAX_HEALTH_INCREASE = 10;
    private static final double VELOCITY_INCREASE = 0.5;
    private HealthEffectFactory healthEffectFactory = new HealthEffectsFactoryImpl();
    private SpeedEffectFactory speedEffectFactory = new SpeedEffectFactoryImpl();

    public ShopItemFactory(){
        super();
    }
    
    public Entity createShopHeart(final double x, final double y){
        return new EntityBuilder()
        .add(new PositionComponent(new Vector2D(x,y), 1))
        .add(new BodyComponent(new RectBodyShape(W,H), true))
        .add(new InteractableComponent(healthEffectFactory.IncreasePlayerMaxHealthEffect(MAX_HEALTH_INCREASE)))
        .add(new AnimationComponent(this.map.get("shopHeart"), "idle"))
        .build();
    }

    public Entity createShopVelocity(final double x, final double y){
        return new EntityBuilder()
        .add(new PositionComponent(new Vector2D(x,y), 1))
        .add(new BodyComponent(new RectBodyShape(W,H), true))
        .add(new InteractableComponent(speedEffectFactory.increaseSpeedEffect(VELOCITY_INCREASE)))
        .add(new AnimationComponent(this.map.get("shopSpeed"), "idle"))
        .build();
    }
}
