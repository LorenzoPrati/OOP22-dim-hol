package dimhol.gamelevels.map;

/**
 * Exception that is thrown when an error occurs during map loading.
 * It is a subclass of the RuntimeException class, indicating that it is an unchecked exception.
 */
public class MapLoadingException extends RuntimeException {
    /**
     * Constructs a new MapLoadingException with the specified error message and cause.
     *
     * @param message The error message that describes the cause of the exception.
     * @param cause   The cause of the exception.
     */
    public MapLoadingException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
