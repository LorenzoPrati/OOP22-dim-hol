package dimhol.logic.ai;

import dimhol.components.AiComponent;
import dimhol.components.BodyComponent;
import dimhol.components.PositionComponent;
import dimhol.entity.Entity;
import dimhol.entity.factories.AttackFactory;
import dimhol.events.Event;
import dimhol.logic.collision.BodyShape;
import org.locationtech.jts.math.Vector2D;

import java.util.List;
import java.util.Optional;

public abstract class AbstractAction implements Action {

    /**
     * Divisor = 2 is util to have half of a body's width and height.
     */
    private static final int DIVISOR = 2;
    private Entity player;
    private int aggroRay = Integer.MAX_VALUE;
    private int waitingTime = 0;
    private Vector2D playerCentralPos;
    private Vector2D enemyCentralPos;
    private Entity enemy;
    private final AttackFactory attackFactory = new AttackFactory();
    private PositionComponent enemyPos;
    private BodyComponent enemyBody;
    private PositionComponent playerPos;
    private BodyComponent playerBody;
    private AiComponent ai;

    /**
     * {@inheritDoc}
     * @return
     */
    public boolean canExecute() {
        return playerCentralPos.distance(enemyCentralPos) <= aggroRay
                && (ai.getCurrentTime() - ai.getPrevTime()) >= waitingTime;
    }

    /**
     * {@inheritDoc}
     * @return
     */
    public abstract Optional<List<Event>> execute();

    /**
     * Player setter, thi method set also:
     * - player's position
     * - player's body
     * - player's central position
     * @param player
     */
    public final void setPlayer(final Entity player) {
        this.player = player;
        playerPos = (PositionComponent) player.getComponent(PositionComponent.class);
        playerBody = (BodyComponent) player.getComponent(BodyComponent.class);
        playerCentralPos = getCentralPosition(playerPos.getPos(), playerBody.getBodyShape());
    }

    /**
     * Enemy setter, this method set also:
     * - enemy's position
     * - enemy's body
     * - enemy's central position
     * - enemy's AI
     * @param enemy
     */
    public final void setEnemy(final Entity enemy) {
        this.enemy = enemy;
        enemyPos = (PositionComponent) this.enemy.getComponent(PositionComponent.class);
        enemyBody = (BodyComponent) this.enemy.getComponent(BodyComponent.class);
        enemyCentralPos = getCentralPosition(enemyPos.getPos(), enemyBody.getBodyShape());
        ai = (AiComponent) enemy.getComponent(AiComponent.class);
    }

    /**
     * Player getter.
     * @return player
     */
    public final Entity getPlayer() {
        return player;
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

    public int getAggroRay() {
        return aggroRay;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public Vector2D getPlayerCentralPos() {
        return playerCentralPos;
    }

    public Vector2D getEnemyCentralPos() {
        return enemyCentralPos;
    }

    public Entity getEnemy() {
        return enemy;
    }

    public AttackFactory getAttackFactory() {
        return attackFactory;
    }

    public PositionComponent getEnemyPos() {
        return enemyPos;
    }

    public BodyComponent getEnemyBody() {
        return enemyBody;
    }

    public PositionComponent getPlayerPos() {
        return playerPos;
    }

    public void setAggroRay(int aggroRay) {
        this.aggroRay = aggroRay;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }
}
