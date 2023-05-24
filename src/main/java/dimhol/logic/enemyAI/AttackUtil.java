package dimhol.logic.enemyAI;

import org.locationtech.jts.math.Vector2D;

/**
 * This class has util method for AI actions.
 */
public class AttackUtil {

    /**
     * This value indicates the angle that forms the viewing quadrant of the entity
     * (in this case, referring to a view divided into 4 visual zones).
     */
    public static final int ANGLE = 45;
    /**
     * This method is used to know in which direction the player is located.
     * @param playerCentralPos is the central player position
     * @param enemyCentralPos is the central enemy position
     * @return the direction
     */
    public static Vector2D getPlayerDirection(final Vector2D playerCentralPos, final Vector2D enemyCentralPos) {
        double y1 = playerCentralPos.getY();
        double y2 = enemyCentralPos.getY();
        double x1 = playerCentralPos.getX();
        double x2 = enemyCentralPos.getX();
        double m = (y1 - y2) / (x1 - x2);
        var angle = (Math.atan(m) * 180 / Math.PI);
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

    /**
     * This method returns the perfect radius that defines the area in which, if the player enters,
     * the enemy decides to perform a melee attack.
     * @param entityPos entity's position
     * @param enemyCentralPos entity's central position
     * @param playerPos player's position
     * @param playerCentralPos player's central position
     * @return the ray's value
     */
    /*public static double getMeleeRay(final Vector2D entityPos, final Vector2D enemyCentralPos,
                                  final Vector2D playerPos, final Vector2D playerCentralPos) {
        return entityPos.distance(enemyCentralPos) + playerPos.distance(playerCentralPos);
    }

     */
}