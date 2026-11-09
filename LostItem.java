
package campuslostandfound;

public class LostItem extends Item {

    public LostItem(int itemId, String title, String description,
                    String category, String location,
                    String dateReported, String status, int reportedBy) {

        super(itemId, title, description, category,
              location, dateReported, status, reportedBy);
    }

    @Override
    public String getItemType() {
        return "LOST";
    }

    @Override
    public void displayDetails() {
        System.out.println("----- LOST ITEM DETAILS -----");
        super.displayDetails();
    }
}
