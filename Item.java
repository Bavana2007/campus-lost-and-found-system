
package campuslostandfound;

public abstract class Item {

    private int itemId;
    private String title;
    private String description;
    private String category;
    private String location;
    private String dateReported;
    private String status;
    private int reportedBy;

    // Constructor
    public Item(int itemId, String title, String description,
                String category, String location,
                String dateReported, String status, int reportedBy) {

        this.itemId = itemId;
        this.title = title;
        this.description = description;
        this.category = category;
        this.location = location;
        this.dateReported = dateReported;
        this.status = status;
        this.reportedBy = reportedBy;
    }

    // Getters and Setters
    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDateReported() {
        return dateReported;
    }

    public void setDateReported(String dateReported) {
        this.dateReported = dateReported;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getReportedBy() {
        return reportedBy;
    }

    public void setReportedBy(int reportedBy) {
        this.reportedBy = reportedBy;
    }

    // Abstract method
    public abstract String getItemType();

    // Display item details
    public void displayDetails() {
        System.out.println("Item ID: " + itemId);
        System.out.println("Title: " + title);
        System.out.println("Description: " + description);
        System.out.println("Category: " + category);
        System.out.println("Location: " + location);
        System.out.println("Date Reported: " + dateReported);
        System.out.println("Status: " + status);
        System.out.println("Reported By: " + reportedBy);
        System.out.println("Item Type: " + getItemType());
    }
}
