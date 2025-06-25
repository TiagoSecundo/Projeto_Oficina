package entities;

public class Elevador {

    private int id;
    private String status;
    private String veiculoNaPlataforma;
    private String servicoEmExecucao;
    private boolean exclusivoBalanceamento = false;

    public Elevador() {
    }

    /**
     *
     * @param id
     */
    public Elevador(int id) {
        this.id = id;
        this.status = "Livre";
        this.veiculoNaPlataforma = null;
        this.servicoEmExecucao = null;
        this.exclusivoBalanceamento = false;
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

  // ... (restante dos getters e setters e toString)
    public boolean isExclusivoBalanceamento() {
        return exclusivoBalanceamento;
    }

    public void setExclusivoBalanceamento(boolean exclusivoBalanceamento) {
        this.exclusivoBalanceamento = exclusivoBalanceamento;
    }

    @Override
    public String toString() {

        return "Elevador [ID=" + id + ", Status=" + status
                + ", Veiculo=" + (veiculoNaPlataforma == null ? " " : veiculoNaPlataforma)
                + ", Servico=" + (servicoEmExecucao == null ? " " : servicoEmExecucao)
                + ", ExclusivoBalanceamento=" + exclusivoBalanceamento + "]" +"\n"; // Adicionado para depuração
    }
}