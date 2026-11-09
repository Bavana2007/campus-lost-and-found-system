package campuslostandfound;

import java.util.List;

/**
 * Business logic for claims. The UI calls this class, and this class
 * calls ClaimDAO. Validation lives here, SQL lives in the DAO.
 * Demonstrates: Abstraction, Encapsulation.
 */
public class ClaimService {

    private final ClaimDAO claimDAO = new ClaimDAO();

    /** Student submits a claim. Returns a message to show in the UI. */
    public String submitClaim(int itemId, int userId, String description) {
        if (description == null || description.trim().isEmpty()) {
            return "Please describe why this item is yours.";
        }
        if (description.trim().length() < 10) {
            return "Description is too short. Add identifying details.";
        }
        if (claimDAO.hasPendingClaim(itemId, userId)) {
            return "You already have a pending claim on this item.";
        }
        Claim claim = new Claim(itemId, userId, description.trim());
        return claimDAO.addClaim(claim)
                ? "Claim submitted successfully. Status: PENDING."
                : "Could not submit claim. Please try again.";
    }

    /** Student views their own claims and statuses. */
    public List<Claim> viewMyClaims(int userId) {
        return claimDAO.getClaimsByUser(userId);
    }

    /** Admin: list claims waiting for a decision. */
    public List<Claim> getPendingClaims() {
        return claimDAO.getClaimsByStatus("PENDING");
    }

    /** Admin: list every claim. */
    public List<Claim> getAllClaims() {
        return claimDAO.getAllClaims();
    }

    /** Admin approves a claim. The item status is updated to CLAIMED. */
    public String approveClaim(int claimId) {
        Claim claim = claimDAO.getClaimById(claimId);
        if (claim == null) return "Claim not found.";
        if (!"PENDING".equals(claim.getStatus())) {
            return "This claim has already been " + claim.getStatus().toLowerCase() + ".";
        }
        return claimDAO.approveClaim(claimId, claim.getItemId())
                ? "Claim approved. Item marked as CLAIMED."
                : "Could not approve claim.";
    }

    /** Admin rejects a claim. */
    public String rejectClaim(int claimId) {
        Claim claim = claimDAO.getClaimById(claimId);
        if (claim == null) return "Claim not found.";
        if (!"PENDING".equals(claim.getStatus())) {
            return "This claim has already been " + claim.getStatus().toLowerCase() + ".";
        }
        return claimDAO.rejectClaim(claimId)
                ? "Claim rejected."
                : "Could not reject claim.";
    }
}
