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

    private final TileMap tileMap;

    /**
     * Constructs a MapCollisionSystem with the specified World instance.
     * @param world The world instance this system is associated.
     */
    public MapCollisionSystem(final WorldImpl world) {
        super(world, PositionComponent.class, BodyComponent.class, MovementComponent.class);
        tileMap = world.getScene().getMapLoader().getTileMap();
    }

    /**
     * Checks if the entity represented by the specified components.
     * @param pos The position component of the entity.
     * @param body The body component of the entity.
     * @param movement The movement component of the entity.
     * @return true if the entity collides with the game map, false otherwise.
     */
    private boolean checkMapCollision(final PositionComponent pos, final BodyComponent body, final MovementComponent movement) {
        var x1 = pos.getPos().getX();
        var y1 = pos.getPos().getY();
        var x2 = x1 + body.getBodyShape().getBoundingWidth();
        var y2 = y1 + body.getBodyShape().getBoundingHeight();

        int startY = (int) Math.floor(y1);
        int endY = (int) Math.floor(y2);
        int startX = (int) Math.floor(x1);
        int endX = (int) Math.floor(x2);

        if (movement.getDir().equals(new Vector2D(0, -1))) { // Moving UP
            return IntStream.rangeClosed(startX, endX)
                    .anyMatch(i -> !tileMap.getTile(startY, i).isWalkable());
        } else if (movement.getDir().equals(new Vector2D(0, 1))) { // Moving DOWN
            return IntStream.rangeClosed(startX, endX)
                    .anyMatch(i -> !tileMap.getTile(endY, i).isWalkable());
        } else if (movement.getDir().equals(new Vector2D(-1, 0))) { // Moving LEFT
            return IntStream.rangeClosed(startY, endY)
                    .anyMatch(i -> !tileMap.getTile(i, startX).isWalkable());
        } else if (movement.getDir().equals(new Vector2D(1, 0))) { // Moving RIGHT
            return IntStream.rangeClosed(startY, endY)
                    .anyMatch(i -> !tileMap.getTile(i, endX).isWalkable());
        }

        // Invalid direction
        return false;
    }

    @Override
    public void process(final Entity entity, final double deltaTime) {
        var position = (PositionComponent) entity.getComponent(PositionComponent.class);
        var body = (BodyComponent) entity.getComponent(BodyComponent.class);
        var movement = (MovementComponent) entity.getComponent(MovementComponent.class);
        if (checkMapCollision(position, body, movement)) {
            position.resetToLastPos();
        }
    }
}
