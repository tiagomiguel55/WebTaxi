package WebTaxi.model;

public class Trip extends Service{

    public Trip(int hour, int weekDay, double ox, double oy, double dx, double dy){
        super(hour,weekDay,ox,oy,dx,dy);
    }


    @Override
    public void calculatePrice(double baseKmCost) {
        if (hour >= 8 && hour < 22) {
            // Peak hours: 20% increase
            this.price = this.distanceKm * baseKmCost;
        } else {
            this.price = this.distanceKm * (2.0 * baseKmCost);
        }
        if (client != null) { // Apply client discount if applicable
            double discountPercent = client.getDescontoPercent(this); //
            this.price = this.price * (1.0 - discountPercent);
        }
    }
}
