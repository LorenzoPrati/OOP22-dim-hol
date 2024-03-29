package dimhol.systems;

import dimhol.components.BodyComponent;
import dimhol.components.BulletComponent;
import dimhol.components.MovementComponent;
import dimhol.components.PositionComponent;
import dimhol.core.World;
import dimhol.entity.Entity;
import dimhol.events.RemoveEntityEvent;
import dimhol.levels.map.TileMap;
import org.locationtech.jts.math.Vector2D;

import java.math.BigDecimal;
import java.util.stream.IntStream;

/**
 * A System to detect collisions between entities and the game map.
 */
public final class MapCollisionSystem extends AbstractSystem {

    /**
     * Constructs a MapCollisionSystem with the specified World instance.
     */
    public MapCollisionSystem() {
        super(BodyComponent.class, MovementComponent.class);
    }

    /**
     * Checks if the entity represented by the specified components collides with the game map.
     *
     * @param pos      The position component of the entity.
     * @param body     The body component of the entity.
     * @param movement The movement component of the entity.
     * @param tileMap  The tile map representing the game map.
     * @return true if the entity collides with the game map, false otherwise.
     */
    private boolean checkMapCollision(final PositionComponent pos, final BodyComponent body,
                                      final MovementComponent movement, final TileMap tileMap) {
        final var x1 = pos.getPos().getX();
        final var x2 = x1 + body.getBodyShape().getBoundingWidth();
        final var y1 = pos.getPos().getY();
        final var y2 = y1 + body.getBodyShape().getBoundingHeight();

        //The use of BigDecimal has been found there: https://www.baeldung.com/java-bigdecimal-biginteger
        final int startX = BigDecimal.valueOf(x1).intValue();
        final int startY = BigDecimal.valueOf(y1).intValue();
        final int endX = BigDecimal.valueOf(x2).intValue();
        final int endY = BigDecimal.valueOf(y2).intValue();

        final Vector2D vector = movement.getDir();
        if (vector.getX() == 0) {
            return IntStream.rangeClosed(startX, endX)
                    .anyMatch(i -> isTileWalkable(tileMap, vector.getY() < 0 ? startY : endY, i));
        } else {
            return IntStream.rangeClosed(startY, endY)
                    .anyMatch(i -> isTileWalkable(tileMap, i, vector.getX() < 0 ? startX : endX));
        }
    }

    /**
     * Checks if the tile at the specified coordinates is walkable.
     *
     * @param tileMap The tile map representing the game map.
     * @param rows    The rows index of the tile.
     * @param cols    The cols index of the tile.
     * @return true if the tile is walkable, false otherwise.
     */
    private boolean isTileWalkable(final TileMap tileMap, final int rows, final int cols) {
        return !tileMap.getTile(rows, cols).isWalkableTile();
    }

    @Override
    protected void process(final Entity entity, final double deltaTime, final World world) {
        final var position = (PositionComponent) entity.getComponent(PositionComponent.class);
        final var body = (BodyComponent) entity.getComponent(BodyComponent.class);
        final var movement = (MovementComponent) entity.getComponent(MovementComponent.class);
        if (checkMapCollision(position, body, movement, world.getLevelManager().getTileMap())) {
            if (entity.hasComponent(BulletComponent.class)) {
                world.notifyEvent(new RemoveEntityEvent(entity));
            } else {
                position.resetToLastPos();
            }
        }
    }
}
