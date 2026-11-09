
package campuslostandfound;

public class FoundItem extends Item {

    public FoundItem(int itemId, String title, String description,
                     String category, String location,
                     String dateReported, String status, int reportedBy) {

        super(itemId, title, description, category,
              location, dateReported, status, reportedBy);
    }

    @Override
    public String getItemType() {
        return "FOUND";
    }

    @Override
    public void displayDetails() {
        System.out.println("----- FOUND ITEM DETAILS -----");
        super.displayDetails();
    }
}
