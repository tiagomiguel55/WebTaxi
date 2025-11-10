package WebTaxi.model;

import java.util.ArrayList;
import java.util.List;

public abstract class Client {

    private String name;
    private String id;
    private double balance;
    protected double actionRadius = 3.0; // default 3 km
    private List<Service> service = new ArrayList<>();

    public Client(String code, String name) {
        this.id = code;
        this.name = name;
        this.balance = 0.0;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public double getBalance() {
        return balance;
    }

    public List<Service> getService() {
        return service;
    }

    public void addService(Service service) {
        this.service.add(service);
    }

    public void addCredit(double amount) {
        this.balance += amount;
    }

    public void setActionRadius(double radius) {
        this.actionRadius = radius;
    }

    public double getActionRadius() {
        return this.actionRadius;
    }


    public boolean debit(double amount){ // returns true if successful
        if(this.balance + 1e-9 >= amount){ this.balance -= amount; return true; }
        return false; // insufficient funds
    }

    public abstract double getDescontoPercent(Service s);

    public String toString() {
        return "WebTaxi.model.Client{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", balance=" + balance +
                ", service=" + service +
                '}';
    }



}
