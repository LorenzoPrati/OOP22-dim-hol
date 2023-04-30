package it.unibo.dimhol.commons;

import org.locationtech.jts.math.Vector2D;

public class DirectionUtil {

    public static String getStringFromVec(final Vector2D vec) {
        if (vec.equals(new Vector2D(0,-1))) {
            return "up";
        } else if (vec.equals(new Vector2D(0,1))) {
            return "down";
        } else if (vec.equals(new Vector2D(-1,0))) {
            return "left";
        } else if (vec.equals(new Vector2D(1,0))) {
            return "right";
        }
        return "";
    }

}
