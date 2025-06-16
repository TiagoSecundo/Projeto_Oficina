package services;

import entities.Agenda;
import entities.Cliente;
import entities.Funcionario;
import entities.Mecanico;
import entities.Veiculo;

import Chains.Agendamento.AgendamentoBaseHandler;
import Chains.Agendamento.AgendamentoHandler;
import Chains.Agendamento.VerificaClienteHandler;
import Chains.Agendamento.VerificaDataHandler;
import Chains.Agendamento.VerificaMecanicoHandler;
import Chains.Agendamento.VerificaVeiculoHandler;

import entities.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class AgendamentoService {

    private List<Agenda> agendamentos;
    private ClienteService clienteService;
    private VeiculoService veiculoService;
    private FuncionarioService funcionarioService;

    public AgendamentoService(List<Agenda> agendamentos, ClienteService clienteService, VeiculoService veiculoService, FuncionarioService funcionarioService) {
        this.agendamentos = agendamentos;
        this.clienteService = clienteService;
        this.veiculoService = veiculoService;
        this.funcionarioService = funcionarioService;
        this.agendamentos = agendamentos;

    }

    public void menuAgendamentos() {
        Scanner sc = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n--- Menu de Agendamentos ---");
            System.out.println("1. Realizar agendamento");
            System.out.println("2. Consultar agendamentos por data");
            System.out.println("3. Listar todos os agendamentos");
            System.out.println("4. Cancelar agendamento");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1 ->
                    agendarServico();
                case 2 ->
                    consultarAgendaPorData();
                case 3 ->
                    listarTodosAgendamentos();
                case 4 ->
                    cancelarAgendamento();
                case 0 ->
                    System.out.println("Voltando ao menu principal...");
                default ->
                    System.out.println("Opcao invalida.");
            }

        } while (opcao != 0);
    }

    public void agendarServico() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n--- Agendar Servico ---");

        System.out.print("ID do agendamento: ");
        int id = sc.nextInt();
        sc.nextLine();

        // Cliente
        System.out.print("Cliente ja está cadastrado? (s/n): ");
        String respCliente = sc.nextLine();

        Cliente cliente;
        if (respCliente.equalsIgnoreCase("s")) {
            cliente = clienteService.buscarClientePorId();
            if (cliente == null) {
                System.out.println("Cliente nao encontrado. Encerrando agendamento.");
                return;
            }
        } else {
            clienteService.cadastrarCliente();
            cliente = clienteService.getUltimoClienteCadastrado();
        }

        // Veículo
        System.out.print("Veiculo já esta cadastrado? (s/n): ");
        String respVeiculo = sc.nextLine();

        Veiculo veiculo;
        if (respVeiculo.equalsIgnoreCase("s")) {
            veiculo = veiculoService.buscarVeiculoPorCliente(cliente);
            if (veiculo == null) {
                System.out.println("Veiculo nao encontrado para este cliente. Encerrando agendamento.");
                return;
            }
        } else {
            veiculoService.cadastrarVeiculoParaCliente(cliente);
            veiculo = veiculoService.getUltimoVeiculoCadastrado();
        }

        // Descrição
        System.out.print("Descricao do problema: ");
        String problema = sc.nextLine();

        // Funcionário
        System.out.print("ID do mecanico: ");
        int idMec = sc.nextInt();
        sc.nextLine();

        Mecanico mecanico = funcionarioService.buscarMecanicoPorId(idMec);
        if (mecanico == null) {
            System.out.print("Mecanico nao encontrado. Deseja cadastrar um novo? (s/n): ");
            String respFunc = sc.nextLine();
            if (respFunc.equalsIgnoreCase("s")) {
                funcionarioService.cadastrarFuncionario();
                mecanico = funcionarioService.getUltimoMecanicoCadastrado();
            } else {
                System.out.println("Agendamento cancelado.");
                return;
            }
        }

        // Data e hora
        System.out.print("Data e hora do agendamento (ex: 20/05/2025 14:30): ");
        String dataHoraStr = sc.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime dataHora;

        try {
            dataHora = LocalDateTime.parse(dataHoraStr, formatter);
        } catch (Exception e) {
            System.out.println("Formato de data e hora invalido.");
            return;
        }

    }

    public void cancelarAgendamento() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n--- Cancelar Agendamento ---");

        System.out.print("Digite o ID do agendamento: ");
        int id = sc.nextInt();
        sc.nextLine();

        for (Agenda a : agendamentos) {
            if (a.getId() == id) {
                a.setStatus("Cancelado");

                // Exemplo: se houver valor associado ao serviço.
                System.out.print("Informe o valor estimado do serviço (ou 0 se não houver): ");
                double valor = sc.nextDouble();
                sc.nextLine();

                if (valor > 0) {
                    double multa = valor * 0.20;
                    System.out.printf("Agendamento cancelado. Multa de 20%%: R$ %.2f\n", multa);
                } else {
                    System.out.println("Agendamento cancelado sem multa.");
                }
                return;
            }
        }
        System.out.println("Agendamento com ID " + id + " não encontrado.");
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
            System.out.println("Formato de data invalido.");
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

    public List<Agenda> getAgendamentos() {
        return agendamentos;
    }

}
