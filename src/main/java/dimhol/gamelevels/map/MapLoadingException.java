package dimhol.gamelevels.map;

/**
 * Exception class for map loading errors.
 */
public class MapLoadingException extends RuntimeException {
    public MapLoadingException(String message, Throwable cause) {
        super(message, cause);
    }
}
