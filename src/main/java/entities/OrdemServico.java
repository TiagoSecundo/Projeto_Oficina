package entities;

import java.time.LocalDateTime;
import java.util.List;

public class OrdemServico {

    private int id;
    private Cliente cliente;
    private Veiculo veiculo;
    private Mecanico mecanico;
    private Elevador elevador;
    private LocalDateTime dataHora;
    private List<ItemServico> itensServico; // peças e quantidades
    private double valorMaoDeObra;
    private double valorTotal;
    private String status; // Aberta, Concluída, Emitida Nota Fiscal
    private TipoServico tipoServico;

    public OrdemServico(int id, Cliente cliente, Veiculo veiculo, Mecanico mecanico,
            Elevador elevador, LocalDateTime dataHora, List<ItemServico> itensServico,
            double valorMaoDeObra, String status, TipoServico tipoServico) {
        this.id = id;
        this.cliente = cliente;
        this.veiculo = veiculo;
        this.mecanico = mecanico;
        this.elevador = elevador;
        this.dataHora = dataHora;
        this.itensServico = itensServico;
        this.valorMaoDeObra = valorMaoDeObra;
        this.valorTotal = calcularValorTotal();
        this.status = status;
        this.tipoServico = tipoServico;
    }

    public double calcularValorTotal() {
        double totalPecas = 0;
        for (ItemServico item : itensServico) {
            totalPecas += item.getQuantidade() * item.getProduto().getPrecoFinal();
        }
        return totalPecas + valorMaoDeObra;
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
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     *
     * @param cliente
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     *
     * @return
     */
    public Veiculo getVeiculo() {
        return veiculo;
    }

    /**
     *
     * @param veiculo
     */
    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    /**
     *
     * @return
     */
    public Mecanico getMecanico() {
        return mecanico;
    }

    /**
     *
     * @param mecanico
     */
    public void setMecanico(Mecanico mecanico) {
        this.mecanico = mecanico;
    }

    /**
     *
     * @return
     */
    public Elevador getElevador() {
        return elevador;
    }

    /**
     *
     * @param elevador
     */
    public void setElevador(Elevador elevador) {
        this.elevador = elevador;
    }

    /**
     *
     * @return
     */
    public LocalDateTime getDataHora() {
        return dataHora;
    }

    /**
     *
     * @param dataHora
     */
    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    /**
     *
     * @return
     */
    public List<ItemServico> getItensServico() {
        return itensServico;
    }

    /**
     *
     * @param itensServico
     */
    public void setItensServico(List<ItemServico> itensServico) {
        this.itensServico = itensServico;
    }

    /**
     *
     * @return
     */
    public double getValorMaoDeObra() {
        return valorMaoDeObra;
    }

    /**
     *
     * @param valorMaoDeObra
     */
    public void setValorMaoDeObra(double valorMaoDeObra) {
        this.valorMaoDeObra = valorMaoDeObra;
    }

    /**
     *
     * @return
     */
    public double getValorTotal() {
        return valorTotal;
    }

    /**
     *
     * @param valorTotal
     */
    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
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

    public TipoServico getTipoServico() { // ✅ NOVO GETTER
        return tipoServico;
    }

    public void setTipoServico(TipoServico tipoServico) { // ✅ NOVO SETTER
        this.tipoServico = tipoServico;
    }

    /**
     *
     * @return
     */
    @Override
   public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("OrdemServico [ID=").append(id)
          .append(", Cliente=").append(cliente != null ? cliente.getNome() : "N/A")
          .append(", Veículo=").append(veiculo != null ? veiculo.getModelo() : "N/A")
          .append(", Mecânico=").append(mecanico != null ? mecanico.getNome() : "N/A")
          .append(", Elevador=").append(elevador != null ? elevador.getId() : "N/A")
          .append(", Tipo Serviço=").append(tipoServico != null ? tipoServico.getDescricao() : "N/A") // ✅ Exibir TipoServico
          .append(", Data/Hora=").append(dataHora)
          .append(", Status=").append(status)
          .append(", Valor Mão de Obra=").append(String.format("%.2f", valorMaoDeObra))
          .append(", Valor Total=").append(String.format("%.2f", getValorTotal()))
          .append("]\n");

        if (itensServico != null && !itensServico.isEmpty()) {
            sb.append("  Itens de Serviço:\n");
            for (ItemServico item : itensServico) {
                sb.append("    - ").append(item.getProduto().getNome()).append(" (")
                  .append(item.getQuantidade()).append("x) = R$ ")
                  .append(String.format("%.2f", item.getProduto().getPrecoFinal() * item.getQuantidade()))
                  .append("\n");
            }
        }
        return sb.toString();
    }
}
