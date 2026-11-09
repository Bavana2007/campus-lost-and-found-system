package campuslostandfound;

import java.sql.Timestamp;

/**
 * Represents a claim made by a student on a lost/found item.
 * Demonstrates: Classes and Objects, Encapsulation.
 */
public class Claim {
    // Private fields (encapsulation)
    private int claimId;
    private int itemId;
    private int userId;
    private String description;
    private String status; // PENDING, APPROVED, REJECTED
    private Timestamp claimDate;

    public Claim() {
        this.status = "PENDING";
    }

    public Claim(int itemId, int userId, String description) {
        this.itemId = itemId;
        this.userId = userId;
        this.description = description;
        this.status = "PENDING";
    }

    public Claim(int claimId, int itemId, int userId, String description,
                 String status, Timestamp claimDate) {
        this.claimId = claimId;
        this.itemId = itemId;
        this.userId = userId;
        this.description = description;
        this.status = status;
        this.claimDate = claimDate;
    }

    // Getters and setters
    public int getClaimId() { return claimId; }
    public void setClaimId(int claimId) { this.claimId = claimId; }

    public int getItemId() { return itemId; }
    public void setItemId(int itemId) { this.itemId = itemId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Timestamp getClaimDate() { return claimDate; }
    public void setClaimDate(Timestamp claimDate) { this.claimDate = claimDate; }

    @Override
    public String toString() {
        return "Claim #" + claimId + " [item=" + itemId + ", user=" + userId
                + ", status=" + status + "]";
    }
}
