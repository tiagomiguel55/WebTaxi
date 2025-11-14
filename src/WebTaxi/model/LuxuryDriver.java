package WebTaxi.model;

public class LuxuryDriver extends Driver {

    public LuxuryDriver(String code, String name, String gender, double x, double y, int serviceTime) {
        super(code, name, gender, x, y, serviceTime);
    }

    @Override
    public double calculateCommissionPercent(Service s) {
        if (s instanceof LuxuryTrip) {
            if (s.getPrice() > 10.0)
                return 0.25; // 25% para viagens premium acima de 10€
            else
                return 0.20; // 20% padrão
        } else {
            // Não aceita outros serviços (Trip, Delivery, etc.)
            return 0.0;
        }
    }
}
