package campuslostandfound;

/**
 * Base class of all claim-related errors.
 * Demonstrates: Inheritance (the three exceptions below extend this).
 */
public class ClaimException extends RuntimeException {
    public ClaimException(String message) {
        super(message);
    }
}
