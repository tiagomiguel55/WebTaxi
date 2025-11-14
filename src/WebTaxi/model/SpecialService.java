package WebTaxi.model;

public interface SpecialService {
    double FIXED_COMMISSION_PERCENT = 0.01; // 1% fixo para todos os condutores

    double calculateFixedCommission();
}
