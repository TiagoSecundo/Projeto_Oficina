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

    public Agenda() {
        this.agendamentos = new ArrayList<>();
    }

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public String getProblemaDescrito() {
        return problemaDescrito;
    }

    public void setProblemaDescrito(String problemaDescrito) {
        this.problemaDescrito = problemaDescrito;
    }

    public Mecanico getMecanicoResponsavel() {
        return mecanicoResponsavel;
    }

    public void setMecanicoResponsavel(Mecanico mecanicoResponsavel) {
        this.mecanicoResponsavel = mecanicoResponsavel;
    }

    public LocalDateTime getDataAgendamento() {
        return dataHora;
    }

    public void setDataAgendamento(LocalDateTime dataAgendamento) {
        this.dataHora = dataAgendamento;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Servico> getAgendamentos() {
        return agendamentos;
    }

    public void setAgendamentos(ArrayList<Servico> agendamentos) {
        this.agendamentos = agendamentos;
    }

    @Override
    public String toString() {
        return "Agenda{" + "id=" + id + ", cliente=" + cliente + ", veiculo=" + veiculo + ", problemaDescrito=" + problemaDescrito + ", mecanicoResponsavel=" + mecanicoResponsavel + ", dataHora=" + dataHora + ", status=" + status + ", agendamentos=" + agendamentos + '}';
    }
    
}
