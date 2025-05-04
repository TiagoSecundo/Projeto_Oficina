package entities;
// agora falta so comentar e passsar as classes pro diagrama
import java.util.ArrayList;
import java.time.LocalDateTime;

public class Agenda {

    private int id;
    private Cliente cliente;
    private Veiculo veiculo;
    private String problemaDescrito;
    private Mecanico mecanicoResponsavel;
    private LocalDateTime dataHora;
    private String status;
    private ArrayList<Servico> agendamentos;
    /**
     * Construtor padrão que inicializa a lista de agendamentos
     */
    public Agenda() {
        this.agendamentos = new ArrayList<>();
    }
    /**
     * Construtor com parâmetros para inicializar todos os atributos da agenda
     * @param id
     * @param cliente
     * @param veiculo
     * @param problemaDescrito
     * @param mecanicoResponsavel
     * @param dataAgendamento
     * @param status 
     */
    public Agenda(int id, Cliente cliente, Veiculo veiculo, String problemaDescrito,
            Mecanico mecanicoResponsavel, LocalDateTime dataAgendamento, String status) {
        this.id = id;
        this.cliente = cliente;
        this.veiculo = veiculo;
        this.problemaDescrito = problemaDescrito;
        this.mecanicoResponsavel = mecanicoResponsavel;
        this.dataHora = dataAgendamento;
        this.status = status;
        this.agendamentos = new ArrayList<>();
    }
    /**
     * Retorna o ID da agenda
     * @return 
     */
    public int getId() {
        return id;
    }
    /**
     * Define o ID da agenda
     * @param id 
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * 
     * @return Retorna o cliente associado ao agendamento
     */
    public Cliente getCliente() {
        return cliente;
    }
    /**
     * Retorna o veículo associado ao agendamento
     * @param cliente 
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    /**
     * Retorna o veículo associado ao agendamento
     * @return 
     */
    public Veiculo getVeiculo() {
        return veiculo;
    }
    /**
     * Define o cliente do agendamento 
     * @param veiculo 
     */
    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }
    /**
     * // Retorna a descrição do problema informado pelo cliente
     * @return 
     */
    public String getProblemaDescrito() {
        return problemaDescrito;
    }
    /**
     * Define a descrição do problema informado pelo cliente
     * @param problemaDescrito 
     */
    public void setProblemaDescrito(String problemaDescrito) {
        this.problemaDescrito = problemaDescrito;
    }
    /**
     * Retorna o mecânico responsável pelo serviço
     * @return 
     */
    public Mecanico getMecanicoResponsavel() {
        return mecanicoResponsavel;
    }
    /**
     * Define o mecânico responsável pelo serviço
     * @param mecanicoResponsavel 
     */
    public void setMecanicoResponsavel(Mecanico mecanicoResponsavel) {
        this.mecanicoResponsavel = mecanicoResponsavel;
    }
    /**
     * Retorna a data e hora do agendamento
     * @return 
     */
    public LocalDateTime getDataAgendamento() {
        return dataHora;
    }
    /**
     * Define a data e hora do agendamento
     * @param dataAgendamento 
     */
    public void setDataAgendamento(LocalDateTime dataAgendamento) {
        this.dataHora = dataAgendamento;
    }
    /**
     * Retorna o status do agendamento 
     * @return 
     */
    public String getStatus() {
        return status;
    }
    /**
     * Define o status do agendamento
     * @param status 
     */
    public void setStatus(String status) {
        this.status = status;
    }
    /**
     * Retorna a lista de serviços agendados
     * @return 
     */
    public ArrayList<Servico> getAgendamentos() {
        return agendamentos;
    }
    /**
     * Define a lista de serviços agendados
     * @param agendamentos 
     */
    public void setAgendamentos(ArrayList<Servico> agendamentos) {
        this.agendamentos = agendamentos;
    }

    /**
     * Retorna uma representação em string da agenda
     * @return 
     */
    @Override
    public String toString() {
        return "Agenda{" + "id=" + id + ", cliente=" + cliente + ", veiculo=" + veiculo + ", problemaDescrito=" + problemaDescrito + ", mecanicoResponsavel=" + mecanicoResponsavel + ", dataHora=" + dataHora + ", status=" + status + ", agendamentos=" + agendamentos + '}';
    }
    
}
