package dimhol.entity.factories;

import dimhol.components.AnimationComponent;
import dimhol.components.BodyComponent;
import dimhol.components.CoinPocketComponent;
import dimhol.components.HealthComponent;
import dimhol.components.InteractorComponent;
import dimhol.components.MovementComponent;
import dimhol.components.PlayerComponent;
import dimhol.components.PositionComponent;
import dimhol.entity.Entity;
import dimhol.entity.EntityBuilder;
import dimhol.logic.collision.RectBodyShape;
import org.locationtech.jts.math.Vector2D;

/**
 * A factory to create various entities.
 */
public class GenericFactory extends AbstractFactory {

    private static final double PLAYER_SPEED = 3;
    private static final double SHOP_KEEPER_SPEED = 1;
    private static final int PLAYER_HEALTH = 10;
    private static final double PLAYER_WIDTH = 1;
    private static final double PLAYER_HEIGHT = 1;
    private static final double SHOP_KEEPER_WIDTH= 2;
    private static final double SHOP_KEEPER_HEIGHT = 2;

    public GenericFactory() {
        super();
    }

    public Entity createPlayer(final double x, final double y) {
        return new EntityBuilder().add(new PlayerComponent())
                .add(new PositionComponent(new Vector2D(x,y), 1))
                .add(new MovementComponent(new Vector2D(0,1), PLAYER_SPEED, false))
                .add(new BodyComponent(new RectBodyShape(PLAYER_WIDTH, PLAYER_HEIGHT), true))
                .add(new CoinPocketComponent(20))
                .add(new HealthComponent(PLAYER_HEALTH))
                .add(new InteractorComponent())
                .add(new AnimationComponent(getAnimationsMap().get("player"),"idle down"))
                .build();
    }

    public Entity createShopkeeper(final double x, final double y) {
        return new EntityBuilder()
                .add(new PositionComponent(new Vector2D(x,y), 0))
                .add(new MovementComponent(new Vector2D(0, 1), SHOP_KEEPER_SPEED, false))
                .add(new BodyComponent(new RectBodyShape(SHOP_KEEPER_WIDTH, SHOP_KEEPER_HEIGHT), true))
                .add(new AnimationComponent(getAnimationsMap().get("shopkeeper"),"idle"))
                .build();
    }
}
