package entities;

import java.util.ArrayList;

public class Veiculo {

    private String marca;
    private String modelo;
    private int ano;
    private String placa;
    private String status;
    private Cliente proprietario;
    private ArrayList<Servico> historicoServicos;
    private String relatorio;
    private static int totalVeiculos = 0;
    /**
     * 
     * @param placa
     * @param marca
     * @param modelo
     * @param ano
     * @param status
     * @param relatorio 
     */
    public Veiculo(String placa, String marca, String modelo, int ano, String status, String relatorio) {
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.status = status;
        this.relatorio = relatorio;
        this.historicoServicos = new ArrayList<>();
        totalVeiculos++;
        Cliente.totalVeiculosProtected++;
    }
    /**
     * 
     */
    public Veiculo() {
    }
    /**
     * 
     * @return 
     */
    public static int getTotalVeiculos() {
        return totalVeiculos;
    }
    /**
     * 
     * @param marca 
     */
    public void setMarca(String marca) {
        this.marca = marca;
    }
    /**
     * 
     * @param ano 
     */
    public void setAno(int ano) {
        this.ano = ano;
    } 
    /**
     * 
     * @param proprietario 
     */
    
    public Cliente getProprietario() {
        return proprietario;
    }

    public void setProprietario(Cliente proprietario) {
        this.proprietario = proprietario;
    }
    /**
     * 
     * @param historicoServicos 
     */
    public void setHistoricoServicos(ArrayList<Servico> historicoServicos) {
        this.historicoServicos = historicoServicos;
    }

    /**
     *
     * @return
     */
    public String getPlaca() {
        return this.placa;
    }

    /**
     *
     * @param placa
     */
    public void setPlaca(String placa) {
        this.placa = placa;
    }

    /**
     *
     * @return
     */
    public String getModelo() {
        return this.modelo;
    }

    /**
     *
     * @param modelo
     */
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    /**
     *
     * @return
     */
    public String getStatus() {
        return this.status;
    }

    /**
     *
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }
    /**
     * 
     * @return 
     */
    public String getRelatorio() {
        return relatorio;
    }
    /**
     * 
     * @param relatorio 
     */
    public void setRelatorio(String relatorio) {
        this.relatorio = relatorio;
    }
    /**
     * 
     * @return 
     */
    @Override
    public String toString() {
        return "Veiculo{" + "marca=" + marca + ", modelo=" + modelo + ", ano=" + ano + ", placa=" + placa + 
                ", status=" + status + ", proprietario=" + proprietario + ", historicoServicos=" + historicoServicos + ", relatorio=" + relatorio + '}'+"\n";
    }
}
