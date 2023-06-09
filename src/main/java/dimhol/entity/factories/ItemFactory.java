package dimhol.entity.factories;

import dimhol.entity.Entity;
import dimhol.entity.EntityBuilder;
import java.util.List;
import java.util.function.BiFunction;
import dimhol.logic.collision.CircleBodyShape;
import org.locationtech.jts.math.Vector2D;
import dimhol.components.AnimationComponent;
import dimhol.components.BodyComponent;
import dimhol.components.CoinPocketComponent;
import dimhol.components.Component;
import dimhol.components.HealthComponent;
import dimhol.components.ItemComponent;
import dimhol.components.PositionComponent;

/**
 * A factory of items.
 */
public class ItemFactory extends BaseFactory {
    private static final double W = 0.5;
    private static final int INCREASE_CURRENT_HEALTH = 1;
    private static final int INCREASE_CURRENT_COINS = 1;

    private final BiFunction<Entity, List<Class<? extends Component>>, Boolean> increaseCurrentHealth = (e, c) -> {
        final var hasComponentNeeded = c.stream().anyMatch(comp -> e.hasComponent(comp));
        if (hasComponentNeeded) {
            final var healthComp = (HealthComponent) e.getComponent(HealthComponent.class);
            if (healthComp.getCurrentHealth() < healthComp.getMaxHealth()) {
                healthComp.setCurrentHealth(healthComp.getCurrentHealth() + INCREASE_CURRENT_HEALTH);
                return true;
            }
            return false;
        }
        return false;
    };

    private final BiFunction<Entity, List<Class<? extends Component>>, Boolean> increaseCurrentCoinsAmount = (e, c) -> {
        final var hasComponentNeeded = c.stream().anyMatch(comp -> e.hasComponent(comp));
        if (hasComponentNeeded) {
            final var coinPocketComp = (CoinPocketComponent) e.getComponent(CoinPocketComponent.class);
            coinPocketComp.setAmount(coinPocketComp.getCurrentAmount() + INCREASE_CURRENT_COINS);
            return true;
        }
        return false;
    };

    /**
     * creates a heart item.
     * @param x
     * @param y
     * @return a heart item.
     */
    public Entity createHeart(final double x, final double y) {
        return new EntityBuilder()
        .add(new PositionComponent(new Vector2D(x, y), 0))
        .add(new BodyComponent(new CircleBodyShape(W / 2), false))
        .add(new ItemComponent(increaseCurrentHealth))
        .add(new AnimationComponent(getAnimationsMap().get("heart"), "idle"))
        .build();
    }

    /**
     * creates a coin item.
     * @param x
     * @param y
     * @return a coin item.
     */
    public Entity createCoin(final double x, final double y) {
        return new EntityBuilder()
        .add(new PositionComponent(new Vector2D(x, y), 0))
        .add(new BodyComponent(new CircleBodyShape(W / 2), false))
        .add(new ItemComponent(increaseCurrentCoinsAmount))
        .add(new AnimationComponent(getAnimationsMap().get("coin"), "idle"))
        .build();
    }
}
