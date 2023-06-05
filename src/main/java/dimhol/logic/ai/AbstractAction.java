package dimhol.logic.ai;

import dimhol.components.AIComponent;
import dimhol.components.BodyComponent;
import dimhol.components.MovementComponent;
import dimhol.components.PositionComponent;
import dimhol.entity.Entity;
import dimhol.entity.EntityImpl;
import dimhol.entity.factories.EnemyAttackFactory;
import dimhol.events.WorldEvent;
import dimhol.logic.collision.BodyShape;
import org.locationtech.jts.math.Vector2D;

import java.util.List;
import java.util.Optional;

public abstract class AbstractAction implements Action {

    /**
     * Divisor = 2 is util to have half of a body's width and height.
     */
    private static final int DIVISOR = 2;

    private double aggroRay = Double.MAX_VALUE;
    private double waitingTime = 0;
    private Vector2D playerCentralPos;
    private Vector2D enemyCentralPos;
    private Entity enemy;
    private final EnemyAttackFactory attackFactory = new EnemyAttackFactory();
    private AIComponent ai;
    private MovementComponent movComp;

    @Override
    public boolean canExecute() {
        return getPlayerCentralPos().distance(getEnemyCentralPos()) <= aggroRay;
    }

    @Override
    public abstract Optional<List<WorldEvent>> execute();

    @Override
    public final void setPlayer(final Entity player) {
        PositionComponent playerPosComp = (PositionComponent) player.getComponent(PositionComponent.class);
        BodyComponent playerBodyComp = (BodyComponent) player.getComponent(BodyComponent.class);
        playerCentralPos = getCentralPosition(playerPosComp.getPos(), playerBodyComp.getBodyShape());
    }

    @Override
    public final void setEnemy(final Entity enemy) {
        this.enemy = new EntityImpl(enemy);
        PositionComponent enemyPosComp = (PositionComponent) enemy.getComponent(PositionComponent.class);
        BodyComponent enemyBodyComp = (BodyComponent) enemy.getComponent(BodyComponent.class);
        Vector2D enemyPos = enemyPosComp.getPos();
        enemyCentralPos = getCentralPosition(enemyPos, enemyBodyComp.getBodyShape());
        ai = (AIComponent) enemy.getComponent(AIComponent.class);
        movComp = (MovementComponent) enemy.getComponent(MovementComponent.class);
    }

    /**
     * This method returns an entity's central position.
     * @param entityPos entity position (top-left point)
     * @param entityBody entity body
     * @return central position
     */
    private Vector2D getCentralPosition(final Vector2D entityPos, final BodyShape entityBody) {
        return new Vector2D(entityPos.getX() + (entityBody.getBoundingWidth() / DIVISOR),
                entityPos.getY() + (entityBody.getBoundingHeight() / DIVISOR));
    }

    /**
     * Player's central position getter.
     */
    protected Vector2D getPlayerCentralPos() {
        return playerCentralPos;
    }

    /**
     * Enemy' central position getter.
     */
    protected Vector2D getEnemyCentralPos() {
        return enemyCentralPos;
    }

    /**
     * Attack Factory getter.
     */
    protected final EnemyAttackFactory getAttackFactory() {
        return attackFactory;
    }

    /**
     * Aggro ray setter.
     */
    protected final void setAggroRay(final double aggroRay) {
        this.aggroRay = aggroRay;
    }

    /**
     * Waiting time setter.
     */
    protected final void setWaitingTime(final double waitingTime) {
        this.waitingTime = waitingTime;
    }

    /**
     * Enemy's AI getter.
     */
    protected final AIComponent getAi() {
        return ai;
    }

    /**
     * Enemy's movement component getter.
     */
    protected final MovementComponent getMovComp() {
        return movComp;
    }

    /**
     * Enemy getter.
     */
    protected Entity getEnemy() {
        return enemy;
    }

    /**
     * Waiting time getter.
     * @return waiting time
     */
    public double getWaitingTime() {
        return waitingTime;
    }

    /**
     * Aggro ray getter.
     * @return aggro ray
     */
    public double getAggroRay() {
        return aggroRay;
    }

    /**
     * Check if waiting time passed.
     * @return
     */
    protected boolean reloadTimePassed() {
        if (getAi().getCurrentTime() - getAi().getPrevTime() >= getWaitingTime()) {
            getAi().setPrevTime(getAi().getCurrentTime());
            return true;
        }
        return false;
    }
}
