package WebTaxi.model;

import java.util.ArrayList;
import java.util.List;

public abstract class Driver {
    protected String code;
    protected String name;
    protected String gender; // "M" / "F" / etc
    protected double x, y; // current location
    protected int serviceTime; // months/years
    protected List<Service> services = new ArrayList<>();


    public Driver(String code, String name, String gender, double x, double y, int serviceTime) {
        this.code = code;
        this.name = name;
        this.gender = gender;
        this.x = x;
        this.y = y;
        this.serviceTime = serviceTime;
    }


    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setLocation(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public List<Service> getServices() {
        return services;
    }

    public void addService(Service s) {
        services.add(s);
    }


    public double getEarnings() {
        double sum = 0.0;
        for (Service s : services) sum += s.getCommissionValue(this);
        return sum;
    }


    public int getNumberOfTrips() {
        return services.size();
    }

    public double getTotalKilometers() {
        double total = 0.0;
        for(Service s : services) { // 'services' é a lista de serviços do driver
            total += s.getDistanceKm(); // cada serviço deve ter o método getDistance()
        }
        return total;
    }


    // percentage (0..1) or specific value - implemented in subclasses
    public abstract double calculateCommissionPercent(Service s);


    @Override
    public String toString() {
        return String.format("Driver[%s] %s (loc=%.2f,%.2f)", code, name, x, y);
    }


}




