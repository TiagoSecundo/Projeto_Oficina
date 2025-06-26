package entities;

public class Elevador {

    private int id;
    private String status;
    private String tipo;
    private String veiculoNaPlataforma;
    private String servicoEmExecucao;

    public Elevador() {
    }

    /**
     *
     * @param id
     */
    public Elevador(int id, String tipo) {
        this.id = id;
        this.tipo = tipo;
        this.status = "Livre";
        this.veiculoNaPlataforma = null;
        this.servicoEmExecucao = null;
    }

    /**
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @return
     */
    public String getStatus() {
        return status;
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
    public String getVeiculoNaPlataforma() {
        return veiculoNaPlataforma;
    }

    /**
     *
     * @param veiculoNaPlataforma
     */
    public void setVeiculoNaPlataforma(String veiculoNaPlataforma) {
        this.veiculoNaPlataforma = veiculoNaPlataforma;
    }

    /**
     *
     * @return
     */
    public String getServicoEmExecucao() {
        return servicoEmExecucao;
    }

    /**
     *
     * @param servicoEmExecucao
     */
    public void setServicoEmExecucao(String servicoEmExecucao) {
        this.servicoEmExecucao = servicoEmExecucao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "Elevador [ID=" + id + ", Status=" + status
                + ", Veículo=" + (veiculoNaPlataforma == null ? "Nenhum" : veiculoNaPlataforma)
                + ", Serviço=" + (servicoEmExecucao == null ? "Nenhum" : servicoEmExecucao) + "]";
    }
}
