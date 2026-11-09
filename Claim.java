package campuslostandfound;

import java.sql.Timestamp;

/**
 * A claim made by a student on an item.
 * Maps to the claims table: claim_id, item_id, claimant_id, message, status, claim_date.
 *
 * Demonstrates: Classes and Objects, Encapsulation (private fields + validation
 * in setters), Inheritance (LostItemClaim and FoundItemClaim extend this class),
 * Polymorphism (subclasses override getMinMessageLength and getClaimSummary).
 */
public class Claim {
    private int claimId;
    private int itemId;
    private int claimantId;
    private String message;
    private ClaimStatus status;
    private Timestamp claimDate;

    public Claim() {
        this.status = ClaimStatus.PENDING;
    }

    /** Used when a student submits a new claim (validated). */
    public Claim(int itemId, int claimantId, String message) {
        setItemId(itemId);
        setClaimantId(claimantId);
        setMessage(message);
        this.status = ClaimStatus.PENDING;
    }

    /** Used by ClaimDAO when loading a row from MySQL (data already trusted). */
    public Claim(int claimId, int itemId, int claimantId, String message,
                 String status, Timestamp claimDate) {
        this.claimId = claimId;
        this.itemId = itemId;
        this.claimantId = claimantId;
        this.message = message;
        this.status = ClaimStatus.fromString(status);
        this.claimDate = claimDate;
    }

    // ---------- Polymorphic behaviour (overridden in subclasses) ----------

    /** Minimum length of the claim message. Subclasses can demand more detail. */
    public int getMinMessageLength() {
        return 10;
    }

    /** One-line description of this kind of claim. */
    public String getClaimSummary() {
        return "Claim #" + claimId + " on item #" + itemId + " by user #" + claimantId;
    }

    // ---------- Getters and setters (with validation) ----------

    public int getClaimId() { return claimId; }
    public void setClaimId(int claimId) { this.claimId = claimId; }

    public int getItemId() { return itemId; }
    public void setItemId(int itemId) {
        if (itemId <= 0) throw new InvalidClaimException("Invalid item id.");
        this.itemId = itemId;
    }

    public int getClaimantId() { return claimantId; }
    public void setClaimantId(int claimantId) {
        if (claimantId <= 0) throw new InvalidClaimException("Invalid user id.");
        this.claimantId = claimantId;
    }

    public String getMessage() { return message; }
    public void setMessage(String message) {
        if (message == null || message.trim().isEmpty()) {
            throw new InvalidClaimException("Please describe why this item is yours.");
        }
        this.message = message.trim();
    }

    public ClaimStatus getStatus() { return status; }
    public void setStatus(ClaimStatus status) { this.status = status; }

    public boolean isPending() { return status == ClaimStatus.PENDING; }

    public Timestamp getClaimDate() { return claimDate; }
    public void setClaimDate(Timestamp claimDate) { this.claimDate = claimDate; }

    @Override
    public String toString() {
        return "Claim #" + claimId + " [item=" + itemId + ", claimant=" + claimantId
                + ", status=" + status + "]";
    }
}
