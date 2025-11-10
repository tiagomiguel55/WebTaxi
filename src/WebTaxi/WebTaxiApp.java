package WebTaxi;

import WebTaxi.model.*;

import java.util.List;
import java.util.Scanner;

public class WebTaxiApp {
    public static void main(String[] args) {
        Platform p = new Platform();
        seedData(p);
        Scanner sc = new Scanner(System.in);
        while (true) {
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
            System.out.println("10. Driver type with most services");
            System.out.println("11. Client with most services and their top driver");
            System.out.println("12. Client who used most different drivers");
            System.out.println("13. Driver with fewest services");
            System.out.println("14. Drivers in a specified region");
            System.out.println("15. Most popular service type");
            System.out.println("16. Service histogram by day");
            System.out.println("17. Set client action radius");
            System.out.println("18. Register RideSharing request for a driver");
            System.out.println("0. Exit");
            System.out.print("Option: ");
            String op = sc.nextLine();
            if (op.equals("0")) break;

            switch (op) {
                case "1":
                    System.out.println("--- Clients ---");
                    for (Client c : p.listClients()) {
                        System.out.println(c + " | Services: " + c.getService().size());
                    }
                    break;
                case "2":
                    System.out.println("--- Drivers ---");
                    for (Driver d : p.listDrivers()) {
                        System.out.println(d + " | Earnings: " + String.format("%.2f", d.getEarnings()) + " | Trips: " + d.getNumberOfTrips());
                    }
                    break;
                case "3":
                    System.out.print("Client code: ");
                    String cod = sc.nextLine();
                    Client cli = p.getClient(cod);
                    if (cli == null) {
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
                    Trip s = new Trip(10, 2, 0.0, 0.0, 2.0, 1.5);
                    s.setClient(demoClient);
                    boolean ok = p.launchRequest(s);
                    if (ok) {
                        System.out.println("Service successfully assigned to driver: " + s.getDriver().getName());
                        System.out.println("Price charged: " + String.format("%.2f", s.getPrice()));
                    } else {
                        System.out.println("Failed to assign service (no driver/insufficient balance)");
                    }
                    break;
                case "5":
                    System.out.println("--- Drivers by earnings ---");
                    for (Driver d : p.sortByEarnings()) {
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
                    if (c == null) {
                        System.out.println("Client not found");
                        break;
                    }
                    System.out.println("--- Services for client: " + c.getName() + " ---");
                    for (Service srv : c.getService()) {
                        System.out.println(srv);
                    }
                    System.out.println("Current balance: " + String.format("%.2f", c.getBalance()));
                    break;
                case "8":
                    System.out.print("Driver code: ");
                    String dc = sc.nextLine();
                    Driver drv = p.getDriver(dc);
                    if (drv == null) {
                        System.out.println("Driver not found");
                        break;
                    }
                    System.out.println("--- Services for driver: " + drv.getName() + " ---");
                    for (Service srv : drv.getServices()) {
                        System.out.println(srv + " | Commission: " + String.format("%.2f", srv.getCommissionValue(drv)));
                    }
                    System.out.println("Total earnings: " + String.format("%.2f", drv.getEarnings()));
                    break;
                case "9":
                    System.out.print("Driver code: ");
                    String dcc = sc.nextLine();
                    Driver drvKm = p.getDriver(dcc);
                    if (drvKm == null) {
                        System.out.println("Driver not found");
                        break;
                    }
                    System.out.println("Total kilometers driven: " + String.format("%.2f", drvKm.getTotalKilometers()));
                    break;
                case "10":
                    System.out.println("Type of driver with most services: " + p.getDriverTypeWithMostServices());
                    break;
                case "11":
                    Client topClient = p.getTopClient();
                    if (topClient != null) {
                        Driver topDriver = p.getTopDriverForClient(topClient);
                        System.out.println("Client with most services: " + topClient.getId());
                        System.out.println("Driver with most services for this client: " + topDriver.getCode());
                    } else {
                        System.out.println("No services found.");
                    }
                    break;
                case "12": // nova opção do menu
                    Client topClientDrivers = p.getClientWithMostDifferentDrivers();
                    if (topClientDrivers != null) {
                        System.out.println("Client who used most different drivers: " + topClientDrivers.getId());
                    } else {
                        System.out.println("No services found.");
                    }
                    break;
                case "13": // nova opção do menu
                    Driver fewestDriver = p.getDriverWithFewestServices();
                    if (fewestDriver != null) {
                        System.out.println("Driver with fewest services: " + fewestDriver.getCode() +
                                " (" + fewestDriver.getServices().size() + " services)");
                    } else {
                        System.out.println("No drivers found.");
                    }
                    break;
                case "14": // nova opção no menu
                    System.out.print("Enter x1: ");
                    double x1 = sc.nextDouble();
                    System.out.print("Enter y1: ");
                    double y1 = sc.nextDouble();
                    System.out.print("Enter x2: ");
                    double x2 = sc.nextDouble();
                    System.out.print("Enter y2: ");
                    double y2 = sc.nextDouble();

                    List<Driver> driversInRegion = p.getDriversInRegion(x1, y1, x2, y2);
                    if (driversInRegion.isEmpty()) {
                        System.out.println("No drivers found in the region.");
                    } else {
                        System.out.println("Drivers in the region:");
                        for (Driver d : driversInRegion) {
                            System.out.println(d.getCode() + " (" + d.getName() + ")");
                        }
                    }
                    break;
                case "15": // nova opção do menu
                    String popularService = p.getMostPopularServiceType();
                    System.out.println("Most popular service type: " + popularService);
                    break;
                case "16": // nova opção do menu
                    p.printServiceHistogramByDay();
                    break;
                case "17": // nova opção do menu
                    System.out.print("Enter client code: ");
                    String clientCode = sc.next();
                    Client client = p.getClient(clientCode);

                    if (client != null) {
                        System.out.print("Enter new action radius (km): ");
                        String input = sc.next();                // lê como string
                        input = input.replace(',', '.');         // substitui vírgula por ponto
                        try {
                            double radius = Double.parseDouble(input); // converte para double
                            client.setActionRadius(radius);
                            System.out.println("Action radius for client " + client.getId() + " set to " + radius + " km.");
                            sc.nextLine();
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid number format. Please enter a valid decimal number.");
                        }
                    } else {
                        System.out.println("Client not found.");
                    }
                    break;
                case "18": // nova opção
                    System.out.print("Enter driver code requesting ride: ");
                    String driverCode = sc.next();
                    sc.nextLine();
                    Driver driver = p.getDriver(driverCode);
                    if (driver != null) {
                        RideSharing rs = new RideSharing(15, 3, driver.getX(), driver.getY(), driver.getX() + 1, driver.getY() + 1);
                        rs.setDriver(driver);
                        p.launchRequest(rs);
                        System.out.println("RideSharing registered for driver " + driver.getCode());
                    } else {
                        System.out.println("Driver not found.");
                    }
                    break;


                default:
                    System.out.println("Invalid option");
            }
            System.out.println();
        }
        sc.close();
        System.out.println("Goodbye");
    }

    private static void seedData(Platform p) {
        // -------------------
        // Clientes
        // -------------------
        Client c1 = new FrequentClient("C1", "Alice");
        c1.addCredit(200.0);
        Client c2 = new RegularClient("C2", "Bruno");
        c2.addCredit(150.0);
        Client c3 = new OccasionalClient("C3", "Carla");
        c3.addCredit(100.0);
        Client c4 = new FrequentClient("C4", "David");
        c4.addCredit(250.0);
        Client c5 = new RegularClient("C5", "Eva");
        c5.addCredit(120.0);

        p.registerClient(c1);
        p.registerClient(c2);
        p.registerClient(c3);
        p.registerClient(c4);
        p.registerClient(c5);

        // -------------------
        // Condutores
        // -------------------
        Driver d1 = new RookieDriver("D1", "Tiago", "M", 0.5, 0.1, 3);
        Driver d2 = new VeteranDriver("D2", "Maria", "F", 10.0, 10.0, 60, 0.12);
        Driver d3 = new RookieDriver("D3", "João", "M", 1.2, 0.8, 2);
        Driver d4 = new VeteranDriver("D4", "Clara", "F", 2.5, 2.0, 48, 0.15);
        Driver d5 = new RookieDriver("D5", "Lucas", "M", 0.8, 1.5, 1);

        p.registerDriver(d1);
        p.registerDriver(d2);
        p.registerDriver(d3);
        p.registerDriver(d4);
        p.registerDriver(d5);

        // -------------------
        // Serviços - viagens normais (Trip)
        // -------------------
        Trip t1 = new Trip(10, 2, 0.0, 0.0, 2.0, 1.5);
        t1.setClient(c1);
        p.launchRequest(t1);
        Trip t2 = new Trip(14, 3, 1.0, 0.5, 3.0, 1.8);
        t2.setClient(c1);
        p.launchRequest(t2);
        Trip t3 = new Trip(9, 1, 0.2, 0.1, 1.5, 1.0);
        t3.setClient(c2);
        p.launchRequest(t3);
        Trip t4 = new Trip(20, 5, 2.0, 1.5, 4.0, 3.0);
        t4.setClient(c2);
        p.launchRequest(t4);

        // -------------------
        // Serviços especiais - DeliveryMeal
        // -------------------
        DeliveryMeal meal1 = new DeliveryMeal(12, 3, 0.0, 0.0, 2.0, 1.0, "Pizza", "Pizzaria XYZ", "Rua A", 10.0);
        meal1.setClient(c3);
        p.launchRequest(meal1);

        DeliveryMeal meal2 = new DeliveryMeal(13, 4, 1.0, 1.0, 2.5, 2.0, "Sushi", "Sushi Bar", "Rua B", 12.0);
        meal2.setClient(c4);
        p.launchRequest(meal2);

        // -------------------
        // Serviços especiais - DeliveryPackage
        // -------------------
        DeliveryPackage pkg1 = new DeliveryPackage(15, 2, 0.5, 0.5, 3.0, 2.0, 2.5);
        pkg1.setClient(c5);
        p.launchRequest(pkg1);

        DeliveryPackage pkg2 = new DeliveryPackage(16, 5, 2.0, 1.0, 4.0, 3.5, 1.0);
        pkg2.setClient(c1);
        p.launchRequest(pkg2);

        // -------------------
        // Serviços especiais - RideSharing (boleia)
        // -------------------
        RideSharing rs1 = new RideSharing(17, 3, 1.0, 1.0, 2.0, 1.5);
        rs1.setDriver(d1); // d1 pede boleia a outro condutor
        p.launchRequest(rs1);

        RideSharing rs2 = new RideSharing(18, 4, 2.0, 1.5, 3.0, 2.0);
        rs2.setDriver(d3); // d3 pede boleia
        p.launchRequest(rs2);
    }

}

