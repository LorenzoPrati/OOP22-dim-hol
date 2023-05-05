package dimhol.logic.ai;

import dimhol.components.BodyComponent;
import dimhol.components.PositionComponent;
import dimhol.entity.factories.AttackFactory;
import dimhol.entity.Entity;
import dimhol.events.Event;
import org.locationtech.jts.math.Vector2D;

import java.util.List;
import java.util.Optional;

public abstract class AbstractAction implements Action {

    private Entity player;
    protected double aggroRay;
    protected int waitTime;
    protected Vector2D playerCentralPos;
    protected Vector2D enemyCentralPos;
    protected Entity entity;
    protected AttackFactory attackFactory = new AttackFactory();
    protected PositionComponent entityPos;
    protected BodyComponent entityBody;
    protected PositionComponent playerPos;

    public AbstractAction(int aggroRay, int waitTime) {
        this.aggroRay = aggroRay;
        this.waitTime = waitTime;
    }

    public AbstractAction(int aggroRay) {
        this.aggroRay = aggroRay;
    }

    public boolean canExecute(Entity entity) {
        this.entity = entity;
        playerPos = (PositionComponent) player.getComponent(PositionComponent.class);
        var playerBody = (BodyComponent) player.getComponent(BodyComponent.class);
        entityPos = (PositionComponent) entity.getComponent(PositionComponent.class);
        entityBody = (BodyComponent) entity.getComponent(BodyComponent.class);

        playerCentralPos = MathUtilities.getCentralPosition(playerPos.getPos(), playerBody.getBodyShape());
        enemyCentralPos = MathUtilities.getCentralPosition(entityPos.getPos(), entityBody.getBodyShape());

        return MathUtilities.getDistance(playerCentralPos, enemyCentralPos) <= aggroRay;
    }
    public abstract Optional<List<Event>> execute();

    public void setPlayer(Entity player) {
        this.player = player;
    }

    public Entity getPlayer() {
        return player;
    }

    public int getAggro(AbstractAction action) {
        return (int) this.aggroRay;
    }
}
