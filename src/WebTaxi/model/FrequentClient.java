package WebTaxi.model;

public class FrequentClient extends Client {

    public FrequentClient(String code, String name){ super(code,name); }

    @Override
    public double getDescontoPercent(Service s) {
        int n = this.getService().size();
        double pct = Math.min(0.20, 0.02 * n); // 2% per service, up to 20%
        return pct;
    }
}
