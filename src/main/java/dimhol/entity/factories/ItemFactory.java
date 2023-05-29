package dimhol.entity.factories;

import dimhol.components.*;
import dimhol.entity.Entity;
import dimhol.entity.EntityBuilder;
import java.util.function.BiFunction;
import org.locationtech.jts.math.Vector2D;
import dimhol.logic.collision.RectBodyShape;
import dimhol.components.ItemComponent;

public class ItemFactory extends AbstractFactory {
    private static final double W = 1;
    private static final double H = 1;
    public static final int INCREASE_CURRENT_HEALTH = 1;
    private static final int INCREASE_CURRENT_COINS = 1;

    public ItemFactory() {
        super();
    }

    BiFunction<Entity, Class<? extends Component>, Boolean>increaseCurrentHealth = (e, c) -> {
        if(e.hasComponent(c)) {
            var healthComp = (HealthComponent)e.getComponent(HealthComponent.class);
            if(healthComp.getCurrentHealth() < healthComp.getMaxHealth()) {
                healthComp.setCurrentHealth(healthComp.getCurrentHealth() + INCREASE_CURRENT_HEALTH);
                return true;
            }
            return false;
        }
        return false;
    };

    BiFunction<Entity, Class<? extends Component>, Boolean>increaseCurrentCoinsAmount = (e, c) -> {
        if(e.hasComponent(c)) {
            var coinPocketComp = (CoinPocketComponent)e.getComponent(CoinPocketComponent.class);
            coinPocketComp.setAmount(coinPocketComp.getCurrentAmount() + INCREASE_CURRENT_COINS);
            return true;
        }
        return false;
    };

    public Entity createHeart(final double x, final double y) {
        return new EntityBuilder()
        .add(new PositionComponent(new Vector2D(x, y), 0))
        .add(new BodyComponent(new RectBodyShape(W,H), false))
        .add(new ItemComponent(increaseCurrentHealth))
        .add(new AnimationComponent(this.map.get("heart"), "idle"))
        .build();
    }

    public Entity createCoin(final double x, final double y) {
        return new EntityBuilder()
        .add(new PositionComponent(new Vector2D(x,y), 0))
        .add(new BodyComponent(new RectBodyShape(W,H), false))
        .add(new ItemComponent(increaseCurrentCoinsAmount))
        .add(new AnimationComponent(this.map.get("coin"), "idle"))
        .build();
    }
}
