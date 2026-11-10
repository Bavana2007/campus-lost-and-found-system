package campuslostandfound;

import javax.swing.SwingUtilities;

public class CampusLostAndFound {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            Dashboard dashboard = new Dashboard();

            dashboard.setVisible(true);

        });

    }
}