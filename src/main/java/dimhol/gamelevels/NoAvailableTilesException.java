package dimhol.gamelevels;

/**
 * Custom exception thrown when there are no available tiles in a game level.
 */
public class NoAvailableTilesException extends RuntimeException {
    /**
     * Constructs a new NoAvailableTilesException with the specified detail message.
     *
     * @param message the detail message
     */
    public NoAvailableTilesException(String message) {
        super(message);
    }
}
