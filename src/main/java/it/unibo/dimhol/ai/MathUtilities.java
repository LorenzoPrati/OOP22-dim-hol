package it.unibo.dimhol.ai;

import it.unibo.dimhol.components.BodyComponent;
import it.unibo.dimhol.components.PositionComponent;
import org.locationtech.jts.math.Vector2D;

public class MathUtilities {

    public static double getDistance(Vector2D pos, Vector2D pos1) {
        return pos.distance(pos1);
    }

    public static Vector2D getCentralPosition(PositionComponent entityPos, BodyComponent entityBody) {
        return new Vector2D(entityPos.getPos().getX() + (entityBody.getBs().getBoundingWidth() / 2),
                entityPos.getPos().getY() + (entityBody.getBs().getBoundingHeight() / 2));
    }

    public static double getAngle(Vector2D playerCentralPos, Vector2D enemyCentralPos) {
        double y1 = playerCentralPos.getY();
        double y2 = enemyCentralPos.getY();
        double x1 = playerCentralPos.getX();
        double x2 = enemyCentralPos.getX();
        double m = (y1 - y2) / (x1 - x2);
        return (Math.atan(m) * 180/Math.PI);
    }
}
