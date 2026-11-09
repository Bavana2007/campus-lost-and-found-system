


package campuslostandfound;

import java.sql.SQLException;
import java.util.List;

public class ItemService {

    private final ItemDAO itemDAO;

    // Constructor
    public ItemService() {
        this.itemDAO = new ItemDAO();
    }

    // Validate item details
    public boolean validateItem(Item item) {

        if (item == null) {
            return false;
        }

        if (item.getTitle() == null ||
                item.getTitle().trim().isEmpty()) {
            return false;
        }

        if (item.getCategory() == null ||
                item.getCategory().trim().isEmpty()) {
            return false;
        }

        if (item.getLocation() == null ||
                item.getLocation().trim().isEmpty()) {
            return false;
        }

        if (item.getDateReported() == null ||
                item.getDateReported().trim().isEmpty()) {
            return false;
        }

        if (item.getReportedBy() <= 0) {
            return false;
        }

        String type = item.getItemType();

        return type.equals("LOST") || type.equals("FOUND");
    }

    // Add item
    public boolean addItem(Item item) throws SQLException {

        if (!validateItem(item)) {
            System.out.println("Invalid item details!");
            return false;
        }

        return itemDAO.addItem(item);
    }

    // View all items
    public List<Item> viewItems() throws SQLException {
        return itemDAO.viewItems();
    }

    // Update item
    public boolean updateItem(Item item) throws SQLException {

        if (!validateItem(item)) {
            System.out.println("Invalid item details!");
            return false;
        }

        if (item.getItemId() <= 0) {
            return false;
        }

        return itemDAO.updateItem(item);
    }

    // Delete item
    public boolean deleteItem(int itemId) throws SQLException {

        if (itemId <= 0) {
            return false;
        }

        return itemDAO.deleteItem(itemId);
    }

    // Search items
    public List<Item> searchItems(String keyword)
            throws SQLException {

        if (keyword == null || keyword.trim().isEmpty()) {
            return viewItems();
        }

        return itemDAO.searchItems(keyword.trim());
    }

    // Filter by status
    public List<Item> filterByStatus(String status)
            throws SQLException {

        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }

        String value = status.trim().toLowerCase();

        if (!value.equals("lost") &&
                !value.equals("found") &&
                !value.equals("claimed")) {

            throw new IllegalArgumentException("Invalid status");
        }

        return itemDAO.filterByStatus(value);
    }
}
