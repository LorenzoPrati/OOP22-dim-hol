package it.unibo.dimhol.entity;

import org.locationtech.jts.math.Vector2D;
import it.unibo.dimhol.components.*;
import it.unibo.dimhol.logic.collision.RectBodyShape;
import it.unibo.dimhol.logic.effects.*;

public class ShopItemFactory extends AbstractFactory{
    private static final double W = 3;
    private static final double H = 3;
    private static final int MAX_HEALTH_INCREASE = 10;
    private static final double VELOCITY_INCREASE = 0.5;

    public ShopItemFactory(){
        super();
    }
    
    public Entity createShopHeart(final double x, final double y){
        return new EntityBuilder()
        .add(new PositionComponent(new Vector2D(x,y), 1))
        .add(new BodyComponent(new RectBodyShape(W,H), true))
        .add(new InteractableComponent(new IncreaseMaxHealthEffect(MAX_HEALTH_INCREASE)))
        .add(new AnimationComponent(this.map.get("shopHeart"), "idle"))
        .build();
    }

    public Entity createShopVelocity(final double x, final double y){
        return new EntityBuilder()
        .add(new PositionComponent(new Vector2D(x,y), 1))
        .add(new BodyComponent(new RectBodyShape(W,H), true))
        .add(new InteractableComponent(new IncreaseSpeedEffect(VELOCITY_INCREASE)))
        .add(new AnimationComponent(this.map.get("shopSpeed"), "idle"))
        .build();
    }

    /*public Entity createShopDamage(final double x, final double y){
        return new EntityBuilder()
        .add(new PositionComponent(new Vector2D(x,y), 0))
        .add(new BodyComponent(new RectBodyShape(W,H), true))
        .add(new InteractableComponent(new IncreaseCoinAmountEffect(1)))
        .add(new AnimationComponent(this.map.get("shopDamage"), "idle"))
        .build();
    }*/
}
