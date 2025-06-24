package services;

import entities.*;
import utils.PersistenciaUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class AgendamentoService {

    private List<Agenda> agendamentos;
    private List<OrdemServico> ordens;
    private ClienteService clienteService;
    private VeiculoService veiculoService;
    private FuncionarioService funcionarioService;
    private ElevadorService elevadorService;
    private ProdutoService produtoService;

    public AgendamentoService(List<Agenda> agendamentos, List<OrdemServico> ordens,
            ClienteService clienteService, VeiculoService veiculoService,
            FuncionarioService funcionarioService, ElevadorService elevadorService,
            ProdutoService produtoService) {
        this.agendamentos = agendamentos;
        this.ordens = ordens;
        this.clienteService = clienteService;
        this.veiculoService = veiculoService;
        this.funcionarioService = funcionarioService;
        this.elevadorService = elevadorService;
        this.produtoService = produtoService;
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
            System.out.print("Escolha uma opcao: ");
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

            salvarDados();

        } while (opcao != 0);
    }

    public void agendarServico() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n--- Agendar Servico ---");

        System.out.print("ID do agendamento: ");
        int id = sc.nextInt();
        sc.nextLine();

        // Cliente
        Cliente cliente;
        System.out.print("Cliente já está cadastrado? (s/n): ");
        String respCliente = sc.nextLine();

        if (respCliente.equalsIgnoreCase("s")) {
            cliente = clienteService.buscarClientePorId();
            if (cliente == null) {
                System.out.println("Cliente não encontrado. Encerrando agendamento.");
                return;
            }
        } else {
            clienteService.cadastrarCliente();
            cliente = clienteService.getUltimoClienteCadastrado();
        }

        // Veículo
        Veiculo veiculo;
        System.out.print("Veículo já está cadastrado? (s/n): ");
        String respVeiculo = sc.nextLine();

        if (respVeiculo.equalsIgnoreCase("s")) {
            veiculo = veiculoService.buscarVeiculoPorCliente(cliente);
            if (veiculo == null) {
                System.out.println("Veículo não encontrado para este cliente. Encerrando agendamento.");
                return;
            }
        } else {
            veiculoService.cadastrarVeiculoParaCliente(cliente);
            veiculo = veiculoService.getUltimoVeiculoCadastrado();
        }

        // Descrição do problema
        System.out.print("Descrição do problema: ");
        String problema = sc.nextLine();

        // Escolher mecânico
        System.out.print("Deseja escolher um mecânico específico? (s/n): ");
        String escolhaMec = sc.nextLine();

        Mecanico mecanico;
        if (escolhaMec.equalsIgnoreCase("s")) {
            List<Mecanico> mecanicos = funcionarioService.listarMecanicos();
            if (mecanicos.isEmpty()) {
                System.out.println("Nenhum mecânico cadastrado. Encerrando agendamento.");
                return;
            }
            System.out.println("\n--- Mecânicos Disponíveis ---");
            for (Mecanico m : mecanicos) {
                System.out.println("ID: " + m.getId() + " | Nome: " + m.getNome() + " | Especialidade: " + m.getEspecialide());
            }
            System.out.print("Digite o ID do mecânico: ");
            int idMec = sc.nextInt();
            sc.nextLine();

            mecanico = funcionarioService.buscarMecanicoPorId(idMec);
            if (mecanico == null) {
                System.out.println("Mecânico não encontrado. Agendamento cancelado.");
                return;
            }
        } else {
            mecanico = funcionarioService.getUltimoMecanicoCadastrado();
            if (mecanico == null) {
                System.out.println("Nenhum mecânico cadastrado. Agendamento cancelado.");
                return;
            }
            System.out.println("Mecânico selecionado automaticamente: " + mecanico.getNome());
        }

        // Data e hora
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

        // Verificar se já existe agendamento ativo no mesmo horário
        for (Agenda a : agendamentos) {
            if (a.getDataAgendamento().equals(dataHora) && !a.getStatus().equalsIgnoreCase("Cancelado")) {
                System.out.println("Já existe um agendamento neste horário. Agendamento cancelado.");
                return;
            }
        }

        // Buscar elevador disponível
        Elevador elevador = elevadorService.buscarElevadorDisponivel();
        if (elevador == null) {
            System.out.println("Nenhum elevador disponível no momento.");
            return;
        }

        // Ocupa elevador
        elevador.setStatus("Ocupado");
        elevador.setVeiculoNaPlataforma(veiculo.getPlaca());
        elevador.setServicoEmExecucao(problema);

        // Criar agendamento
        Agenda agenda = new Agenda(id, cliente, veiculo, problema, mecanico, dataHora, "Agendado", elevador);
        agendamentos.add(agenda);

        // Criar ordem de serviço vinculada
        int idOrdem = ordens.size() + 1;
        OrdemServico ordem = new OrdemServico(
                idOrdem,
                cliente,
                veiculo,
                mecanico,
                elevador,
                dataHora,
                List.of(), // Sem itens por enquanto
                0.0, // Valor da mão de obra inicial
                "Aberta"
        );

        ordens.add(ordem);

        System.out.println("\n--- Agendamento e Ordem de Serviço criados com sucesso! ---");
        System.out.println("Agendamento ID: " + id);
        System.out.println("Ordem de Serviço ID: " + idOrdem);
    }

    public void cancelarAgendamento() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n--- Cancelar Agendamento ---");

        System.out.print("Digite o ID do agendamento: ");
        int id = sc.nextInt();
        sc.nextLine();

        Agenda agendamentoEncontrado = null;

        for (Agenda a : agendamentos) {
            if (a.getId() == id) {
                agendamentoEncontrado = a;
                break;
            }
        }

        if (agendamentoEncontrado != null) {
            // Calcular multa
            System.out.print("Informe o valor estimado do serviço: R$ ");
            double valor = sc.nextDouble();
            sc.nextLine();

            LocalDate hoje = LocalDate.now();
            LocalDate dataAgendada = agendamentoEncontrado.getDataAgendamento().toLocalDate();

            double multa;
            if (dataAgendada.equals(hoje)) {
                multa = valor * 0.5; // 50% no mesmo dia
                System.out.printf("Cancelamento no mesmo dia. Multa de 50%%: R$ %.2f%n", multa);
            } else {
                multa = valor * 0.2; // 20% se antes
                System.out.printf("Cancelamento antecipado. Multa de 20%%: R$ %.2f%n", multa);
            }

            agendamentos.remove(agendamentoEncontrado);

            // Liberar elevador
            Elevador elevador = agendamentoEncontrado.getElevador();
            if (elevador != null) {
                elevador.setStatus("Disponível");
                elevador.setVeiculoNaPlataforma("");
                elevador.setServicoEmExecucao("");
            }

            System.out.println("Agendamento removido com sucesso.");
        } else {
            System.out.println("Agendamento com ID " + id + " não encontrado.");
        }

        salvarDados();
    }

    public void consultarAgendaPorData() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Digite a data (dd/MM/aaaa): ");
        String dataStr = sc.nextLine();

        LocalDate data;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/aaaa");
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

    public void listarMecanicosDisponiveis() {
        List<Mecanico> mecanicos = funcionarioService.listarMecanicos();
        if (mecanicos.isEmpty()) {
            System.out.println("Não há mecânicos cadastrados.");
        } else {
            System.out.println("\n--- Mecânicos Disponíveis ---");
            for (Mecanico m : mecanicos) {
                System.out.println("ID: " + m.getId() + " | Nome: " + m.getNome() + " | Especialidade: " + m.getEspecialide());
            }
        }
    }

    private void salvarDados() {
        PersistenciaUtil.salvarEmArquivo(agendamentos, "agendamentos.json");
        PersistenciaUtil.salvarEmArquivo(ordens, "ordens.json");
    }
}
