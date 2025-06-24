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

    public OrdemServico(int id, Cliente cliente, Veiculo veiculo, Mecanico mecanico,
                           Elevador elevador, LocalDateTime dataHora, List<ItemServico> itensServico,
                           double valorMaoDeObra, String status) {
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
    
    /**
     * 
     * @return 
     */
    @Override
    public String toString() {
        return "\n--- ORDEM DE SERVIÇO ---" +
                "\nID: " + id +
                "\nCliente: " + cliente.getNome() +
                "\nVeículo: " + veiculo.getModelo() + " | Placa: " + veiculo.getPlaca() +
                "\nMecânico: " + mecanico.getNome() +
                "\nElevador: " + elevador.getId() +
                "\nData/Hora: " + dataHora +
                "\nItens de Serviço: " + itensServico +
                "\nMão de Obra: R$ " + valorMaoDeObra +
                "\nValor Total: R$ " + valorTotal +
                "\nStatus: " + status +
                "\n-------------------------\n";
    }
}
