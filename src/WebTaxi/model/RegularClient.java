package WebTaxi.model;

public class RegularClient extends Client {


    public RegularClient(String code, String name){ super(code,name); }

    @Override
    public double getDescontoPercent(Service s) {
        int day = s.getWeekDay();
        if(day >=1 && day <=3) return 0.10; // 10% Mon-Wed
        return 0.0;
    }
}
