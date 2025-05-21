package services;

import entities.Agenda;
import entities.Cliente;
import entities.Funcionario;
import entities.Mecanico;
import entities.Veiculo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class AgendamentoService {

    private List<Agenda> agendamentos;
    private List<Cliente> clientes;
    private List<Funcionario> funcionarios;

    public AgendamentoService(List<Agenda> agendamentos, List<Cliente> clientes, List<Funcionario> funcionarios) {
        this.agendamentos = agendamentos;
        this.clientes = clientes;
        this.funcionarios = funcionarios;
    }

    public void menuAgendamentos() {
        Scanner sc = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n--- Menu de Agendamentos ---");
            System.out.println("1. Realizar agendamento");
            System.out.println("2. Consultar agendamentos por data");
            System.out.println("3. Listar todos os agendamentos");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1 -> agendarServico();
                case 2 -> consultarAgendaPorData();
                case 3 -> listarTodosAgendamentos();
                case 0 -> System.out.println("Voltando ao menu principal...");
                default -> System.out.println("Opção inválida.");
            }

        } while (opcao != 0);
    }

    public void agendarServico() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n--- Agendar Serviço ---");

        System.out.print("ID do agendamento: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("ID do cliente: ");
        int idCliente = sc.nextInt();
        sc.nextLine();
        Cliente cliente = buscarClientePorId(idCliente);
        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }

        System.out.print("Placa do veículo: ");
        String placa = sc.nextLine();
        Veiculo veiculo = null;
        for (Veiculo v : cliente.getVeiculo()) {
            if (v.getPlaca().equalsIgnoreCase(placa)) {
                veiculo = v;
                break;
            }
        }
        if (veiculo == null) {
            System.out.println("Veículo não encontrado para este cliente.");
            return;
        }

        System.out.print("Descrição do problema: ");
        String problema = sc.nextLine();

        System.out.print("ID do mecânico: ");
        int idMec = sc.nextInt();
        sc.nextLine();
        Mecanico mecanico = null;
        for (Funcionario f : funcionarios) {
            if (f instanceof Mecanico && f.getId() == idMec) {
                mecanico = (Mecanico) f;
                break;
            }
        }
        if (mecanico == null) {
            System.out.println("Mecânico não encontrado.");
            return;
        }

        System.out.print("Data e hora do agendamento (ex: 20/05/2025 14:30): ");
        String dataHoraStr = sc.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime dataHora;

        try {
            dataHora = LocalDateTime.parse(dataHoraStr, formatter);
        } catch (Exception e) {
            System.out.println("Formato de data e hora inválido.");
            return;
        }

        Agenda nova = new Agenda(id, cliente, veiculo, problema, mecanico, dataHora, "Agendado");
        agendamentos.add(nova);
        System.out.println("Agendamento realizado com sucesso!");
    }

    public void consultarAgendaPorData() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Digite a data (dd/MM/yyyy): ");
        String dataStr = sc.nextLine();

        LocalDate data;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            data = LocalDate.parse(dataStr, formatter);
        } catch (Exception e) {
            System.out.println("Formato de data inválido.");
            return;
        }

        boolean encontrado = false;
        System.out.println("\n--- Agendamentos para " + data + " ---");

        for (Agenda a : agendamentos) {
            if (a.getDataAgendamento().toLocalDate().equals(data)) {
                System.out.println(a);
                encontrado = true;
            }
        }

        if (!encontrado) {
            System.out.println("Nenhum agendamento encontrado para essa data.");
        }
    }

    public void listarTodosAgendamentos() {
        if (agendamentos.isEmpty()) {
            System.out.println("Nenhum agendamento cadastrado.");
            return;
        }

        System.out.println("\n--- Lista de Agendamentos ---");
        for (Agenda a : agendamentos) {
            System.out.println(a);
        }
    }

    private Cliente buscarClientePorId(int id) {
        for (Cliente cliente : clientes) {
            if (cliente.getIdCliente() == id) {
                return cliente;
            }
        }
        return null;
    }

    public List<Agenda> getAgendamentos() {
        return agendamentos;
    }
}
