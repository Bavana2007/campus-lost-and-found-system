package campuslostandfound;

import java.util.List;

/**
 * Business logic for claims. Validation and rules live here, SQL lives in the DAO.
 * Public methods keep their old names and still return a message String,
 * so code written by other members does not need to change.
 *
 * Demonstrates: Abstraction (depends on the ClaimRepository interface),
 * Encapsulation, Polymorphism (a Claim variable holds a LostItemClaim or
 * FoundItemClaim), custom exceptions.
 */
public class ClaimService {

    private final ClaimRepository repo;

    public ClaimService() {
        this(new ClaimDAO());
    }

    /** Lets tests or other storage classes be plugged in. */
    public ClaimService(ClaimRepository repo) {
        this.repo = repo;
    }

    /** Student submits a claim. Returns a message to show the user. */
    public String submitClaim(int itemId, int claimantId, String message) {
        try {
            String itemStatus = repo.getItemStatus(itemId);
            if (itemStatus == null) throw new InvalidClaimException("Item not found.");
            if ("claimed".equals(itemStatus)) throw new ItemAlreadyClaimedException();

            Claim claim = createClaim(itemStatus, itemId, claimantId, message);
            if (claim.getMessage().length() < claim.getMinMessageLength()) {
                throw new InvalidClaimException("Message is too short. Add identifying details ("
                        + claim.getMinMessageLength() + " characters minimum).");
            }
            if (repo.hasPendingClaim(itemId, claimantId)) throw new DuplicateClaimException();

            return repo.addClaim(claim)
                    ? "Claim submitted successfully. Status: pending."
                    : "Could not submit claim. Please try again.";
        } catch (ClaimException e) {
            return e.getMessage();
        }
    }

    /** Polymorphism: the right subclass is chosen from the item's status. */
    private Claim createClaim(String itemStatus, int itemId, int claimantId, String message) {
        if ("found".equals(itemStatus)) {
            return new FoundItemClaim(itemId, claimantId, message);
        }
        return new LostItemClaim(itemId, claimantId, message);
    }

    /** Student views their own claims and statuses. */
    public List<Claim> viewMyClaims(int claimantId) {
        return repo.getClaimsByClaimant(claimantId);
    }

    /** Admin: claims waiting for a decision. */
    public List<Claim> getPendingClaims() {
        return repo.getClaimsByStatus(ClaimStatus.PENDING);
    }

    /** Admin: every claim. */
    public List<Claim> getAllClaims() {
        return repo.getAllClaims();
    }

    /** Admin approves a claim: item becomes 'claimed', other pending claims on it are rejected. */
    public String approveClaim(int claimId) {
        try {
            Claim claim = loadPendingClaim(claimId);
            if ("claimed".equals(repo.getItemStatus(claim.getItemId()))) {
                throw new ItemAlreadyClaimedException();
            }
            return repo.approveClaim(claimId, claim.getItemId())
                    ? "Claim approved. Item marked as claimed."
                    : "Could not approve claim.";
        } catch (ClaimException e) {
            return e.getMessage();
        }
    }

    /** Admin rejects a claim. */
    public String rejectClaim(int claimId) {
        try {
            loadPendingClaim(claimId);
            return repo.rejectClaim(claimId)
                    ? "Claim rejected."
                    : "Could not reject claim.";
        } catch (ClaimException e) {
            return e.getMessage();
        }
    }

    /** Finds a claim and makes sure it is still pending. */
    private Claim loadPendingClaim(int claimId) {
        Claim claim = repo.getClaimById(claimId);
        if (claim == null) throw new InvalidClaimException("Claim not found.");
        if (!claim.isPending()) {
            throw new InvalidClaimException("This claim has already been " + claim.getStatus() + ".");
        }
        return claim;
    }
}
