package dimhol.gamelevels;

import java.io.Serial;
import java.io.Serializable;

/**
 * Custom exception thrown to indicate invalid entity dimensions in a game level.
 */
public class InvalidEntityDimensionsException extends RuntimeException implements Serializable {
    @Serial
    private static final long serialVersionUID = 8673769077352500431L;

    /**
     * Constructs a new InvalidEntityDimensionsException with the specified detail message.
     *
     * @param message the detail message
     */
    public InvalidEntityDimensionsException(final String message) {
        super(message);
    }
}
