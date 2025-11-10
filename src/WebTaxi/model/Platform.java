package WebTaxi.model;

import util.GeoUtils;

import java.util.*;

public class Platform {

    private Map<String, Client> clients = new HashMap<>();
    private Map<String, Driver> drivers = new HashMap<>();
    private List<Service> services = new ArrayList<>();
    private double baseKmCost = 1.0;

    public Platform() {
    }


    public void setBaseKmCost(double v) {
        this.baseKmCost = v;
    }


    public void registerClient(Client c) {
        clients.put(c.getId(), c);
    }

    public Client getClient(String code) {
        return clients.get(code);
    }


    public void registerDriver(Driver d) {
        drivers.put(d.getCode(), d);
    }

    public Driver getDriver(String code) {
        return drivers.get(code);
    }


    public Collection<Driver> listDrivers() {
        return drivers.values();
    }


    public boolean launchRequest(Service s) {
        List<Driver> candidates = new ArrayList<>();
        for (Driver d : drivers.values()) {
            double d2 = GeoUtils.distance(s.ox, s.oy, d.getX(), d.getY());
            if (d2 <= 3.0) candidates.add(d);
        }
        if (candidates.isEmpty()) return false;
        Driver chosen = null;
        double best = Double.MAX_VALUE;
        for (Driver d : candidates) {
            double dist = GeoUtils.distance(s.ox, s.oy, d.getX(), d.getY());
            if (dist < best) {
                best = dist;
                chosen = d;
            }
        }
        if (chosen == null) return false;
        s.setDriver(chosen);
        if (s.client == null) return false;
        s.calculatePrice(baseKmCost);
        boolean ok = s.client.debit(s.getPrice());
        if (!ok) return false;
        chosen.addService(s);
        s.client.addService(s);
        services.add(s);
        return true;
    }

    public double genderProportion() {
        int male = 0;
        int female = 0;
        for (Driver d : drivers.values()) {
            if (d.getGender().equals("M")) male++;
            else if (d.getGender().equals("F")) female++;
        }
        if (male + female == 0) return 0.0;
        return (double) female / (male + female);


    }

    public List<Driver> sortByEarnings(){
        List<Driver> l = new ArrayList<>(drivers.values());
        Collections.sort(l, new Comparator<Driver>(){
            public int compare(Driver a, Driver b){
                return Double.compare(b.getEarnings(), a.getEarnings());
            }
        });
        return l;
    }

    public Collection<Client> listClients() {
        return clients.values();
    }


    public List<Service> getServices(){ return services; }


}