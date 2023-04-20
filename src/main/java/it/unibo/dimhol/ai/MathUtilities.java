package it.unibo.dimhol.ai;

import it.unibo.dimhol.commons.shapes.BodyShape;
import it.unibo.dimhol.commons.shapes.RectBodyShape;
import org.locationtech.jts.math.Vector2D;

public class MathUtilities {

    public static double getDistance(Vector2D pos, Vector2D pos1) {
        return pos.distance(pos1);
    }

    public static Vector2D getCentralPosition(Vector2D entityPos, BodyShape entityBody) {
        return new Vector2D(entityPos.getX() + (entityBody.getBoundingWidth() / 2),
                entityPos.getY() + (entityBody.getBoundingHeight() / 2));
    }

    public static double getAngle(Vector2D playerCentralPos, Vector2D enemyCentralPos) {
        double y1 = playerCentralPos.getY();
        double y2 = enemyCentralPos.getY();
        double x1 = playerCentralPos.getX();
        double x2 = enemyCentralPos.getX();
        double m = (y1 - y2) / (x1 - x2);
        return (Math.atan(m) * 180/Math.PI);
    }

    public static int getVisionZone(double angle, Vector2D playerPos, Vector2D enemyPos) {
        if (angle > -45 && angle < 45) {
            if (playerPos.getX() > enemyPos.getX()) {
                // right
                return 1;
            } else {
                //left
                return 2;
            }
        } else {
            if (playerPos.getY() > enemyPos.getY()) {
                //down
                return 3;
            } else {
                //up
                return 4;
            }
        }
    }

    public static Vector2D setAttackPosition(double dirX, double dirY, Vector2D entityPos, BodyShape entityBody, double attackWidth, double attackHeight) {
        var enemyHeight = entityBody.getBoundingHeight();
        var enemyWidth = entityBody.getBoundingWidth();
        var centralEntityPos = getCentralPosition(entityPos, entityBody);
        double bulletX;
        double bulletY;

        if (dirX == 1) {
            bulletX = centralEntityPos.getX() + (enemyWidth / 2);
            bulletY = centralEntityPos.getY() - (attackWidth / 2);
        } else if (dirX == -1) {
            bulletX = centralEntityPos.getX() - (enemyWidth / 2) - attackHeight;
            bulletY = centralEntityPos.getY() - (attackWidth / 2);
        } else if (dirY == 1) {
            bulletX = centralEntityPos.getX() - (attackHeight / 2);
            bulletY = centralEntityPos.getY() + (enemyHeight / 2);
        } else {    // dirY = -1
            bulletX = centralEntityPos.getX() - (attackHeight / 2);
            bulletY = centralEntityPos.getY() - (enemyHeight / 2) - attackWidth;
        }

        return new Vector2D(bulletX, bulletY);
    }

    public static BodyShape rotate90GradeRect(BodyShape entityBody){
        return new RectBodyShape(entityBody.getBoundingHeight(), entityBody.getBoundingWidth());
    }

}
