package WebTaxi.model;

public class DeliveryMeal extends Service {

    private String mealName;
    private String restaurant;
    private String destinationAddress;
    private double mealCost;

    public DeliveryMeal(int hour, int weekDay, double ox, double oy, double dx, double dy,
                        String mealName, String restaurant, String destinationAddress, double mealCost) {
        super(hour, weekDay, ox, oy, dx, dy);
        this.mealName = mealName;
        this.restaurant = restaurant;
        this.destinationAddress = destinationAddress;
        this.mealCost = mealCost;
    }

    @Override
    public void calculatePrice(double baseKmCost) {
        this.price = mealCost * 1.2; // 20% sobre o custo da refeição
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" [Meal: %s, Restaurant: %s]", mealName, restaurant);
    }
}
