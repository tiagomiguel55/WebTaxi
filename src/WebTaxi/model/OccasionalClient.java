package WebTaxi.model;

public class OccasionalClient extends Client {

    public OccasionalClient(String code, String name) {
        super(code, name);
    }

    @Override
    public double getDescontoPercent(Service s) {
        return 0.0;
    }
}
