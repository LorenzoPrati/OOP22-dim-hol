package it.unibo.dimhol.ai;

import it.unibo.dimhol.components.AiComponent;
import it.unibo.dimhol.components.MovementComponent;
import it.unibo.dimhol.components.PositionComponent;
import it.unibo.dimhol.entity.Entity;
import org.locationtech.jts.math.Vector2D;

import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * A random movement behaviour.
 */
public final class RandomMovementAction implements Action {

    private static final int START_RANGE_TIME = 1000;
    private static final int END_RANGE_TIME = 3000;
    private final Random rand = new Random();
    private final int aggroRay;
    private int walkingTimeMillis;

    /**
     *
     * @param aggroRay is the distance that enemy value to activate this action.
     */
    public RandomMovementAction(final int aggroRay) {
        this.aggroRay = aggroRay;
        setWalkingTimeMillis();
    }

    @Override
    public boolean canExecute(final Entity player, final Entity enemy) {
        var playerPos = (PositionComponent) player.getComponent(PositionComponent.class);
        var enemyPos = (PositionComponent) enemy.getComponent(PositionComponent.class);

        var distance = playerPos.getPos().distance(enemyPos.getPos());
        return distance >= aggroRay;
    }

    @Override
    public Optional<List<Entity>> execute(final Entity enemy) {
        var movComp = (MovementComponent) enemy.getComponent(MovementComponent.class);
        var aiComp = (AiComponent) enemy.getComponent(AiComponent.class);
        movComp.setEnabled(true);
        if (System.currentTimeMillis() - aiComp.getPrevMovTime() >= walkingTimeMillis) {
            var point = rand.nextDouble(-1, 1);
            movComp.setDir(new Vector2D(point, point));
            aiComp.setPrevMovTime(System.currentTimeMillis());
            setWalkingTimeMillis();
        }
        return Optional.empty();
    }

    /**
     *
     * Set walkingTimeMillis with a random value for a more natural behaviour.
     */
    public void setWalkingTimeMillis() {
        this.walkingTimeMillis = rand.nextInt(START_RANGE_TIME, END_RANGE_TIME);
    }

}
