package dimhol.logic.ai;

import dimhol.logic.collision.BodyShape;
import dimhol.logic.util.DirectionUtil;
import org.locationtech.jts.math.Vector2D;

public class AttackUtil {

    /**
     * This method is used to know in which direction the player is located.
     * @param playerCentralPos is the central player position
     * @param enemyCentralPos is the central enemy position
     * @return the direction
     */
    public static Vector2D getPlayerDirection(Vector2D playerCentralPos, Vector2D enemyCentralPos) {
        double y1 = playerCentralPos.getY();
        double y2 = enemyCentralPos.getY();
        double x1 = playerCentralPos.getX();
        double x2 = enemyCentralPos.getX();
        double m = (y1 - y2) / (x1 - x2);
        var angle = (Math.atan(m) * 180/Math.PI);
        if (angle > -45 && angle < 45) {
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
    public static Vector2D getAttackPos(Vector2D dir, Vector2D entityCentralPos, BodyShape entityBody, double attackWidth, double attackHeight) {
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
    public static int getMeleeRay(Vector2D entityPos, Vector2D enemyCentralPos, Vector2D playerPos, Vector2D playerCentralPos) {
        return (int) (entityPos.distance(enemyCentralPos) + playerPos.distance(playerCentralPos));
    }
}
