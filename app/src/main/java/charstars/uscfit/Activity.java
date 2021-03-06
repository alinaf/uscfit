package charstars.uscfit;

public class Activity {


    static int _ID = 0;
    private int id;
    private String category;
    private double defaultCalorieValue;

    public Activity(String category, double defaultCalorieValue) {
        this.category = category;
        this.defaultCalorieValue = defaultCalorieValue;
        this.id = _ID++;
    }

    public Activity() {

    }

    public double getDefaultCalorieValue() {
        return defaultCalorieValue;
    }

    public void setDefaultCalorieValue(int defaultCalorieValue) {
        this.defaultCalorieValue = defaultCalorieValue;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getID() {
        return this.id;
    }


    @Override
    public String toString() {
        return this.category;
    }
}
