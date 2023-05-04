package it.unibo.dimhol.entity;

import org.locationtech.jts.math.Vector2D;
import it.unibo.dimhol.components.*;
import it.unibo.dimhol.logic.collision.RectBodyShape;
import it.unibo.dimhol.logic.effects.*;

public class ItemFactory extends AbstractFactory{
    private static final double W = 3;
    private static final double H = 3;
    private static final int CHEST_INCREASE = 30;

    public ItemFactory(){
        super();
    }

    public Entity createHeart(final double x, final double y){
        return new EntityBuilder()
        .add(new PositionComponent(new Vector2D(x, y), 0))
        .add(new BodyComponent(new RectBodyShape(W,H), false))
        .add(new PickableComponent(new IncreaseCurrentHealthEffect(1)))
        .add(new AnimationComponent(this.map.get("heart"), "idle"))
        .build();
    }

    public Entity createCoin(final double x, final double y){
        return new EntityBuilder()
        .add(new PositionComponent(new Vector2D(x,y), 0))
        .add(new BodyComponent(new RectBodyShape(W,H), false))
        .add(new PickableComponent(new IncreaseCoinAmountEffect(1)))
        .add(new AnimationComponent(this.map.get("coin"), "idle"))
        .build();
    }

    public Entity createKey(final double x, final double y){
        return new EntityBuilder()
        .add(new PositionComponent(new Vector2D(x,y), 0))
        .add(new BodyComponent(new RectBodyShape(W,H), false))
        .add(new PickableComponent(new IncreaseKeyAmountEffect(1)))
        .add(new AnimationComponent(this.map.get("key"), "idle"))
        .build();
    }

    public Entity createChest(final double x, final double y){
        return new EntityBuilder()
        .add(new PositionComponent(new Vector2D(x,y), 0))
        .add(new BodyComponent(new RectBodyShape(W,H), false))
        .add(new PickableComponent(new IncreaseCoinAmountEffect(CHEST_INCREASE)))
        .add(new AnimationComponent(this.map.get("chest"), "idle"))
        .build();
    }
    
}
