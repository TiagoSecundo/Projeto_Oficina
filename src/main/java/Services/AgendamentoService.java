package services;

import entities.Agenda;
import entities.Cliente;
import entities.Funcionario;
import entities.Mecanico;
import entities.Veiculo;
import entities.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class AgendamentoService {

    private List<Agenda> agendamentos;
    private ClienteService clienteService;
    private VeiculoService veiculoService;
    private FuncionarioService funcionarioService;
    private ElevadorService elevadorService;
    private OrdemServicoService ordemServicoService;

    public AgendamentoService(List<Agenda> agendamentos, ClienteService clienteService, VeiculoService veiculoService,
            FuncionarioService funcionarioService, ElevadorService elevadorService, OrdemServicoService ordemServicoService) {
        this.agendamentos = agendamentos;
        this.clienteService = clienteService;
        this.veiculoService = veiculoService;
        this.funcionarioService = funcionarioService;
        this.elevadorService = elevadorService;
        this.ordemServicoService = ordemServicoService;
        if (ordemServicoService == null) {
            System.out.println("ERRO: ordemServicoService ainda está NULL no AgendamentoService!");
        } else {
            System.out.println("V: ordemServicoService foi recebida corretamente.");
        }
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

        for (Agenda a : agendamentos) {
            if (a.getId() == id) {
                System.out.println("Ja existe um agendamento com esse ID. Cancelando operacao.");
                return;
            }
        }

        Cliente cliente;
        System.out.print("Cliente ja esta cadastrado? (s/n): ");
        String respCliente = sc.nextLine();

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

        Veiculo veiculo;
        System.out.print("Veiculo ja esta cadastrado? (s/n): ");
        String respVeiculo = sc.nextLine();

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

        System.out.print("Descricao do problema apresentado pelo cliente: ");
        String problema = sc.nextLine();

        List<Mecanico> mecanicos = funcionarioService.getMecanicos();
        if (mecanicos.isEmpty()) {
            System.out.println("Nao ha mecanicos cadastrados no sistema. Agendamento cancelado.");
            return;
        }

        System.out.println("Mecanicos disponiveis:");
        for (Mecanico m : mecanicos) {
            System.out.println("ID: " + m.getId() + " - Nome: " + m.getNome() + " - Especialidade: " + m.getEspecialidade());
        }

        System.out.print("Digite o ID do mecanico desejado: ");
        int idMec = sc.nextInt();
        sc.nextLine();

        Mecanico mecanico = funcionarioService.buscarMecanicoPorId(idMec);
        if (mecanico == null) {
            System.out.println("Mecanico nao encontrado. Agendamento cancelado.");
            return;
        }

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

        for (Agenda a : agendamentos) {
            if (a.getDataAgendamento().equals(dataHora)) {
                System.out.println("Ja existe um agendamento neste horario. Agendamento cancelado.");
                return;
            }
        }

        System.out.print("O servico necessita de elevador? (s/n): ");
        String resp = sc.nextLine();
        Elevador elevador = null;

        if (resp.equalsIgnoreCase("s")) {
            elevador = elevadorService.buscarElevadorDisponivelParaServico(problema);
            if (elevador == null) {
                System.out.println("Nenhum elevador disponivel no momento. Agendamento cancelado.");
                return;
            }
            elevador.setStatus("Ocupado");
            elevador.setVeiculoNaPlataforma(veiculo.getPlaca());
            elevador.setServicoEmExecucao(problema);
        }

        Agenda agenda = new Agenda(id, cliente, veiculo, problema, mecanico, dataHora, "Agendado", elevador);
        agendamentos.add(agenda);
        System.out.println("Deseja gerar automaticamente a ordem de servico para este agendamento? (s/n)");
        String gerarOS = sc.nextLine();
        if (gerarOS.equalsIgnoreCase("s")) {
            OrdemServico os = new OrdemServico(
                    gerarIdOrdemServico(), // crie ou use uma função para gerar novo ID
                    cliente,
                    veiculo,
                    mecanico,
                    elevador,
                    LocalDateTime.now(),
                    new ArrayList<>(), // itens em branco por padrão
                    0.0, // valor inicial da mão de obra
                    "Aberta"
            );
            ordemServicoService.getOrdensDeServico().add(os);
            System.out.println("Ordem de servico gerada com sucesso para o agendamento.");
        }

        System.out.println("\n--- Agendamento realizado com sucesso! ---");
        System.out.println("Cliente: " + cliente.getNome());
        System.out.println("Veiculo: " + veiculo.getModelo() + " - " + veiculo.getPlaca());
        System.out.println("Mecanico: " + mecanico.getNome());
        if (elevador != null) {
            System.out.println("Elevador utilizado: " + elevador.getId());
        } else {
            System.out.println("Elevador: nao necessario");
        }
        System.out.println("Data e hora: " + dataHora.format(formatter));
    }

    private int gerarIdOrdemServico() {
        return ordemServicoService.getOrdensDeServico().stream()
                .mapToInt(OrdemServico::getId)
                .max().orElse(0) + 1;
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

        if (agendamentoEncontrado == null) {
            System.out.println("Agendamento com ID " + id + " nao encontrado.");
            return;
        }

        LocalDateTime agora = LocalDateTime.now();
        LocalDate dataHoje = agora.toLocalDate();
        LocalDate dataAgendamento = agendamentoEncontrado.getDataAgendamento().toLocalDate();

        System.out.print("Informe o valor estimado do servico (ou 0 se nao houver): ");
        double valor = sc.nextDouble();
        sc.nextLine();

        if (valor > 0) {
            if (dataHoje.equals(dataAgendamento)) {
                double multa = valor * 0.50;
                System.out.printf("Cancelamento no mesmo dia. Multa de 50%%: R$ %.2f\n", multa);
            } else {
                double multa = valor * 0.20;
                System.out.printf("Cancelamento antecipado. Multa de 20%%: R$ %.2f\n", multa);
            }
        } else {
            System.out.println("Cancelamento sem multa.");
        }

        // ✅ Liberar elevador, se tiver
        Elevador elevador = agendamentoEncontrado.getElevador();
        if (elevador != null) {
            elevador.setStatus("Disponivel");
            elevador.setVeiculoNaPlataforma(null);
            elevador.setServicoEmExecucao(null);
        }

        // ✅ Marcar como cancelado OU remover da lista
        agendamentoEncontrado.setStatus("Cancelado");
        // agendamentos.remove(agendamentoEncontrado); // Se preferir remover em vez de marcar

        System.out.println("Agendamento cancelado com sucesso.");
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

    public void criarOrdemDeServicoAutomatica(Agenda agenda) {
        List<ItemServico> itens = new ArrayList<>(); // começa vazia
        OrdemServico os = new OrdemServico(
                agenda.getId(), // mesmo ID do agendamento
                agenda.getCliente(),
                agenda.getVeiculo(),
                agenda.getMecanicoResponsavel(),
                agenda.getElevador(),
                LocalDateTime.now(),
                itens,
                0.0, // valor inicial da mão de obra
                "Aberta"
        );
        ordemServicoService.getOrdensDeServico().add(os);
        System.out.println("Ordem de serviço criada automaticamente após o agendamento.");
    }

    public List<Agenda> getAgendamentos() {
        return agendamentos;
    }
}
