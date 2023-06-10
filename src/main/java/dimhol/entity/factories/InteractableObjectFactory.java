package dimhol.entity.factories;

import dimhol.components.CoinPocketComponent;
import dimhol.components.HealthComponent;
import dimhol.components.InteractableComponent;
import dimhol.components.MovementComponent;
import dimhol.components.PositionComponent;
import dimhol.components.ShopKeeperComponent;
import dimhol.components.AIComponent;
import dimhol.components.AnimationComponent;
import dimhol.components.BodyComponent;
import dimhol.core.World;
import dimhol.entity.Entity;
import dimhol.entity.EntityBuilder;
import dimhol.events.ChangeLevelEvent;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import org.locationtech.jts.math.Vector2D;
import dimhol.logic.collision.RectBodyShape;

/**
 * A factory of intractable objects.
 */
public class InteractableObjectFactory extends BaseFactory {
    private static final double W_POWERUP = 1;
    private static final double H_POWERUP = 1;
    private static final double W_GATE = 1;
    private static final double H_GATE = 1;
    private static final int MAX_HEALTH_INCREASE = 10;
    private static final double VELOCITY_INCREASE = 0.8;
    private static final int MAX_HEALTH_PRICE = 10;
    private static final int VELOCITY_PRICE = 15;
    private final BiPredicate<Entity, Integer> checkCoins;
    private final BiConsumer<Entity, Integer> payPrice;
    private final BiFunction<Entity, World, Boolean> powerUpMaxHealth;
    private final BiFunction<Entity, World, Boolean> powerUpSpeed;
    private final BiFunction<Entity, World, Boolean> useGate;

    /**
     * Creates an InteractableObjectFactory.
     */
    public InteractableObjectFactory() {
        super();
        this.checkCoins = (e, i) -> {
            final var currentCoins = (CoinPocketComponent) e.getComponent(CoinPocketComponent.class);
            return currentCoins.getCurrentAmount() >= i;
        };
        this.payPrice = (e, p) -> {
            final var coinPocket = (CoinPocketComponent) e.getComponent(CoinPocketComponent.class);
            coinPocket.setAmount(coinPocket.getCurrentAmount() - p);
        };
        this.powerUpMaxHealth = (e, w) -> {
            if (checkCoins.test(e, MAX_HEALTH_PRICE)) {
                payPrice.accept(e, MAX_HEALTH_PRICE);
                final var healthComp = (HealthComponent) e.getComponent(HealthComponent.class);
                healthComp.setMaxHealth(healthComp.getMaxHealth() + MAX_HEALTH_INCREASE);
                return true;
            }
            return false;
        };
        this.powerUpSpeed = (e, w) -> {
            if (checkCoins.test(e, VELOCITY_PRICE)) {
                payPrice.accept(e, VELOCITY_PRICE);
                final var moveComp = (MovementComponent) e.getComponent(MovementComponent.class);
                moveComp.setSpeed(moveComp.getSpeed() + VELOCITY_INCREASE);
                return true;
            } 
            return false;
        };
        this.useGate = (e, w) -> {
            if (w.getEntities().stream().noneMatch(entity -> entity.hasComponent(AIComponent.class)) 
                || w.getEntities().stream().anyMatch(entity -> entity.hasComponent(ShopKeeperComponent.class))) {
                w.notifyEvent(new ChangeLevelEvent());
                return true;
            }
            return false;
        };
    }

    /**
     * Creates a health power up.
     * @param x
     * @param y
     * @return a health power up entity.
     */
    public Entity createShopHeart(final double x, final double y) {
        return new EntityBuilder()
        .add(new PositionComponent(new Vector2D(x, y), 0))
        .add(new BodyComponent(new RectBodyShape(W_POWERUP, H_POWERUP), false))
        .add(new InteractableComponent(powerUpMaxHealth))
        .add(new AnimationComponent(getAnimationsMap().get("shopHeart"), "idle"))
        .build();
    }

    /**
     * Creates a speed power up.
     * @param x
     * @param y
     * @return a speed power up entity.
     */
    public Entity createShopVelocity(final double x, final double y) {
        return new EntityBuilder()
        .add(new PositionComponent(new Vector2D(x, y), 0))
        .add(new BodyComponent(new RectBodyShape(W_POWERUP, H_POWERUP), false))
        .add(new InteractableComponent(powerUpSpeed))
        .add(new AnimationComponent(getAnimationsMap().get("shopSpeed"), "idle"))
        .build();
    }

    /**
     * Creates a gate.
     * @param x
     * @param y
     * @return gate.
     */
    public Entity createGate(final double x, final double y) {
        return new EntityBuilder()
        .add(new PositionComponent(new Vector2D(x, y), 0))
        .add(new BodyComponent(new RectBodyShape(W_GATE, H_GATE), false))
        .add(new InteractableComponent(useGate))
        .add(new AnimationComponent(getAnimationsMap().get("gate"), "idle"))
        .build();
    }
}
