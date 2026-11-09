package campuslostandfound;

import java.sql.Timestamp;

/**
 * Represents a claim made by a student on a lost/found item.
 * Maps to the claims table: claim_id, item_id, claimant_id, message, status, claim_date.
 * Demonstrates: Classes and Objects, Encapsulation.
 */
public class Claim {
    // Private fields (encapsulation)
    private int claimId;
    private int itemId;
    private int claimantId;
    private String message;
    private String status; // pending, approved, rejected
    private Timestamp claimDate;

    public Claim() {
        this.status = "pending";
    }

    public Claim(int itemId, int claimantId, String message) {
        this.itemId = itemId;
        this.claimantId = claimantId;
        this.message = message;
        this.status = "pending";
    }

    public Claim(int claimId, int itemId, int claimantId, String message,
                 String status, Timestamp claimDate) {
        this.claimId = claimId;
        this.itemId = itemId;
        this.claimantId = claimantId;
        this.message = message;
        this.status = status;
        this.claimDate = claimDate;
    }

    // Getters and setters
    public int getClaimId() { return claimId; }
    public void setClaimId(int claimId) { this.claimId = claimId; }

    public int getItemId() { return itemId; }
    public void setItemId(int itemId) { this.itemId = itemId; }

    public int getClaimantId() { return claimantId; }
    public void setClaimantId(int claimantId) { this.claimantId = claimantId; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Timestamp getClaimDate() { return claimDate; }
    public void setClaimDate(Timestamp claimDate) { this.claimDate = claimDate; }

    @Override
    public String toString() {
        return "Claim #" + claimId + " [item=" + itemId + ", claimant=" + claimantId
                + ", status=" + status + "]";
    }
}
