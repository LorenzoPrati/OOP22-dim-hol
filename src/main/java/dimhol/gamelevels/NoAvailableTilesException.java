package dimhol.gamelevels;

import java.io.Serial;

/**
 * Custom exception thrown when there are no available tiles in a game level.
 */
public class NoAvailableTilesException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -3415571821734734803L;

    /**
     * Constructs a new NoAvailableTilesException with the specified detail message.
     *
     * @param message the detail message
     */
    public NoAvailableTilesException(final String message) {
        super(message);
    }
}
