package campuslostandfound;

import javax.swing.*;
import java.awt.*;

public class Dashboard extends JFrame {

    private JButton addItemButton;
    private JButton searchItemButton;
    private JButton viewItemsButton;
    private JButton claimItemButton;
    private JButton exitButton;

    public Dashboard() {

        setTitle("Campus Lost & Found Management System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(5, 1, 10, 10));
        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(30, 80, 30, 80)
        );

        JLabel heading = new JLabel(
                "Campus Lost & Found Management System",
                SwingConstants.CENTER
        );

        heading.setFont(new Font("Arial", Font.BOLD, 20));

        addItemButton = new JButton("Add Item");
        searchItemButton = new JButton("Search Item");
        viewItemsButton = new JButton("View Items");
        claimItemButton = new JButton("Claim Item");
        exitButton = new JButton("Exit");

        addItemButton.addActionListener(e -> {
            ItemForm itemForm = new ItemForm();
            itemForm.setVisible(true);
        });

        searchItemButton.addActionListener(e -> {
            SearchItems searchItems = new SearchItems();
            searchItems.setVisible(true);
        });

        viewItemsButton.addActionListener(e -> {
            ViewItems viewItems = new ViewItems();
            viewItems.setVisible(true);
        });

        claimItemButton.addActionListener(e -> {
            ClaimForm claimForm = new ClaimForm();
            claimForm.setVisible(true);
        });

        exitButton.addActionListener(e -> {
            System.exit(0);
        });

        mainPanel.add(addItemButton);
        mainPanel.add(searchItemButton);
        mainPanel.add(viewItemsButton);
        mainPanel.add(claimItemButton);
        mainPanel.add(exitButton);

        add(heading, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
    }
}