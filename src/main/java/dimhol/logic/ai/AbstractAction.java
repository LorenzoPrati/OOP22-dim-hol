package dimhol.logic.ai;

import dimhol.components.BodyComponent;
import dimhol.components.PositionComponent;
import dimhol.entity.factories.AttackFactory;
import dimhol.entity.Entity;
import dimhol.events.Event;
import dimhol.logic.collision.BodyShape;
import org.locationtech.jts.math.Vector2D;

import java.util.List;
import java.util.Optional;

public abstract class AbstractAction implements Action {

    private Entity player;
    protected Optional<Integer> aggroRay = Optional.empty();
    protected int waitTime;
    protected Vector2D playerCentralPos;
    protected Vector2D enemyCentralPos;
    protected Entity enemy;
    protected AttackFactory attackFactory = new AttackFactory();
    protected PositionComponent enemyPos;
    protected BodyComponent enemyBody;
    protected PositionComponent playerPos;
    protected BodyComponent playerBody;

    public AbstractAction(int aggroRay, int waitTime) {
        this.aggroRay = Optional.of(aggroRay);
        this.waitTime = waitTime;
    }

    public AbstractAction(int aggroRay) {
        this.aggroRay = Optional.of(aggroRay);
    }

    public AbstractAction() {
    }

    public boolean canExecute(Entity entity) {
        if (aggroRay.isPresent()) {
            return playerCentralPos.distance(enemyCentralPos) <= aggroRay.get();
        } else {
            return true;
        }
    }
    public abstract Optional<List<Event>> execute();

    public void setPlayer(Entity player) {
        this.player = player;
        playerPos = (PositionComponent) player.getComponent(PositionComponent.class);
        playerBody = (BodyComponent) player.getComponent(BodyComponent.class);
        playerCentralPos = getCentralPosition(playerPos.getPos(), playerBody.getBodyShape());
    }

    public void setEnemy(Entity enemy) {
        this.enemy = enemy;
        enemyPos = (PositionComponent) this.enemy.getComponent(PositionComponent.class);
        enemyBody = (BodyComponent) this.enemy.getComponent(BodyComponent.class);
        enemyCentralPos = getCentralPosition(enemyPos.getPos(), enemyBody.getBodyShape());
    }

    public Entity getPlayer() {
        return player;
    }

    public int getAggro() {
        return this.aggroRay.get();
    }

    private Vector2D getCentralPosition(Vector2D entityPos, BodyShape entityBody) {
        return new Vector2D(entityPos.getX() + (entityBody.getBoundingWidth() / 2),
                entityPos.getY() + (entityBody.getBoundingHeight() / 2));
    }
}
