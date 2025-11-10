package WebTaxi.model;

public class DeliveryPackage extends Service {

    private double weight;

    public DeliveryPackage(int hour, int weekDay, double ox, double oy, double dx, double dy, double weight) {
        super(hour, weekDay, ox, oy, dx, dy);
        this.weight = weight;
    }

    @Override
    public void calculatePrice(double baseKmCost) {
        this.price = baseKmCost * distanceKm; // preço depende apenas da quilometragem
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" [Package weight: %.2f kg]", weight);
    }
}
