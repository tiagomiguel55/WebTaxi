package WebTaxi.model;

public class RideSharing extends Service {

    private static final double FIXED_PRICE = 5.0; // valor fixo para a boleia

    public RideSharing(int hour, int weekDay, double ox, double oy, double dx, double dy) {
        super(hour, weekDay, ox, oy, dx, dy);
    }

    @Override
    public void calculatePrice(double baseKmCost) {
        this.price = FIXED_PRICE;
    }

    @Override
    public Client getClient() {
        return null; // não há cliente, apenas condutores
    }

    @Override
    public String toString() {
        return super.toString() + " [RideSharing]";
    }
}
