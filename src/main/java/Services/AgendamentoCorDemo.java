package services;

import Chains.Agendamento.*;
import entities.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

/**
 * Classe de demonstração do padrão Chain of Responsibility aplicado
 * no processo de agendamento da oficina.
 * Responsável por montar e executar a cadeia de validações.
 * 
 * @author Tiago
 */
public class AgendamentoCorDemo {

    /**
     * Método que executa o processo de agendamento utilizando
     * a cadeia de responsabilidade para validar cliente, veículo,
     * mecânico e data.
     * 
     * @param clientes      Lista de clientes cadastrados.
     * @param funcionarios  Lista de funcionários cadastrados.
     * @param agendamentos  Lista de agendamentos já registrados.
     */
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

        // Definição da cadeia
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
            System.out.println("\n[SUCESSO] Agendamento concluído e salvo com sucesso!");
        } else {
            System.out.println("\n[FALHA] Agendamento cancelado por falha na validacao.");
        }
    }

    /**
     * Busca um cliente pelo ID.
     * 
     * @param clientes Lista de clientes cadastrados.
     * @param id       ID do cliente a ser buscado.
     * @return Cliente encontrado ou null se não existir.
     */
    private Cliente buscarClientePorId(List<Cliente> clientes, int id) {
        for (Cliente c : clientes) {
            if (c.getIdCliente() == id) return c;
        }
        return null;
    }

    /**
     * Busca um veículo do cliente pela placa.
     * 
     * @param cliente Cliente ao qual o veículo deve pertencer.
     * @param placa   Placa do veículo a ser buscado.
     * @return Veículo encontrado ou null se não existir.
     */
    private Veiculo buscarVeiculoDoCliente(Cliente cliente, String placa) {
        for (Veiculo v : cliente.getVeiculo()) {
            if (v.getPlaca().equalsIgnoreCase(placa)) return v;
        }
        return null;
    }

    /**
     * Busca um mecânico pelo ID na lista de funcionários.
     * 
     * @param funcionarios Lista de funcionários cadastrados.
     * @param id           ID do mecânico a ser buscado.
     * @return Mecanico encontrado ou null se não existir.
     */
    private Mecanico buscarMecanicoPorId(List<Funcionario> funcionarios, int id) {
        for (Funcionario f : funcionarios) {
            if (f instanceof Mecanico && f.getId() == id) {
                return (Mecanico) f;
            }
        }
        return null;
    }
}
