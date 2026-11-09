package campuslostandfound;

/** Thrown when someone tries to claim or approve a claim on an item that is already claimed. */
public class ItemAlreadyClaimedException extends ClaimException {
    public ItemAlreadyClaimedException() {
        super("This item has already been claimed.");
    }
}
