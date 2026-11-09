package campuslostandfound;

import java.util.List;

/**
 * Contract for claim data access.
 * ClaimService depends on this interface, not on ClaimDAO directly, so the
 * storage (MySQL today) can be swapped or faked for testing.
 * Demonstrates: Abstraction (interface).
 */
public interface ClaimRepository {
    boolean addClaim(Claim claim);
    List<Claim> getClaimsByClaimant(int claimantId);
    List<Claim> getClaimsByStatus(ClaimStatus status);
    List<Claim> getAllClaims();
    Claim getClaimById(int claimId);
    boolean hasPendingClaim(int itemId, int claimantId);

    /** Current status of an item ('lost', 'found', 'claimed') or null if it does not exist. */
    String getItemStatus(int itemId);

    /** Approve a claim, mark the item claimed, and reject other pending claims on it. */
    boolean approveClaim(int claimId, int itemId);

    boolean rejectClaim(int claimId);
}
