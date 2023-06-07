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
import java.util.function.Predicate;
import org.locationtech.jts.math.Vector2D;
import dimhol.logic.collision.RectBodyShape;

/**
 * .
 */
public class InteractableObjectFactory extends AbstractFactory {
    private static final double W_POWERUP = 1;
    private static final double H_POWERUP = 1;
    private static final double W_GATE = 2;
    private static final double H_GATE = 2;
    private static final int MAX_HEALTH_INCREASE = 10;
    private static final double VELOCITY_INCREASE = 0.8;
    private static final int MAX_HEALTH_PRICE = 10;
    private static final int VELOCITY_PRICE = 15;

    /**
     * .
     */
    public InteractableObjectFactory() {
        super();
    }

    BiPredicate<Entity,Integer> checkCoins = (e, i) -> {
        var currentCoins = (CoinPocketComponent) e.getComponent(CoinPocketComponent.class);
        return currentCoins.getCurrentAmount() >= i;
    };
    
    BiConsumer<Entity,Integer> payPrice = (e, p) -> {
        var coinPocket = (CoinPocketComponent) e.getComponent(CoinPocketComponent.class);
        coinPocket.setAmount(coinPocket.getCurrentAmount() - p);
    };

    Predicate<World> checkAllEnemyAreDead = (w) -> w.getEntities()
        .stream()
        .noneMatch(e -> e.hasComponent(AIComponent.class));

    BiFunction<Entity, World, Boolean> powerUpMaxHealth = (e, w)-> {
        if (checkCoins.test(e, MAX_HEALTH_PRICE )) {
            payPrice.accept(e, MAX_HEALTH_PRICE);
            var healthComp = (HealthComponent)e.getComponent(HealthComponent.class);
            healthComp.setMaxHealth(healthComp.getMaxHealth() + MAX_HEALTH_INCREASE);
            return true;
        }
        return false;
    };

    BiFunction<Entity, World, Boolean> powerUpSpeed = (e, w)-> {
        if (checkCoins.test(e, VELOCITY_PRICE)) {
            payPrice.accept(e, VELOCITY_PRICE);
            var moveComp = (MovementComponent)e.getComponent(MovementComponent.class);
            moveComp.setSpeed(moveComp.getSpeed() + VELOCITY_INCREASE);
            return true;
        } 
        return false;
    };

    BiFunction<Entity, World, Boolean> useGate = (e,w)-> {
        if(checkAllEnemyAreDead.test(w) || checkForShopKeeperEntity(w)) {
            w.notifyEvent(new ChangeLevelEvent());
            return true;
        }
        return false;
    };

    private boolean checkForShopKeeperEntity(World world) {
        return world.getEntities().stream().anyMatch(e -> e.hasComponent(ShopKeeperComponent.class));
    }

    public Entity createShopHeart(final double x, final double y) {
        return new EntityBuilder()
        .add(new PositionComponent(new Vector2D(x,y), 0))
        .add(new BodyComponent(new RectBodyShape(W_POWERUP, H_POWERUP), false))
        .add(new InteractableComponent(powerUpMaxHealth))
        .add(new AnimationComponent(getAnimationsMap().get("shopHeart"), "idle"))
        .build();
    }

    public Entity createShopVelocity(final double x, final double y) {
        return new EntityBuilder()
        .add(new PositionComponent(new Vector2D(x,y), 0))
        .add(new BodyComponent(new RectBodyShape(W_POWERUP, H_POWERUP), false))
        .add(new InteractableComponent(powerUpSpeed))
        .add(new AnimationComponent(getAnimationsMap().get("shopSpeed"), "idle"))
        .build();
    }

    public Entity createGate(final double x, final double y) {
        return new EntityBuilder()
        .add(new PositionComponent(new Vector2D(x,y), 0))
        .add(new BodyComponent(new RectBodyShape(W_GATE, H_GATE), false))
        .add(new InteractableComponent(useGate))
        .add(new AnimationComponent(getAnimationsMap().get("gate"), "idle"))
        .build();
    }
}
