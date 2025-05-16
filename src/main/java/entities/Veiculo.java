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
    //ainda preciso ver como relacionar as classes cliente, servicos e relatorio com o veiculo

    public Veiculo() {
    }

    public Veiculo(String placa, String marca, String modelo, int ano, String status) {
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.status = status;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public void setProprietario(Cliente proprietario) {
        this.proprietario = proprietario;
    }

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

    @Override
    public String toString() {
        return "Veiculo{" + "marca=" + marca + ", modelo=" + modelo + ", ano=" + ano + ", placa=" + placa + ", status=" + status + ", proprietario=" + proprietario + ", historicoServicos=" + historicoServicos + '}';
    }

}
