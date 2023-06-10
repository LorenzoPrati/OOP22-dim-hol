package dimhol.levels.map;

import java.io.Serial;

/**
 * Exception that is thrown when an error occurs during map loading.
 * It is a subclass of the RuntimeException class, indicating that it is an unchecked exception.
 */
public class MapLoadingException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -351410835945198394L;

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
