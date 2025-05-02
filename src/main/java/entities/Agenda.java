package entities;

import java.util.ArrayList;
import java.time.LocalDate;

public class Agenda {

    private int id;
    private Cliente cliente;
    private Veiculo veiculo;
    private String problemaDescrito;
    private Mecanico mecanicoResponsavel;
    private LocalDate dataAgendamento;
    private String status;

    public Agenda() {
    }

    public Agenda(int id, Cliente cliente, Veiculo veiculo, String problemaDescrito,
            Mecanico mecanicoResponsavel, LocalDate dataAgendamento, String status) {
        this.id = id;
        this.cliente = cliente;
        this.veiculo = veiculo;
        this.problemaDescrito = problemaDescrito;
        this.mecanicoResponsavel = mecanicoResponsavel;
        this.dataAgendamento = dataAgendamento;
        this.status = status;
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

    public LocalDate getDataAgendamento() {
        return dataAgendamento;
    }

    public void setDataAgendamento(LocalDate dataAgendamento) {
        this.dataAgendamento = dataAgendamento;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
