package dimhol.gamelevels;

/**
 * Custom exception thrown to indicate invalid entity dimensions in a game level.
 */
public class InvalidEntityDimensionsException extends RuntimeException {
    /**
     * Constructs a new InvalidEntityDimensionsException with the specified detail message.
     *
     * @param message the detail message
     */
    public InvalidEntityDimensionsException(final String message) {
        super(message);
    }
}
