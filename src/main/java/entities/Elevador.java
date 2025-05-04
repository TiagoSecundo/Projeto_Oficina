
package entities;

import java.time.LocalDate;


public class Elevador {
    private int id;
    private String alinhamentoBalanceamento;
    private String geral;
    private Veiculo veiculo;
    
    public Elevador(){} 
    /**
     * Construtor com parâmetros para inicializar os dados do elevador
     * @param id
     * @param alinhamentoBalanceamento
     * @param geral
     * @param veiculo 
     */
    public Elevador(int id, String alinhamentoBalanceamento, String geral, Veiculo veiculo) {
        this.id = id;
        this.alinhamentoBalanceamento = alinhamentoBalanceamento;
        this.geral = geral;
    }
    /**
     * Retorna o ID do elevador
     * @return 
     */
    public int getId() {
        return id;
    }
    /**
     * Define o ID do elevador
     * @param id 
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * Retorna o veículo associado ao elevador
     * @return 
     */
    public Veiculo getVeiculo() {
        return veiculo;
    }
    /**
     * Define o veículo associado ao elevador
     * @param veiculo 
     */
    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }
    /**
     * Retorna informações sobre o alinhamento e balanceamento realizados
     * @return 
     */
    public String getAlinhamentoBalanceamento() {
        return alinhamentoBalanceamento;
    }
    /**
     * Define informações sobre o alinhamento e balanceamento realizados
     * @param alinhamentoBalanceamento 
     */
    public void setAlinhamentoBalanceamento(String alinhamentoBalanceamento) {
        this.alinhamentoBalanceamento = alinhamentoBalanceamento;
    }
    /**
     * Retorna informações sobre a revisão geral feita
     * @return 
     */
    public String getGeral() {
        return geral;
    }
    /**
     * Define informações sobre a revisão geral feita
     * @param geral 
     */
    public void setGeral(String geral) {
        this.geral = geral;
    }
    /**
     * Retorna uma representação em string dos dados do elevador
     * @return 
     */
    @Override
    public String toString() {
        return "Elevador{" + "id=" + id + ", alinhamentoBalanceamento=" + alinhamentoBalanceamento + ", geral=" + geral + '}';
    }
}
