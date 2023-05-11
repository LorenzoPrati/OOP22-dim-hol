package dimhol.systems;

import dimhol.core.WorldImpl;
import dimhol.entity.Entity;
import dimhol.map.TileMap;
import dimhol.components.BodyComponent;
import dimhol.components.MovementComponent;
import dimhol.components.PositionComponent;
import org.locationtech.jts.math.Vector2D;

import java.util.stream.IntStream;

/**
 * A system to register collisions between entities and the game map.
 */
public class MapCollisionSystem extends AbstractSystem {

    private TileMap tileMap;

    /**
     * Constructs a MapCollisionSystem with the specified World instance.
     * @param world The world instance this system is associated.
     */
    public MapCollisionSystem(WorldImpl world) {
        super(world, PositionComponent.class, BodyComponent.class, MovementComponent.class);
        tileMap = world.getScene().getMapLoader().getTileMap();
    }

    /**
     * Checks if the entity represented by the specified components 
     * @param position
     * @param body
     * @param movement
     * @return true if the entity collides with the game map, false otherwise.
     */
    private boolean checkMapCollision(PositionComponent position, BodyComponent body, MovementComponent movement) {
        double x1 = position.getPos().getX();
        double y1 = position.getPos().getY();

        //Moving UP:
        if (movement.getDir().equals(new Vector2D(0, -1))) {
            var x2 = x1 + body.getBodyShape().getBoundingWidth();
            var y = position.getPos().getY();
            return IntStream.iterate((int) x1, i -> i <= x2, i -> i + 1).anyMatch(i -> !tileMap.getTile((int) Math.floor(y), (int) Math.floor(i)).isWalkable());
        } //Moving DOWN:
        else if (movement.getDir().equals(new Vector2D(0, 1))) {
            var x2 = x1 + body.getBodyShape().getBoundingWidth();
            var y = position.getPos().getY() + body.getBodyShape().getBoundingHeight();
            return IntStream.iterate((int) x1, i -> i <= x2, i -> i + 1).anyMatch(i -> !tileMap.getTile((int) Math.floor(y), (int) Math.floor(i)).isWalkable());
        } //Moving LEFT:
        else if (movement.getDir().equals(new Vector2D(-1, 0))) {
            var x = position.getPos().getX();
            var y2 = y1 + body.getBodyShape().getBoundingHeight();
            return IntStream.iterate((int) y1, i -> i <= y2, i -> i + 1).anyMatch(i -> !tileMap.getTile((int) Math.floor(i), (int) Math.floor(x)).isWalkable());
        } //Moving RIGHT:
        else if (movement.getDir().equals(new Vector2D(1, 0))) {
            var x = position.getPos().getX() + body.getBodyShape().getBoundingWidth();
            var y2 = y1 + body.getBodyShape().getBoundingHeight();
            return IntStream.iterate((int) y1, i -> i <= y2, i -> i + 1).anyMatch(i -> !tileMap.getTile((int) Math.floor(i), (int) Math.floor(x)).isWalkable());
        }
          //Invalid direction
        return false;
    }

    @Override
    public void process(Entity entity, double deltaTime) {
        var position = (PositionComponent) entity.getComponent(PositionComponent.class);
        var body = (BodyComponent) entity.getComponent(BodyComponent.class);
        var movement = (MovementComponent) entity.getComponent(MovementComponent.class);
        if (checkMapCollision(position, body, movement)) {
            position.resetToLastPos();
        }
    }
}