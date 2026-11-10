package campuslostandfound;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemServiceImpl implements ItemService {

    private final ItemDAO itemDAO;

    public ItemServiceImpl() {
        itemDAO = new ItemDAO();
    }

    @Override
    public boolean addItem(Item item) {
        try {
            return itemDAO.addItem(item);
        } catch (SQLException e) {
            System.out.println("Error adding item: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Item> getAllItems() {
        try {
            return itemDAO.viewItems();
        } catch (SQLException e) {
            System.out.println("Error retrieving items: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public List<Item> searchItems(String keyword) {
        try {
            return itemDAO.searchItems(keyword);
        } catch (SQLException e) {
            System.out.println("Error searching items: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}