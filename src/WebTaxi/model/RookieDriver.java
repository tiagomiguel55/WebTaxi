package WebTaxi.model;

/**
 * Classe que representa um motorista novato (RookieDriver).
 * Herda da classe Driver e sobrescreve a forma de calcular
 * o percentual de comissão de acordo com a hora do serviço.
 */
public class RookieDriver extends Driver {


    /**
     * Construtor: delega a inicialização para a classe base Driver.
     *
     * @param code identificador do condutor
     * @param name nome do condutor
     * @param gender gênero (string, ex.: "M" ou "F")
     * @param x posição inicial do condutor em coordenadas (double)
     * @param y posição inicial do condutor em coordenadas (double)
     * @param serviceTime tempo estimado para o serviço (em minutos ou outra unidade usada pelo projeto)
     */
    public RookieDriver(String code, String name, String gender, double x, double y, int serviceTime) {
        super(code, name, gender, x, y, serviceTime);
    }

    @Override
    /**
     * Calcula o percentual de comissão aplicado ao serviço `s`.
     * Lógica:
     *  - Usa a hora do serviço (s.getHour()) para determinar o percentual.
     *  - Manhã (6-11): 10%
     *  - Tarde  (12-17): 12%
     *  - Noite (18-21): 15%
     *  - Demais (22-5): 8%
     * Observações:
     *  - Espera-se que s.getHour() retorne um inteiro no formato 0-23.
     *  - Não são feitas validações adicionais aqui; assume-se que o valor retornado é válido.
     */
    public double calculateCommissionPercent(Service s) {
        int hour = s.getHour();
        if(hour >= 6 && hour < 12) return 0.10; // manhã: 10%
        if(hour >= 12 && hour < 18) return 0.12; // tarde: 12%
        if(hour >= 18 && hour < 22) return 0.15; // noite: 15%
        return 0.08; // restante (22-5): 8%
    }
}
