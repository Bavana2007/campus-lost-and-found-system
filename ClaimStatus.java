package campuslostandfound;

/**
 * Allowed states of a claim.
 * Demonstrates: Enum (type safety), Encapsulation.
 * toString() returns the lowercase value stored in MySQL, so tables/labels
 * that print the status still show "pending", "approved", "rejected".
 */
public enum ClaimStatus {
    PENDING("pending"),
    APPROVED("approved"),
    REJECTED("rejected");

    private final String dbValue;

    ClaimStatus(String dbValue) {
        this.dbValue = dbValue;
    }

    public String getDbValue() {
        return dbValue;
    }

    /** Convert the String stored in the database into the enum. */
    public static ClaimStatus fromString(String value) {
        for (ClaimStatus s : values()) {
            if (s.dbValue.equalsIgnoreCase(value)) return s;
        }
        throw new IllegalArgumentException("Unknown claim status: " + value);
    }

    @Override
    public String toString() {
        return dbValue;
    }
}
