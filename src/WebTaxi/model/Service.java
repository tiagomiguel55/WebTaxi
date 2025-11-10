package WebTaxi.model;


public abstract class Service {

    protected int hour; // 0..23
    protected int weekDay; // 1..7
    protected double ox, oy; // origin
    protected double dx, dy; // destination
    protected double distanceKm;
    protected double price;
    protected Driver driver;
    protected Client client;


    public Service(int hour, int weekDay, double ox, double oy, double dx, double dy){
        this.hour = hour; this.weekDay = weekDay; this.ox=ox; this.oy=oy; this.dx=dx; this.dy=dy;
        this.distanceKm = util.GeoUtils.distance(ox,oy,dx,dy);
    }

    public int getHour() {
        return hour;
    }
    public int getWeekDay() {
        return weekDay;
    }

    public double getDistanceKm() {
        return distanceKm;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Driver getDriver() {
        return driver;
    }

    public Client getClient() {
        return client;
    }

    public abstract void calculatePrice(double baseKmCost);

    public double getPrice() {
        return price;
    }

    public double getCommissionValue(Driver driver) {
        double pct = driver.calculateCommissionPercent(this);
        return price * pct;
    }

    @Override
    public String toString(){
        return String.format("Service(%s) price=%.2f km=%.2f h=%02d d=%d", this.getClass().getSimpleName(), price, distanceKm, hour, weekDay);
    }
}



