package dimhol.logic.ai;

import dimhol.logic.collision.BodyShape;
import dimhol.logic.util.DirectionUtil;
import org.locationtech.jts.math.Vector2D;

/**
 * This class has util method for AI actions.
 */
class AttackUtil {

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
     * This method positions the attack; whatever it is, next to the body of the entity exactly
     * centered with respect to its height or width (depending on the direction).
     * @param dir attack's direction
     * @param entityCentralPos entity's central position
     * @param entityBody entity's body shape
     * @param attackWidth attack width
     * @param attackHeight attack height
     * @return the attack position
     */
    public static Vector2D getAttackPos(final Vector2D dir, final Vector2D entityCentralPos, final BodyShape entityBody,
                                        final double attackWidth, final double attackHeight) {
        double bulletX;
        double bulletY;

        switch (DirectionUtil.getStringFromVec(dir)) {
            case "right" -> {
                bulletX = entityCentralPos.getX() + (entityBody.getBoundingWidth() / 2);
                bulletY = entityCentralPos.getY() - (attackHeight / 4);
            }
            case "left" -> {
                bulletX = entityCentralPos.getX() - (entityBody.getBoundingWidth() / 2) - attackHeight;
                bulletY = entityCentralPos.getY() - (attackWidth / 4);
            }
            case "down" -> {
                bulletX = entityCentralPos.getX() - (attackHeight / 4);
                bulletY = entityCentralPos.getY() + (entityBody.getBoundingHeight() / 2);
            }
            default -> { // up
                bulletX = entityCentralPos.getX() - (attackHeight / 4);
                bulletY = entityCentralPos.getY() - (entityBody.getBoundingHeight() / 2) - attackWidth;
            }

        }

        return new Vector2D(bulletX, bulletY);
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
    public static double getMeleeRay(final Vector2D entityPos, final Vector2D enemyCentralPos,
                                  final Vector2D playerPos, final Vector2D playerCentralPos) {
        return entityPos.distance(enemyCentralPos) + playerPos.distance(playerCentralPos);
    }
}
