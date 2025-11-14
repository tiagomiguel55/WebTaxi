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
        double radius = 3.0;

        // Se houver cliente, usa o actionRadius
        if (s.client != null) {
            radius = s.client.getActionRadius();
        }

        // Selecionar candidatos com base no tipo de serviço
        for (Driver d : drivers.values()) {
            // Filtrar por tipo de condutor
            if (s instanceof LuxuryTrip && !(d instanceof LuxuryDriver)) continue;
            if (!(s instanceof LuxuryTrip) && d instanceof LuxuryDriver) continue;

            // Filtrar por distância
            double d2 = util.GeoUtils.distance(s.ox, s.oy, d.getX(), d.getY());
            if (d2 <= radius) candidates.add(d);
        }

        if (candidates.isEmpty()) return false;

        // Escolher o condutor mais próximo
        Driver chosen = null;
        double best = Double.MAX_VALUE;
        for (Driver d : candidates) {
            double dist = util.GeoUtils.distance(s.ox, s.oy, d.getX(), d.getY());
            if (dist < best) {
                best = dist;
                chosen = d;
            }
        }
        if (chosen == null) return false;

        s.setDriver(chosen);
        s.calculatePrice(baseKmCost);

        // Apenas serviços com cliente precisam de débito e adicionar ao cliente
        if (s.client != null) {
            boolean ok = s.client.debit(s.getPrice());
            if (!ok) return false;
            s.client.addService(s);
        }

        // Adiciona sempre ao condutor e à lista global
        chosen.addService(s);
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

    // Devolve o cliente que tem mais serviços no total
    public Client getTopClient() {
        Client topClient = null;
        int maxServices = 0;

        for (Client c : clients.values()) {
            int n = c.getService().size();
            if (n > maxServices) {
                maxServices = n;
                topClient = c;
            }
        }

        return topClient;
    }

    // Para um dado cliente, devolve o condutor com mais serviços desse cliente
    public Driver getTopDriverForClient(Client client) {
        if (client == null) return null;

        Driver topDriver = null;
        int maxServices = 0;

        Map<Driver, Integer> driverCount = new HashMap<>();
        for (Service s : client.getService()) {
            Driver d = s.getDriver();
            driverCount.put(d, driverCount.getOrDefault(d, 0) + 1);
        }

        for (Map.Entry<Driver, Integer> entry : driverCount.entrySet()) {
            if (entry.getValue() > maxServices) {
                maxServices = entry.getValue();
                topDriver = entry.getKey();
            }
        }

        return topDriver;
    }

    public String getDriverTypeWithMostServices() {
        int rookieCount = 0;
        int veteranCount = 0;

        for (Driver d : drivers.values()) {
            if (d instanceof RookieDriver) rookieCount += d.getServices().size();
            else if (d instanceof VeteranDriver) veteranCount += d.getServices().size();
        }

        if (rookieCount > veteranCount) {
            return "RookieDriver (" + rookieCount + " services)";
        } else if (veteranCount > rookieCount) {
            return "VeteranDriver (" + veteranCount + " services)";
        } else {
            return "Both types of drivers have the same number of services: " + rookieCount;
        }
    }


    public Client getClientWithMostDifferentDrivers() {
        Client topClient = null;
        int maxDrivers = 0;

        for (Client c : clients.values()) {
            Set<Driver> uniqueDrivers = new HashSet<>();
            for (Service s : c.getService()) {
                uniqueDrivers.add(s.getDriver());
            }
            if (uniqueDrivers.size() > maxDrivers) {
                maxDrivers = uniqueDrivers.size();
                topClient = c;
            }
        }

        return topClient;
    }

    public Driver getDriverWithFewestServices() {
        Driver minDriver = null;
        int minServices = Integer.MAX_VALUE;

        for (Driver d : drivers.values()) {
            int n = d.getServices().size();
            if (n < minServices) {
                minServices = n;
                minDriver = d;
            }
        }

        return minDriver;
    }

    public List<Driver> getDriversInRegion(double x1, double y1, double x2, double y2) {
        double xMin = Math.min(x1, x2);
        double xMax = Math.max(x1, x2);
        double yMin = Math.min(y1, y2);
        double yMax = Math.max(y1, y2);

        List<Driver> driversInRegion = new ArrayList<>();
        for (Driver d : drivers.values()) {
            double dx = d.getX();
            double dy = d.getY();
            if (dx >= xMin && dx <= xMax && dy >= yMin && dy <= yMax) {
                driversInRegion.add(d);
            }
        }

        return driversInRegion;
    }

    public String getMostPopularServiceType() {
        if (services.isEmpty()) return "No services available";

        Map<String, Integer> typeCount = new HashMap<>();

        for (Service s : services) {
            String typeName = s.getClass().getSimpleName(); // pega o nome da classe
            typeCount.put(typeName, typeCount.getOrDefault(typeName, 0) + 1);
        }

        String popularType = null;
        int maxCount = 0;

        for (Map.Entry<String, Integer> entry : typeCount.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                popularType = entry.getKey();
            }
        }

        return popularType;
    }

    public Map<Integer, Integer> getServiceCountByDay() {
        Map<Integer, Integer> dayCount = new HashMap<>();
        for (Service s : services) {
            int day = s.getWeekDay(); // assumir que Service tem método getDayOfWeek()
            dayCount.put(day, dayCount.getOrDefault(day, 0) + 1);
        }
        return dayCount;
    }

    public void printServiceHistogramByDay() {
        Map<Integer, Integer> dayCount = getServiceCountByDay();
        System.out.println("Service count per day of the week:");
        for (int i = 1; i <= 7; i++) {
            int count = dayCount.getOrDefault(i, 0);
            System.out.printf("Day %d: %s (%d)%n", i, "*".repeat(count), count);
        }
    }

    public boolean registerRideSharing(Driver requester, double dx, double dy) {
        // O condutor que pede a boleia é treated como "cliente"
        RideSharing rs = new RideSharing(12, 3, requester.getX(), requester.getY(), dx, dy);
        rs.setDriver(requester); // define quem está a pedir a boleia
        rs.calculatePrice(0); // preço fixo
        requester.addService(rs);
        services.add(rs);
        return true;
    }



    public List<Service> getServices(){ return services; }


}