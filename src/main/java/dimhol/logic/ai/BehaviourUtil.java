package dimhol.logic.ai;

import org.locationtech.jts.math.Vector2D;

/**
 * This class has util method for AI actions.
 */
public final class BehaviourUtil {

    /**
     * This value indicates the angle that forms the viewing quadrant of the entity
     * (in this case, referring to a view divided into 4 visual zones).
     */
    public static final int ANGLE = 45;
    /**
     * Flat corner.
     */
    public static final int FLAT_CORNER = 180;

    /**
     * Private constructors since it's util class.
     */
    private BehaviourUtil() {
    }

    /**
     * This method is used to know in which direction the player is located.
     * @param playerCentralPos is the central player position
     * @param enemyCentralPos is the central enemy position
     * @return the direction
     */
    public static Vector2D getPlayerDirection(final Vector2D playerCentralPos, final Vector2D enemyCentralPos) {
        final double y1 = playerCentralPos.getY();
        final double y2 = enemyCentralPos.getY();
        final double x1 = playerCentralPos.getX();
        final double x2 = enemyCentralPos.getX();
        final double m = (y1 - y2) / (x1 - x2);
        final var angle = Math.atan(m) * FLAT_CORNER / Math.PI;
        if (angle > -ANGLE && angle < ANGLE) {
            if (playerCentralPos.getX() > enemyCentralPos.getX()) {
                // right
                return new Vector2D(1, 0);
            } else {
                //left
                return new Vector2D(-1, 0);
            }
        } else {
            if (playerCentralPos.getY() > enemyCentralPos.getY()) {
                //down
                return new Vector2D(0, 1);
            } else {
                //up
                return new Vector2D(0, -1);
            }
        }
    }

}
