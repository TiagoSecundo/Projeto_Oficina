
package entities;

import java.time.LocalDate;


public class Elevador {
    private int id;
    private String alinhamentoBalanceamento;
    private String geral;
    private Veiculo veiculo;
    
    public Elevador(){} 
    
    public Elevador(int id, String alinhamentoBalanceamento, String geral, Veiculo veiculo) {
        this.id = id;
        this.alinhamentoBalanceamento = alinhamentoBalanceamento;
        this.geral = geral;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public String getAlinhamentoBalanceamento() {
        return alinhamentoBalanceamento;
    }

    public void setAlinhamentoBalanceamento(String alinhamentoBalanceamento) {
        this.alinhamentoBalanceamento = alinhamentoBalanceamento;
    }

    public String getGeral() {
        return geral;
    }

    public void setGeral(String geral) {
        this.geral = geral;
    }

    @Override
    public String toString() {
        return "Elevador{" + "id=" + id + ", alinhamentoBalanceamento=" + alinhamentoBalanceamento + ", geral=" + geral + '}';
    }
    

}
