package campuslostandfound;

/** Thrown when a student already has a pending claim on the same item. */
public class DuplicateClaimException extends ClaimException {
    public DuplicateClaimException() {
        super("You already have a pending claim on this item.");
    }
}
