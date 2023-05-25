package dimhol.entity.factories;

import dimhol.components.*;
import dimhol.core.World;
import dimhol.entity.Entity;
import dimhol.entity.EntityBuilder;
import dimhol.events.ChangeLevelEvent;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import org.locationtech.jts.math.Vector2D;
import dimhol.logic.collision.RectBodyShape;

public class InteractableObjectFactory extends AbstractFactory {
    private static final double W = 3;
    private static final double H = 3;
    private static final int MAX_HEALTH_INCREASE = 10;
    private static final double VELOCITY_INCREASE = 0.5;
    private static final int MAX_HEALTH_PRICE = 10;
    private static final int VELOCITY_PRICE = 15;

    public InteractableObjectFactory() {
        super();
    }

    BiPredicate<Entity,Integer> checkCoins = (e,i) -> {
        var currentCoins = (CoinPocketComponent)e.getComponent(CoinPocketComponent.class);
        return currentCoins.getCurrentAmount() >= i;
    };

    Predicate<World> checkAllEnemyAreDead = (w) -> w.getEntities()
        .stream()
        .noneMatch(e -> e.hasComponent(AiComponent.class));
        
    

    BiConsumer<Entity, World> powerUpMaxHealth = (e,w)-> {
        if(checkCoins.test(e, MAX_HEALTH_PRICE )) {
            var healthComp = (HealthComponent)e.getComponent(HealthComponent.class);
            healthComp.setMaxHealth(healthComp.getMaxHealth() + MAX_HEALTH_INCREASE);
        }
    };

    BiConsumer<Entity, World> powerUpSpeed = (e,w)-> {
        if(checkCoins.test(e, VELOCITY_PRICE)) {
            var moveComp = (MovementComponent)e.getComponent(MovementComponent.class);
            moveComp.setSpeed(moveComp.getSpeed() + VELOCITY_INCREASE);
        } 
    };

    BiConsumer<Entity, World> useGate = (e,w)-> {
        if(checkAllEnemyAreDead.test(w)) {
            w.notifyEvent(new ChangeLevelEvent());
        }
    };
    
    public Entity createShopHeart(final double x, final double y) {
        return new EntityBuilder()
        .add(new PositionComponent(new Vector2D(x,y), 1))
        .add(new BodyComponent(new RectBodyShape(W,H), true))
        .add(new InteractableComponent(powerUpMaxHealth))
        .add(new AnimationComponent(this.map.get("shopHeart"), "idle"))
        .build();
    }

    public Entity createShopVelocity(final double x, final double y) {
        return new EntityBuilder()
        .add(new PositionComponent(new Vector2D(x,y), 1))
        .add(new BodyComponent(new RectBodyShape(W,H), true))
        .add(new InteractableComponent(powerUpSpeed))
        .add(new AnimationComponent(this.map.get("shopSpeed"), "idle"))
        .build();
    }

    public Entity createGate(final double x, final double y) {
        return new EntityBuilder()
        .add(new PositionComponent(new Vector2D(x,y), 0))
        .add(new BodyComponent(new RectBodyShape(W,H), false))
        .add(new InteractableComponent(useGate))
        .add(new AnimationComponent(this.map.get("gate"), "idle")) //TO DO add gate sprites
        .build();
    }
}
