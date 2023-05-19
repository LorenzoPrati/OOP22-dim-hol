package dimhol.logic.ai;

import dimhol.components.AiComponent;
import dimhol.components.BodyComponent;
import dimhol.components.MovementComponent;
import dimhol.components.PositionComponent;
import dimhol.entity.Entity;
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
    private PositionComponent enemyPos;
    private BodyComponent enemyBody;
    private PositionComponent playerPos;
    private AiComponent ai;
    private MovementComponent movComp;

    /**
     * {@inheritDoc}
     * @return
     */
    public boolean canExecute() {
        return playerCentralPos.distance(enemyCentralPos) <= aggroRay;
    }

    /**
     * {@inheritDoc}
     * @return
     */
    public abstract Optional<List<WorldEvent>> execute();

    /**
     * Player setter, thi method set also:
     * - player's position
     * - player's body
     * - player's central position
     */
    public final void setPlayer(final Entity player) {
        playerPos = (PositionComponent) player.getComponent(PositionComponent.class);
        BodyComponent playerBody = (BodyComponent) player.getComponent(BodyComponent.class);
        playerCentralPos = getCentralPosition(playerPos.getPos(), playerBody.getBodyShape());
    }

    /**
     * Enemy setter, this method set also:
     * - enemy's position
     * - enemy's body
     * - enemy's central position
     * - enemy's AI
     */
    public final void setEnemy(final Entity enemy) {
        this.enemy = enemy;
        enemyPos = (PositionComponent) this.enemy.getComponent(PositionComponent.class);
        enemyBody = (BodyComponent) this.enemy.getComponent(BodyComponent.class);
        enemyCentralPos = getCentralPosition(enemyPos.getPos(), enemyBody.getBodyShape());
        ai = (AiComponent) enemy.getComponent(AiComponent.class);
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
     * Enemy position getter.
     */
    protected final PositionComponent getEnemyPos() {
        return enemyPos;
    }

    /**
     * Enemy body getter.
     */
    protected final BodyComponent getEnemyBody() {
        return enemyBody;
    }

    /**
     * Player position getter.
     */
    protected final PositionComponent getPlayerPos() {
        return playerPos;
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
    protected final AiComponent getAi() {
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

    public double getWaitingTime() {
        return waitingTime;
    }
}
