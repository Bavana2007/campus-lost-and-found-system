package campuslostandfound;

/** Thrown when claim data is missing or invalid (empty message, bad id, claim not found). */
public class InvalidClaimException extends ClaimException {
    public InvalidClaimException(String message) {
        super(message);
    }
}
