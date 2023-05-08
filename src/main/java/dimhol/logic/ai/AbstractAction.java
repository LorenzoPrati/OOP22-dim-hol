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

    private Entity player;
    protected int aggroRay;
    protected int waitTime;
    protected Vector2D playerCentralPos;
    protected Vector2D enemyCentralPos;
    protected Entity enemy;
    protected AttackFactory attackFactory = new AttackFactory();
    protected PositionComponent enemyPos;
    protected BodyComponent enemyBody;
    protected PositionComponent playerPos;
    protected BodyComponent playerBody;
    private AiComponent ai;

    public boolean canExecute(Entity entity) {
        return playerCentralPos.distance(enemyCentralPos) <= aggroRay && ai.getPrevMovTime() >= waitTime;
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
        ai = (AiComponent) enemy.getComponent(AiComponent.class);
    }

    public Entity getPlayer() {
        return player;
    }

    private Vector2D getCentralPosition(Vector2D entityPos, BodyShape entityBody) {
        return new Vector2D(entityPos.getX() + (entityBody.getBoundingWidth() / 2),
                entityPos.getY() + (entityBody.getBoundingHeight() / 2));
    }

}
