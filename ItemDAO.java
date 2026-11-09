
package campuslostandfound;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {

    // 1. ADD ITEM
    public boolean addItem(Item item) throws SQLException {

        String sql = "INSERT INTO items "
                + "(title, description, category, location, "
                + "date_reported, status, reported_by) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        String status = item.getItemType().toLowerCase();

        if (!status.equals("lost") && !status.equals("found")) {
            throw new IllegalArgumentException("Invalid item type");
        }

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, item.getTitle());
            ps.setString(2, item.getDescription());
            ps.setString(3, item.getCategory());
            ps.setString(4, item.getLocation());
            ps.setDate(5, Date.valueOf(item.getDateReported()));
            ps.setString(6, status);
            ps.setInt(7, item.getReportedBy());

            return ps.executeUpdate() > 0;
        }
    }

    // 2. VIEW ALL ITEMS
    public List<Item> viewItems() throws SQLException {

        List<Item> items = new ArrayList<>();

        String sql = "SELECT * FROM items ORDER BY item_id";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                items.add(mapItem(rs));
            }
        }

        return items;
    }

    // 3. UPDATE ITEM
    public boolean updateItem(Item item) throws SQLException {

        String sql = "UPDATE items SET title=?, description=?, "
                + "category=?, location=?, date_reported=? "
                + "WHERE item_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, item.getTitle());
            ps.setString(2, item.getDescription());
            ps.setString(3, item.getCategory());
            ps.setString(4, item.getLocation());
            ps.setDate(5, Date.valueOf(item.getDateReported()));
            ps.setInt(6, item.getItemId());

            return ps.executeUpdate() > 0;
        }
    }

    // 4. DELETE ITEM
    public boolean deleteItem(int itemId) throws SQLException {

        String sql = "DELETE FROM items WHERE item_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, itemId);

            return ps.executeUpdate() > 0;
        }
    }

    // 5. SEARCH ITEMS BY KEYWORD
    public List<Item> searchItems(String keyword) throws SQLException {

        List<Item> items = new ArrayList<>();

        String sql = "SELECT * FROM items "
                + "WHERE title LIKE ? "
                + "OR description LIKE ? "
                + "OR category LIKE ? "
                + "OR location LIKE ? "
                + "ORDER BY item_id";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            String search = "%" + keyword + "%";

            ps.setString(1, search);
            ps.setString(2, search);
            ps.setString(3, search);
            ps.setString(4, search);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    items.add(mapItem(rs));
                }
            }
        }

        return items;
    }

    // 6. FILTER ITEMS BY STATUS
    public List<Item> filterByStatus(String status)
            throws SQLException {

        List<Item> items = new ArrayList<>();

        String sql = "SELECT * FROM items WHERE status=? "
                + "ORDER BY item_id";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status.toLowerCase());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    items.add(mapItem(rs));
                }
            }
        }

        return items;
    }

    // CONVERT DATABASE RECORD INTO JAVA OBJECT
    private Item mapItem(ResultSet rs) throws SQLException {

        int id = rs.getInt("item_id");
        String title = rs.getString("title");
        String description = rs.getString("description");
        String category = rs.getString("category");
        String location = rs.getString("location");
        String date = rs.getDate("date_reported").toString();
        String status = rs.getString("status");
        int reportedBy = rs.getInt("reported_by");

        if (status.equalsIgnoreCase("lost")) {
            return new LostItem(id, title, description,
                    category, location, date, status, reportedBy);
        }

        if (status.equalsIgnoreCase("found")) {
            return new FoundItem(id, title, description,
                    category, location, date, status, reportedBy);
        }

        // Original type of a claimed item is not stored
        return new ClaimedItem(id, title, description,
                category, location, date, status, reportedBy);
    }

    // Represents a claimed item when its original type is unknown
    private static class ClaimedItem extends Item {

        public ClaimedItem(int id, String title, String description,
                String category, String location, String date,
                String status, int reportedBy) {

            super(id, title, description, category,
                    location, date, status, reportedBy);
        }

        @Override
        public String getItemType() {
            return "CLAIMED (original type unknown)";
        }
    }
}
