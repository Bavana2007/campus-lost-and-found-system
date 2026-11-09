package campuslostandfound;

/**
 * Claim on an item that was reported LOST: the claimant says they found it
 * or know where it is.
 * Demonstrates: Inheritance and Polymorphism (method overriding).
 */
public class LostItemClaim extends Claim {

    public LostItemClaim(int itemId, int claimantId, String message) {
        super(itemId, claimantId, message);
    }

    @Override
    public int getMinMessageLength() {
        return 10;
    }

    @Override
    public String getClaimSummary() {
        return "Found-it report: user #" + getClaimantId()
                + " says they found lost item #" + getItemId();
    }
}
