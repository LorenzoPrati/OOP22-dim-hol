package dimhol.entity.factories;

import dimhol.components.AnimationComponent;
import dimhol.components.BodyComponent;
import dimhol.components.PickableComponent;
import dimhol.components.PositionComponent;
import dimhol.entity.Entity;
import dimhol.entity.EntityBuilder;
import dimhol.logic.effects.IncreaseCoinAmountEffect;
import dimhol.logic.effects.IncreaseCurrentHealthEffect;
import dimhol.logic.effects.IncreaseKeyAmountEffect;
import org.locationtech.jts.math.Vector2D;
import dimhol.logic.collision.RectBodyShape;

public class ItemFactory extends AbstractFactory {
    private static final double W = 3;
    private static final double H = 3;
    private static final int CHEST_INCREASE = 30;

    public ItemFactory(){
        super();
    }

    public Entity createHeart(final double x, final double y){
        return new EntityBuilder()
        .add(new PositionComponent(new Vector2D(x, y), 1))
        .add(new BodyComponent(new RectBodyShape(W,H), false))
        .add(new PickableComponent(new IncreaseCurrentHealthEffect(1)))
        .add(new AnimationComponent(this.map.get("heart"), "idle"))
        .build();
    }

    public Entity createCoin(final double x, final double y){
        return new EntityBuilder()
        .add(new PositionComponent(new Vector2D(x,y), 1))
        .add(new BodyComponent(new RectBodyShape(W,H), false))
        .add(new PickableComponent(new IncreaseCoinAmountEffect(1)))
        .add(new AnimationComponent(this.map.get("coin"), "idle"))
        .build();
    }

    public Entity createKey(final double x, final double y){
        return new EntityBuilder()
        .add(new PositionComponent(new Vector2D(x,y), 1))
        .add(new BodyComponent(new RectBodyShape(W,H), false))
        .add(new PickableComponent(new IncreaseKeyAmountEffect(1)))
        .add(new AnimationComponent(this.map.get("key"), "idle"))
        .build();
    }

    public Entity createChest(final double x, final double y){
        return new EntityBuilder()
        .add(new PositionComponent(new Vector2D(x,y), 1))
        .add(new BodyComponent(new RectBodyShape(W,H), false))
        .add(new PickableComponent(new IncreaseCoinAmountEffect(CHEST_INCREASE)))
        .add(new AnimationComponent(this.map.get("chest"), "idle"))
        .build();
    }
    
}
