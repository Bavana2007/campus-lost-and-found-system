package campuslostandfound;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for the claims table.
 * All SQL for claims lives here, never in Swing forms.
 * Demonstrates: Abstraction, Encapsulation.
 *
 * Uses DBConnection.getConnection() from M1.
 * Tables: claims(claim_id, item_id, claimant_id, message, status, claim_date)
 *         items(item_id, ..., status)  status = 'lost' | 'found' | 'claimed'
 */
public class ClaimDAO {

    /** Insert a new claim. Returns true on success. */
    public boolean addClaim(Claim claim) {
        String sql = "INSERT INTO claims (item_id, claimant_id, message, status) VALUES (?, ?, ?, 'pending')";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, claim.getItemId());
            ps.setInt(2, claim.getClaimantId());
            ps.setString(3, claim.getMessage());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** All claims made by one student (for "view my claim status"). */
    public List<Claim> getClaimsByClaimant(int claimantId) {
        List<Claim> list = new ArrayList<>();
        String sql = "SELECT * FROM claims WHERE claimant_id = ? ORDER BY claim_date DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, claimantId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /** All claims with a given status, e.g. "pending" (for the admin screen). */
    public List<Claim> getClaimsByStatus(String status) {
        List<Claim> list = new ArrayList<>();
        String sql = "SELECT * FROM claims WHERE status = ? ORDER BY claim_date ASC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, status);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /** Every claim (admin overview). */
    public List<Claim> getAllClaims() {
        List<Claim> list = new ArrayList<>();
        String sql = "SELECT * FROM claims ORDER BY claim_date DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /** Get a single claim by id, or null if not found. */
    public Claim getClaimById(int claimId) {
        String sql = "SELECT * FROM claims WHERE claim_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, claimId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /** True if this student already has a pending claim on this item. */
    public boolean hasPendingClaim(int itemId, int claimantId) {
        String sql = "SELECT 1 FROM claims WHERE item_id = ? AND claimant_id = ? AND status = 'pending'";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, itemId);
            ps.setInt(2, claimantId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Approve a claim and mark the item as 'claimed' in one transaction,
     * so either both updates happen or neither does.
     */
    public boolean approveClaim(int claimId, int itemId) {
        String claimSql = "UPDATE claims SET status = 'approved' WHERE claim_id = ?";
        String itemSql = "UPDATE items SET status = 'claimed' WHERE item_id = ?";
        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);
            try (PreparedStatement ps1 = con.prepareStatement(claimSql);
                 PreparedStatement ps2 = con.prepareStatement(itemSql)) {
                ps1.setInt(1, claimId);
                ps2.setInt(1, itemId);
                int a = ps1.executeUpdate();
                int b = ps2.executeUpdate();
                if (a > 0 && b > 0) {
                    con.commit();
                    return true;
                }
                con.rollback();
                return false;
            } catch (SQLException e) {
                con.rollback();
                e.printStackTrace();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Reject a claim. The item status stays unchanged. */
    public boolean rejectClaim(int claimId) {
        String sql = "UPDATE claims SET status = 'rejected' WHERE claim_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, claimId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ---------- helper ----------

    private Claim mapRow(ResultSet rs) throws SQLException {
        return new Claim(
                rs.getInt("claim_id"),
                rs.getInt("item_id"),
                rs.getInt("claimant_id"),
                rs.getString("message"),
                rs.getString("status"),
                rs.getTimestamp("claim_date"));
    }
}
