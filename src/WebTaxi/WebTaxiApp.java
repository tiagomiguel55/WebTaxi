package WebTaxi;

import WebTaxi.model.*;

import java.util.Scanner;

public class WebTaxiApp {
    public static void main(String[] args){
        Platform p = new Platform();
        seedData(p);
        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.println("=== WebTaxi ===");
            System.out.println("1. List clients");
            System.out.println("2. List drivers");
            System.out.println("3. Add credit to client");
            System.out.println("4. Launch example trip request");
            System.out.println("5. Statistics: sort drivers by earnings");
            System.out.println("6. Gender proportion of drivers");
            System.out.println("7. List client services and balance");
            System.out.println("8. List driver services and earnings");
            System.out.println("9. Show total kilometers per driver");
            System.out.println("0. Exit");
            System.out.print("Option: ");
            String op = sc.nextLine();
            if(op.equals("0")) break;

            switch(op){
                case "1":
                    System.out.println("--- Clients ---");
                    for(Client c: p.listClients()) {
                        System.out.println(c + " | Services: " + c.getService().size());
                    }
                    break;
                case "2":
                    System.out.println("--- Drivers ---");
                    for(Driver d: p.listDrivers()) {
                        System.out.println(d + " | Earnings: " + String.format("%.2f", d.getEarnings()) + " | Trips: " + d.getNumberOfTrips());
                    }
                    break;
                case "3":
                    System.out.print("Client code: ");
                    String cod = sc.nextLine();
                    Client cli = p.getClient(cod);
                    if(cli==null){
                        System.out.println("Client not found");
                        break;
                    }
                    System.out.print("Amount to add: ");
                    double v = Double.parseDouble(sc.nextLine());
                    cli.addCredit(v);
                    System.out.println("New balance: " + String.format("%.2f", cli.getBalance()));
                    break;
                case "4":
                    Client demoClient = p.getClient("C1"); // Alice
                    Trip s = new Trip(10,2, 0.0,0.0, 2.0,1.5);
                    s.setClient(demoClient);
                    boolean ok = p.launchRequest(s);
                    if(ok) {
                        System.out.println("Service successfully assigned to driver: " + s.getDriver().getName());
                        System.out.println("Price charged: " + String.format("%.2f", s.getPrice()));
                    } else {
                        System.out.println("Failed to assign service (no driver/insufficient balance)");
                    }
                    break;
                case "5":
                    System.out.println("--- Drivers by earnings ---");
                    for(Driver d: p.sortByEarnings()) {
                        System.out.println(d.getCode() + " - " + d.getName() + " => Earnings: " + String.format("%.2f", d.getEarnings()) + " | Trips: " + d.getNumberOfTrips());
                    }
                    break;
                case "6":
                    double femaleProportion = p.genderProportion();
                    double maleProportion = 1 - femaleProportion;
                    System.out.printf("Proportion of female drivers: %.2f%%\n", femaleProportion * 100);
                    System.out.printf("Proportion of male drivers: %.2f%%\n", maleProportion * 100);
                    break;
                case "7":
                    System.out.print("Client code: ");
                    String cc = sc.nextLine();
                    Client c = p.getClient(cc);
                    if(c == null){
                        System.out.println("Client not found");
                        break;
                    }
                    System.out.println("--- Services for client: " + c.getName() + " ---");
                    for(Service srv: c.getService()){
                        System.out.println(srv);
                    }
                    System.out.println("Current balance: " + String.format("%.2f", c.getBalance()));
                    break;
                case "8":
                    System.out.print("Driver code: ");
                    String dc = sc.nextLine();
                    Driver drv = p.getDriver(dc);
                    if(drv == null){
                        System.out.println("Driver not found");
                        break;
                    }
                    System.out.println("--- Services for driver: " + drv.getName() + " ---");
                    for(Service srv: drv.getServices()){
                        System.out.println(srv + " | Commission: " + String.format("%.2f", srv.getCommissionValue(drv)));
                    }
                    System.out.println("Total earnings: " + String.format("%.2f", drv.getEarnings()));
                    break;
                case "9":
                    System.out.print("Driver code: ");
                    String dcc = sc.nextLine();
                    Driver drvKm = p.getDriver(dcc);
                    if(drvKm == null){
                        System.out.println("Driver not found");
                        break;
                    }
                    System.out.println("Total kilometers driven: " + String.format("%.2f", drvKm.getTotalKilometers()));
                    break;
                default:
                    System.out.println("Invalid option");
            }
            System.out.println();
        }
        sc.close();
        System.out.println("Goodbye");
    }

    private static void seedData(Platform p){
        Client c1 = new FrequentClient("C1","Alice"); c1.addCredit(120.0);
        Client c2 = new RegularClient("C2","Bruno"); c2.addCredit(60.0);
        Client c3 = new OccasionalClient("C3","Carla"); c3.addCredit(30.0);
        Client c4 = new FrequentClient("C4","David"); c4.addCredit(200.0);
        Client c5 = new RegularClient("C5","Eva"); c5.addCredit(80.0);
        p.registerClient(c1); p.registerClient(c2); p.registerClient(c3); p.registerClient(c4); p.registerClient(c5);

        Driver d1 = new RookieDriver("D1","Tiago","M", 0.5,0.1, 3);
        Driver d2 = new VeteranDriver("D2","Maria","F", 10.0,10.0, 60, 0.12);
        Driver d3 = new RookieDriver("D3","João","M", 1.2,0.8, 2);
        Driver d4 = new VeteranDriver("D4","Clara","F", 2.5,2.0, 48, 0.15);
        Driver d5 = new RookieDriver("D5","Lucas","M", 0.8,1.5, 1);
        p.registerDriver(d1); p.registerDriver(d2); p.registerDriver(d3); p.registerDriver(d4); p.registerDriver(d5);

        Trip t1 = new Trip(10,2, 0.0,0.0, 2.0,1.5); t1.setClient(c1); p.launchRequest(t1);
        Trip t2 = new Trip(14,3, 1.0,0.5, 3.0,1.8); t2.setClient(c4); p.launchRequest(t2);
        Trip t3 = new Trip(9,1, 0.2,0.1, 1.5,1.0); t3.setClient(c2); p.launchRequest(t3);
        Trip t4 = new Trip(20,5, 2.0,1.5, 4.0,3.0); t4.setClient(c5); p.launchRequest(t4);
    }
}
