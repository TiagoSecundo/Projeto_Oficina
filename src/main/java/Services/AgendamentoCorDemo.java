package services;

import Chains.Agendamento.AgendamentoBaseHandler;
import Chains.Agendamento.AgendamentoHandler;
import Chains.Agendamento.VerificaClienteHandler;
import Chains.Agendamento.VerificaDataHandler;
import Chains.Agendamento.VerificaMecanicoHandler;
import Chains.Agendamento.VerificaVeiculoHandler;
import entities.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class AgendamentoCorDemo {

    public void executarCadeiaAgendamento(List<Cliente> clientes, List<Funcionario> funcionarios, List<Agenda> agendamentos) {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n--- Novo Agendamento (Com Chain of Responsibility) ---");

        System.out.print("ID do agendamento: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("ID do cliente: ");
        int idCliente = sc.nextInt();
        sc.nextLine();

        Cliente cliente = buscarClientePorId(clientes, idCliente);
        if (cliente == null) {
            System.out.println("[ERRO] Cliente nao encontrado.");
            return;
        }

        System.out.print(" Placa do veiculo: ");
        String placa = sc.nextLine();
        Veiculo veiculo = buscarVeiculoDoCliente(cliente, placa);
        if (veiculo == null) {
            System.out.println("[ERRO] Veiculo nao encontrado para este cliente.");
            return;
        }

        System.out.print("Descricao do problema: ");
        String problema = sc.nextLine();

        System.out.print("ID do mecanico: ");
        int idMec = sc.nextInt();
        sc.nextLine();

        Mecanico mecanico = buscarMecanicoPorId(funcionarios, idMec);
        if (mecanico == null) {
            System.out.println("[ERRO] Mecanico nao encontrado.");
            return;
        }

        System.out.print("Data e hora do agendamento (ex: 20/06/2025 14:30): ");
        String dataHoraStr = sc.nextLine();

        LocalDateTime dataHora;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            dataHora = LocalDateTime.parse(dataHoraStr, formatter);
        } catch (Exception e) {
            System.out.println("[ERRO] Formato de data invalido.");
            return;
        }

        Agenda agenda = new Agenda(id, cliente, veiculo, problema, mecanico, dataHora, "Agendado");

        // Definicao da cadeia
        AgendamentoHandler clienteHandler = new VerificaClienteHandler(clientes);
        AgendamentoHandler veiculoHandler = new VerificaVeiculoHandler(clientes);
        AgendamentoHandler mecanicoHandler = new VerificaMecanicoHandler(funcionarios);
        AgendamentoHandler dataHandler = new VerificaDataHandler();

        clienteHandler.setNext(veiculoHandler);
        veiculoHandler.setNext(mecanicoHandler);
        mecanicoHandler.setNext(dataHandler);

        System.out.println("\n>>> Iniciando validação com Chain of Responsibility <<<");

        if (clienteHandler.handle(agenda)) {
            agendamentos.add(agenda);
            System.out.println("\n[SUCESSO] Agendamento concluído e salvo com sucesso!\"");
        } else {
            System.out.println("\n[FALHA]Agendamento cancelado por falha na validacao.");
        }
    }

    private Cliente buscarClientePorId(List<Cliente> clientes, int id) {
        for (Cliente c : clientes) {
            if (c.getIdCliente() == id) return c;
        }
        return null;
    }

    private Veiculo buscarVeiculoDoCliente(Cliente cliente, String placa) {
        for (Veiculo v : cliente.getVeiculo()) {
            if (v.getPlaca().equalsIgnoreCase(placa)) return v;
        }
        return null;
    }

    private Mecanico buscarMecanicoPorId(List<Funcionario> funcionarios, int id) {
        for (Funcionario f : funcionarios) {
            if (f instanceof Mecanico && f.getId() == id) {
                return (Mecanico) f;
            }
        }
        return null;
    }
}
