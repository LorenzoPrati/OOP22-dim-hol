package dimhol.systems;

import dimhol.components.BodyComponent;
import dimhol.components.MovementComponent;
import dimhol.components.PositionComponent;
import dimhol.core.WorldImpl;
import dimhol.entity.Entity;
import dimhol.gamelevels.map.TileMap;
import org.locationtech.jts.math.Vector2D;

import java.math.BigDecimal;
import java.util.stream.IntStream;

/**
 * A System to detect collisions between entities and the game map.
 */
public final class MapCollisionSystem extends AbstractSystem {

    private final TileMap tileMap;

    /**
     * Constructs a MapCollisionSystem with the specified World instance.
     *
     * @param world The world instance this system is associated.
     */
    public MapCollisionSystem(final WorldImpl world) {
        super(world, PositionComponent.class, BodyComponent.class, MovementComponent.class);
        tileMap = world.getLevelManager().getTileMap();
    }

    /**
     * Checks if the entity represented by the specified components collides with the game map.
     *
     * @param pos The position component of the entity.
     * @param body The body component of the entity.
     * @param movement The movement component of the entity.
     * @return true if the entity collides with the game map, false otherwise.
     */
    private boolean checkMapCollision(final PositionComponent pos, final BodyComponent body, final MovementComponent movement) {
        var x1 = pos.getPos().getX();
        var x2 = x1 + body.getBodyShape().getBoundingWidth();
        var y1 = pos.getPos().getY();
        var y2 = y1 + body.getBodyShape().getBoundingHeight();

        //The use of BigDecimal has been found there: https://www.baeldung.com/java-bigdecimal-biginteger
        int startX = BigDecimal.valueOf(x1).intValue();
        int startY = BigDecimal.valueOf(y1).intValue();
        int endX = BigDecimal.valueOf(x2).intValue();
        int endY = BigDecimal.valueOf(y2).intValue();

        Vector2D vector = movement.getDir();
        if (vector.getX() == 0) {
            return IntStream.rangeClosed(startX, endX)
                    .anyMatch(i -> isTileWalkable(vector.getY() < 0 ? startY : endY, i));
        } else {
            return IntStream.rangeClosed(startY, endY)
                    .anyMatch(i -> isTileWalkable(i, vector.getX() < 0 ? startX : endX));
        }
    }

    /**
     * Checks if the tile at the specified coordinates is walkable.
     *
     * @param rows The rows index of the tile.
     * @param cols The cols index of the tile.
     * @return true if the tile is walkable, false otherwise.
     */
    private boolean isTileWalkable(final int rows, final int cols) {
        return !tileMap.getTile(rows, cols).isWalkable();
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
