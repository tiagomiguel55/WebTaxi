package WebTaxi.model;

public class RideSharing extends Service implements SpecialService {

    private static final double FIXED_PRICE = 5.0;

    public RideSharing(int hour, int weekDay, double ox, double oy, double dx, double dy) {
        super(hour, weekDay, ox, oy, dx, dy);
    }

    @Override
    public void calculatePrice(double baseKmCost) {
        this.price = FIXED_PRICE;
    }

    @Override
    public Client getClient() {
        return null; // Não há cliente, é um condutor que pede a boleia
    }

    @Override
    public double calculateFixedCommission() {
        return price * FIXED_COMMISSION_PERCENT;
    }

    @Override
    public String toString() {
        return super.toString() + " [RideSharing]";
    }
}
