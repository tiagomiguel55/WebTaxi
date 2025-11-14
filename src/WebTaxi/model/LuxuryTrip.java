package WebTaxi.model;

public class LuxuryTrip extends Trip { // herda de Trip
    private static final double LUXURY_MULTIPLIER = 2.0; // dobra o preço
    private static final double NIGHT_BONUS = 0.30;

    public LuxuryTrip(int hour, int weekDay, double ox, double oy, double dx, double dy) {
        super(hour, weekDay, ox, oy, dx, dy);
    }

    @Override
    public void calculatePrice(double baseKmCost) {
        super.calculatePrice(baseKmCost);
        this.price *= LUXURY_MULTIPLIER; // Viagem de luxo é 2x mais cara
        if (hour >= 18 || hour < 6) {
            this.price *= (1 + NIGHT_BONUS); // +30% à noite
        }
    }

    @Override
    public String toString() {
        return super.toString() + " [LuxuryTrip]";
    }
}
