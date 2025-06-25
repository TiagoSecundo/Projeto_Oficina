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
                    System.out.println("Voltando...");
                default ->
                    System.out.println("Opcao invalida.");
            }

            salvarDados();

        } while (opcao != 0);
    }

    public void agendarServico() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n--- Agendar Servico ---");

        int id = solicitarIdAgendamento(sc);
        Cliente cliente = obterCliente(sc);
        if (cliente == null) {
            return;
        }

        Veiculo veiculo = obterVeiculo(sc, cliente);
        if (veiculo == null) {
            return;
        }

        String problema = solicitarDescricaoProblema(sc);
        Mecanico mecanico = selecionarMecanico(sc);
        if (mecanico == null) {
            return;
        }

        LocalDateTime dataHora = solicitarDataHora(sc);
        if (dataHora == null || agendamentoExistente(dataHora)) {
            return;
        }

        System.out.print("O Servico necessita de elevador: (s/n)");
        String resp = sc.nextLine();
        if (resp.equalsIgnoreCase("s")) {
            Elevador elevador = elevadorService.alocarElevador(problema);
            if (elevador == null) {
                return;
            }
            criarAgendamento(id, cliente, veiculo, problema, mecanico, dataHora, elevador);
        }else{
            criarAgendamento(id, cliente, veiculo, problema, mecanico, dataHora, null);
        }

        
    }

    private int solicitarIdAgendamento(Scanner sc) {
        System.out.print("ID do agendamento: ");
        return sc.nextInt();
    }

    private Cliente obterCliente(Scanner sc) {
        sc.nextLine();
        System.out.print("Cliente ja cadastrado? (s/n): ");
        String resp = sc.nextLine();

        if (resp.equalsIgnoreCase("s")) {
            Cliente c = clienteService.buscarClientePorId();
            if (c == null) {
                System.out.println("Cliente nao encontrado.");
            }
            return c;
        } else {
            clienteService.cadastrarCliente();
            return clienteService.getUltimoClienteCadastrado();
        }
    }

    private Veiculo obterVeiculo(Scanner sc, Cliente cliente) {
        System.out.print("Veículo ja cadastrado? (s/n): ");
        String resp = sc.nextLine();

        if (resp.equalsIgnoreCase("s")) {
            Veiculo v = veiculoService.buscarVeiculoPorCliente(cliente);
            if (v == null) {
                System.out.println("Veiculo nao encontrado.");
            }
            return v;
        } else {
            veiculoService.cadastrarVeiculoParaCliente(cliente);
            return veiculoService.getUltimoVeiculoCadastrado();
        }
    }

    private String solicitarDescricaoProblema(Scanner sc) {
        System.out.print("Descricao do problema: ");
        return sc.nextLine();
    }

    private Mecanico selecionarMecanico(Scanner sc) {
        System.out.print("Deseja escolher um mecânico especifico? (s/n): ");
        String resp = sc.nextLine();

        if (resp.equalsIgnoreCase("s")) {
            listarMecanicosDisponiveis();
            System.out.print("Digite o ID do mecanico: ");
            int idMec = sc.nextInt();
            sc.nextLine();
            Mecanico m = funcionarioService.buscarMecanicoPorId(idMec);
            if (m == null) {
                System.out.println("Mecanico nao encontrado.");
            }
            return m;
        } else {
            Mecanico m = funcionarioService.getUltimoMecanicoCadastrado();
            if (m != null) {
                System.out.println("Mecanico selecionado automaticamente: " + m.getNome());
            } else {
                System.out.println("Nenhum mecanico cadastrado.");
            }
            return m;
        }
    }

    private LocalDateTime solicitarDataHora(Scanner sc) {
        System.out.print("Data e hora do agendamento (ex: 20/05/2025 14:30): ");
        String dataStr = sc.nextLine();
        try {
            return LocalDateTime.parse(dataStr, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        } catch (Exception e) {
            System.out.println("Formato invalido.");
            return null;
        }
    }

    private boolean agendamentoExistente(LocalDateTime dataHora) {
        for (Agenda a : agendamentos) {
            if (a.getDataAgendamento().equals(dataHora)
                    && !a.getStatus().equalsIgnoreCase("Cancelado")) {
                System.out.println("Ja existe um agendamento nesse horario.");
                return true;
            }
        }
        return false;
    }

    private Elevador alocarElevador(Veiculo veiculo, String problema) {
        Elevador e = elevadorService.buscarElevadorDisponivel();
        if (e == null) {
            System.out.println("Nenhum elevador disponivel.");
            return null;
        }

        e.setStatus("Ocupado");
        e.setVeiculoNaPlataforma(veiculo.getPlaca());
        e.setServicoEmExecucao(problema);
        return e;
    }

    private void criarAgendamento(int id, Cliente cliente, Veiculo veiculo, String problema,
            Mecanico mecanico, LocalDateTime dataHora, Elevador elevador) {

        Agenda agenda = new Agenda(id, cliente, veiculo, problema, mecanico, dataHora, "Agendado", elevador);
        agendamentos.add(agenda);

        int idOrdem = ordens.size() + 1;
        OrdemServico ordem = new OrdemServico(
                idOrdem,
                cliente,
                veiculo,
                mecanico,
                elevador,
                dataHora,
                List.of(),
                0.0,
                "Aberta"
        );
        ordens.add(ordem);

        System.out.println("\n--- Agendamento e Ordem de Servico criados com sucesso ---");
        System.out.println("Agendamento ID: " + id);
        System.out.println("Ordem de Servico ID: " + idOrdem);
    }

    public void cancelarAgendamento() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n--- Cancelar Agendamento ---");

        System.out.print("Digite o ID do agendamento: ");
        int id = sc.nextInt();
        sc.nextLine();

        Agenda encontrado = null;
        for (Agenda a : agendamentos) {
            if (a.getId() == id) {
                encontrado = a;
                break;
            }
        }

        if (encontrado != null) {
            System.out.print("Informe o valor estimado do servico: R$ ");
            double valor = sc.nextDouble();
            sc.nextLine();

            LocalDate hoje = LocalDate.now();
            LocalDate dataAgendada = encontrado.getDataAgendamento().toLocalDate();

            double multa = (dataAgendada.equals(hoje)) ? valor * 0.5 : valor * 0.2;
            String tipo = (dataAgendada.equals(hoje)) ? "50%" : "20%";

            System.out.printf("Cancelamento com multa de %s: R$ %.2f%n", tipo, multa);

            agendamentos.remove(encontrado);

            Elevador elevador = encontrado.getElevador();
            if (elevador != null) {
                elevador.setStatus("Disponivel");
                elevador.setVeiculoNaPlataforma("");
                elevador.setServicoEmExecucao("");
            }

            System.out.println("Agendamento removido com sucesso.");
        } else {
            System.out.println("Agendamento com ID " + id + " nao encontrado.");
        }

        salvarDados();
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

    public void listarMecanicosDisponiveis() {
        List<Mecanico> mecanicos = funcionarioService.listarMecanicos();
        if (mecanicos.isEmpty()) {
            System.out.println("Nao há mecanicos cadastrados.");
        } else {
            System.out.println("\n--- Mecanicos Disponaveis ---");
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
