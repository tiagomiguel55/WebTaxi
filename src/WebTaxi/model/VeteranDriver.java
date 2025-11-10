package WebTaxi.model;

public class VeteranDriver extends Driver {

    private double fixedRate;

    public VeteranDriver(String code, String name, String gender, double x, double y, int serviceTime, double fixedRate) {
        super(code, name, gender, x, y, serviceTime);
        this.fixedRate = fixedRate;
    }

    @Override
    public double calculateCommissionPercent(Service s) {
        return fixedRate;
    }
}
