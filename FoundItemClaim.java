package campuslostandfound;

/**
 * Claim on an item that was reported FOUND: the claimant says it is theirs,
 * so stronger proof of ownership is required.
 * Demonstrates: Inheritance and Polymorphism (method overriding).
 */
public class FoundItemClaim extends Claim {

    public FoundItemClaim(int itemId, int claimantId, String message) {
        super(itemId, claimantId, message);
    }

    @Override
    public int getMinMessageLength() {
        return 20; // ownership needs more identifying detail
    }

    @Override
    public String getClaimSummary() {
        return "Ownership claim: user #" + getClaimantId()
                + " says found item #" + getItemId() + " belongs to them";
    }
}
