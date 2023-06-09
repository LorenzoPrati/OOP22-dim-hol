package dimhol.gamelevels;

import java.io.Serial;

/**
 * Exception class used for entity generation errors.
 */
public class EntityGenerationException extends Exception {

    @Serial
    private static final long serialVersionUID = -8774957188493661443L;

    /**
     * Constructs an EntityGenerationException with the specified error message and cause.
     *
     * @param message The error message.
     * @param cause   The cause of the exception.
     */
    public EntityGenerationException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
