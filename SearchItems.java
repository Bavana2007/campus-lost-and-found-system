package campuslostandfound;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class SearchItems extends JFrame {

    private JTextField searchField;
    private JButton searchButton;
    private JButton backButton;

    private JTable itemTable;
    private DefaultTableModel tableModel;

    private ItemService itemService;

    public SearchItems() {

        itemService = new ItemServiceImpl();

        setTitle("Search Lost and Found Items");
        setSize(800, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));

        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout());

        JLabel searchLabel = new JLabel("Enter Item Name:");

        searchField = new JTextField(20);

        searchButton = new JButton("Search");

        backButton = new JButton("Back");

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(backButton);

        // Table columns
        String[] columns = {
            "Item ID",
            "Item Name",
            "Category",
            "Location",
            "Date Reported",
            "Status",
            "Reported By"
        };

        tableModel = new DefaultTableModel(columns, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        itemTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(itemTable);

        // Search button action
        searchButton.addActionListener(e -> searchItems());

        // Enter key action
        searchField.addActionListener(e -> searchItems());

        // Back button action
        backButton.addActionListener(e -> {
            dispose();
        });

        mainPanel.add(searchPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);

        // Display all items initially
        searchItems();
    }

    private void searchItems() {

        String keyword = searchField.getText().trim();

        tableModel.setRowCount(0);

        List<Item> items = itemService.searchItems(keyword);

        if (items == null || items.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No items found.",
                    "Search Result",
                    JOptionPane.INFORMATION_MESSAGE
            );

            return;
        }

        for (Item item : items) {

            Object[] row = {
                item.getItemId(),
                item.getTitle(),
                item.getCategory(),
                item.getLocation(),
                item.getDateReported(),
                item.getStatus(),
                item.getReportedBy()
            };

            tableModel.addRow(row);
        }
    }
}