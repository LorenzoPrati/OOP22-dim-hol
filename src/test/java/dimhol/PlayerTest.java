package dimhol;

import dimhol.components.*;
import dimhol.entity.Entity;
import dimhol.entity.factories.GenericFactory;
import dimhol.input.InputImpl;
import dimhol.logic.player.states.IdleState;
import dimhol.logic.player.states.WalkState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.math.Vector2D;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests the player.
 */
class PlayerTest {

    private static final double PLAYER_BASE_SPEED = 3;
    private static final int PLAYER_BASE_HEALTH = 10;

    private Entity playerEntity;
    private HealthComponent health;
    private CoinPocketComponent coins;
    private MovementComponent mov;
    private BodyComponent body;
    private AnimationComponent animation;
    private PlayerComponent player;
    private PositionComponent position;

    @BeforeEach
    void initializePlayer() {
        var genericFactory = new GenericFactory();
        this.playerEntity = genericFactory.createPlayer(0,0);
        this.health = (HealthComponent) playerEntity.getComponent(HealthComponent.class);
        this.coins = (CoinPocketComponent) playerEntity.getComponent(CoinPocketComponent.class);
        this.mov = (MovementComponent) playerEntity.getComponent(MovementComponent.class);
        this.body = (BodyComponent) playerEntity.getComponent(BodyComponent.class);
        this.animation = (AnimationComponent) playerEntity.getComponent(AnimationComponent.class);
        this.player = (PlayerComponent) playerEntity.getComponent(PlayerComponent.class);
        this.position = (PositionComponent) playerEntity.getComponent(PositionComponent.class);
    }

    @Test
    void testBaseValue() {
        assertEquals(PLAYER_BASE_HEALTH, this.health.getCurrentHealth());
        assertEquals(PLAYER_BASE_SPEED, this.mov.getSpeed());
        assertEquals(0, this.coins.getCurrentAmount());
        assertEquals(1, this.body.getBodyShape().getBoundingWidth());
        assertEquals(1, this.body.getBodyShape().getBoundingHeight());
        assertEquals(new Vector2D(0,0), this.position.getPos());
        assertTrue(this.player.getState().getClass().isInstance(new IdleState()));
    }

    @Test
    void testState() {
        var input = new InputImpl();
        input.setUp(true);
        assertTrue(this.player.getState().transition(input).get().getClass().isInstance(new WalkState()));
    }

}
