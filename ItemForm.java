
package campuslostandfound;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;

public class ItemForm extends JFrame {

    private JTextField nameField;
    private JTextField dateField;
    private JTextField locationField;
    private JTextField userIdField;

    private JComboBox<String> categoryBox;
    private JComboBox<String> reportTypeBox;

    private JTextArea descriptionArea;

    private JButton saveButton;
    private JButton clearButton;

    private final ItemService itemService;

    // Constructor
    public ItemForm() {

        itemService = new ItemServiceImpl();

        setTitle("Add Lost or Found Item");
        setSize(600, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Main panel
        JPanel panel = new JPanel(new GridBagLayout());

        panel.setBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        );

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel titleLabel = new JLabel(
                "Add Lost or Found Item",
                SwingConstants.CENTER
        );

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 22)
        );

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        panel.add(titleLabel, gbc);

        gbc.gridwidth = 1;

        // Item name
        gbc.gridx = 0;
        gbc.gridy = 1;

        panel.add(new JLabel("Item Name:"), gbc);

        nameField = new JTextField(20);

        gbc.gridx = 1;

        panel.add(nameField, gbc);

        // Category
        gbc.gridx = 0;
        gbc.gridy = 2;

        panel.add(new JLabel("Category:"), gbc);

        String[] categories = {
            "ID Card",
            "Books",
            "Keys",
            "Water Bottle",
            "Electronics",
            "Other"
        };

        categoryBox = new JComboBox<>(categories);

        gbc.gridx = 1;

        panel.add(categoryBox, gbc);

        // Location
        gbc.gridx = 0;
        gbc.gridy = 3;

        panel.add(new JLabel("Location:"), gbc);

        locationField = new JTextField(20);

        gbc.gridx = 1;

        panel.add(locationField, gbc);

        // Date
        gbc.gridx = 0;
        gbc.gridy = 4;

        panel.add(new JLabel("Date Reported:"), gbc);

        dateField = new JTextField(20);

        dateField.setToolTipText("Enter date as yyyy-MM-dd");

        gbc.gridx = 1;

        panel.add(dateField, gbc);

        // Report type
        gbc.gridx = 0;
        gbc.gridy = 5;

        panel.add(new JLabel("Report Type:"), gbc);

        String[] reportTypes = {
            "LOST",
            "FOUND"
        };

        reportTypeBox = new JComboBox<>(reportTypes);

        gbc.gridx = 1;

        panel.add(reportTypeBox, gbc);

        // User ID
        gbc.gridx = 0;
        gbc.gridy = 6;

        panel.add(new JLabel("Reported By (User ID):"), gbc);

        userIdField = new JTextField(20);

        gbc.gridx = 1;

        panel.add(userIdField, gbc);

        // Description
        gbc.gridx = 0;
        gbc.gridy = 7;

        panel.add(new JLabel("Description:"), gbc);

        descriptionArea = new JTextArea(4, 20);

        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);

        JScrollPane descriptionScroll =
                new JScrollPane(descriptionArea);

        gbc.gridx = 1;

        panel.add(descriptionScroll, gbc);

        // Buttons
        saveButton = new JButton("Save Item");
        clearButton = new JButton("Clear");

        JPanel buttonPanel = new JPanel();

        buttonPanel.add(saveButton);
        buttonPanel.add(clearButton);

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;

        panel.add(buttonPanel, gbc);

        // Save button action
        saveButton.addActionListener(e -> saveItem());

        // Clear button action
        clearButton.addActionListener(e -> clearForm());

        add(panel);
    }

    // Save item
    private void saveItem() {

        String name = nameField.getText().trim();

        String category =
                categoryBox.getSelectedItem().toString();

        String location =
                locationField.getText().trim();

        String date = dateField.getText().trim();

        String reportType =
                reportTypeBox.getSelectedItem().toString();

        String description =
                descriptionArea.getText().trim();

        String userIdText =
                userIdField.getText().trim();

        // Validate empty fields
        if (name.isEmpty()
                || location.isEmpty()
                || date.isEmpty()
                || userIdText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all required fields."
            );

            return;
        }

        // Validate date
        try {
            Date.valueOf(date);
        } catch (IllegalArgumentException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid date format.\n"
                    + "Please enter the date as yyyy-MM-dd."
            );

            return;
        }

        // Validate user ID
        int userId;

        try {
            userId = Integer.parseInt(userIdText);

            if (userId <= 0) {
                throw new NumberFormatException();
            }

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid positive User ID."
            );

            return;
        }

        // Create the correct item object
        Item item;

        if (reportType.equals("LOST")) {

            item = new LostItem(
                    0,
                    name,
                    description,
                    category,
                    location,
                    date,
                    "OPEN",
                    userId
            );

        } else {

            item = new FoundItem(
                    0,
                    name,
                    description,
                    category,
                    location,
                    date,
                    "OPEN",
                    userId
            );
        }

        // Save using the service layer
        boolean saved = itemService.addItem(item);

        if (saved) {

            JOptionPane.showMessageDialog(
                    this,
                    "Item saved successfully!"
            );

            clearForm();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to save item.\n"
                    + "Please check the database connection "
                    + "and User ID."
            );
        }
    }

    // Clear form
    private void clearForm() {

        nameField.setText("");
        locationField.setText("");
        dateField.setText("");
        userIdField.setText("");
        descriptionArea.setText("");

        categoryBox.setSelectedIndex(0);
        reportTypeBox.setSelectedIndex(0);
    }
}
