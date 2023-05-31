package dimhol.logic.util;

import org.locationtech.jts.math.Vector2D;

/**
 * A util to get string representation of the 4 versors.
 */
public class DirectionUtil {

    /**
     * Gets the string representation of the given vector.
     *
     * @param vec the vector to represent as string
     * @return a string representing the vector
     */
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
        else {
            throw new IllegalStateException("Can't return a string representation of a " +
                    "vector that is not one of the four versors");
        }
    }

}
