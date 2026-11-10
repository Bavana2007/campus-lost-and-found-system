
package campuslostandfound;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ViewItems extends JFrame {

    private JTable itemTable;
    private DefaultTableModel tableModel;

    private ItemService itemService;

    public ViewItems() {

        itemService = new ItemServiceImpl();

        setTitle("View Lost and Found Items");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columns = {
            "Item ID",
            "Item Name",
            "Category",
            "Location",
            "Date",
            "Report Type",
            "Status"
        };

        tableModel = new DefaultTableModel(columns, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        itemTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(itemTable);

        add(scrollPane, BorderLayout.CENTER);

        JButton refreshButton = new JButton("Refresh");

        refreshButton.addActionListener(e -> loadItems());

        add(refreshButton, BorderLayout.SOUTH);

        // Load items when the window opens
        loadItems();
    }

    private void loadItems() {

        tableModel.setRowCount(0);

        List<Item> items = itemService.getAllItems();

        if (items == null || items.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No items found in the database.",
                    "View Items",
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
                item.getItemType(),
                item.getStatus()
            };

            tableModel.addRow(row);
        }
    }
}
