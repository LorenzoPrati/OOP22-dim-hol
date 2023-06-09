package dimhol.entity.factories;

import dimhol.components.AIComponent;
import dimhol.components.AnimationComponent;
import dimhol.components.BodyComponent;
import dimhol.components.CoinPocketComponent;
import dimhol.components.HealthComponent;
import dimhol.components.InteractorComponent;
import dimhol.components.MovementComponent;
import dimhol.components.PlayerComponent;
import dimhol.components.PositionComponent;
import dimhol.components.ShopKeeperComponent;
import dimhol.entity.Entity;
import dimhol.entity.EntityBuilder;
import dimhol.logic.ai.RoutineFactory;
import dimhol.logic.collision.RectBodyShape;
import org.locationtech.jts.math.Vector2D;

/**
 * A factory to create various entities.
 */
public class GenericFactory extends AbstractFactory {

    private static final double PLAYER_BASE_SPEED = 3;
    private static final double SHOP_KEEPER_SPEED = 0.2;
    private static final int PLAYER_BASE_HEALTH = 10;
    private static final int PLAYER_BASE_COINS = 15;
    private static final double PLAYER_WIDTH = 1;
    private static final double PLAYER_HEIGHT = 1;

    /**
     * Constructs a generic factory.
     */
    public GenericFactory() {
        super();
    }

    /**
     * Creates the player.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @return the player entity
     */
    public Entity createPlayer(final double x, final double y) {
        return new EntityBuilder().add(new PlayerComponent())
                .add(new PositionComponent(new Vector2D(x, y), 1))
                .add(new MovementComponent(new Vector2D(0, 1), PLAYER_BASE_SPEED, false))
                .add(new BodyComponent(new RectBodyShape(PLAYER_WIDTH, PLAYER_HEIGHT), true))
                .add(new CoinPocketComponent(PLAYER_BASE_COINS))
                .add(new HealthComponent(PLAYER_BASE_HEALTH))
                .add(new InteractorComponent())
                .add(new AnimationComponent(getAnimationsMap().get("player"), "idle down"))
                .build();
    }

    /**
     * Create shopkeeper.
     * @param x
     * @param y
     * @return
     */
    public final Entity createShopkeeper(final double x, final double y) {
        return new EntityBuilder().add(new ShopKeeperComponent())
                .add(new PositionComponent(new Vector2D(x, y), 0))
                .add(new MovementComponent(new Vector2D(0, 1), SHOP_KEEPER_SPEED, false))
                .add(new BodyComponent(new RectBodyShape(PLAYER_WIDTH, PLAYER_HEIGHT), true))
                .add(new HealthComponent(PLAYER_BASE_HEALTH))
                .add(new AnimationComponent(getAnimationsMap().get("shopkeeper"), "idle"))
                .add(new AIComponent(new RoutineFactory().createShopKeeperRoutine()))
                .build();
    }
}
