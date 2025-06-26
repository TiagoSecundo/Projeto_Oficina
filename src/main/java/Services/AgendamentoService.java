package services;

import entities.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException; // Importar para tratamento de exceção mais específico
import java.util.InputMismatchException; // Importar para tratamento de exceção mais específico
import java.util.List;
import java.util.Scanner;

public class AgendamentoService {

    private List<Agenda> agendamentos;
    private List<OrdemServico> ordens;
    private ClienteService clienteService;
    private VeiculoService veiculoService;
    private FuncionarioService funcionarioService;
    private ElevadorService elevadorService;
    private ProdutoService produtoService; // Manter se você ainda usa para criar OS junto com agendamento

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
            sc.nextLine(); // Consumir a quebra de linha

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

<<<<<<< Updated upstream
            salvarDados(); // Salva após cada operação do menu

=======
>>>>>>> Stashed changes
        } while (opcao != 0);
    }

    public void agendarServico() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n--- Agendar Servico ---");

        int id = solicitarIdAgendamento(sc);
        if (id == -1) return; // ID inválido ou duplicado

        Cliente cliente = obterCliente(sc);
        if (cliente == null) {
            return;
        }

        Veiculo veiculo = obterVeiculo(sc, cliente);
        if (veiculo == null) {
            return;
        }

        // ✅ NOVO: Seleção do TipoServico
        TipoServico tipoServico = solicitarTipoServico(sc);
        if (tipoServico == null) {
            System.out.println("Tipo de serviço inválido. Agendamento cancelado.");
            return;
        }

        // Manter a descrição do problema para detalhes (opcional)
        System.out.print("Descricao detalhada do problema (opcional): ");
        String problemaDetalhado = sc.nextLine();


        Mecanico mecanico = selecionarMecanico(sc);
        if (mecanico == null) {
            return;
        }

        LocalDateTime dataHora = solicitarDataHora(sc);
        if (dataHora == null || agendamentoExistente(dataHora)) {
            return;
        }

        Elevador elevadorAlocado = null;
        System.out.print("O Servico necessita de elevador? (s/n): ");
        String resp = sc.nextLine();

        if (resp.equalsIgnoreCase("s")) {
            // ✅ CORREÇÃO: Usar o método alocarElevador do ElevadorService com TipoServico
            elevadorAlocado = elevadorService.alocarElevador(veiculo.getPlaca(), tipoServico);
            if (elevadorAlocado == null) {
                System.out.println("Não foi possível agendar. Nenhum elevador disponível para o serviço solicitado.");
                return; // Impede o agendamento se não há elevador
            }
        }

        // ✅ CORREÇÃO: Passar o TipoServico para o método criarAgendamento
        criarAgendamento(id, cliente, veiculo, problemaDetalhado, mecanico, dataHora, elevadorAlocado, tipoServico);

        System.out.println("Agendamento criado com sucesso!");
        // salvarDados() já é chamado no menuAgendamentos, mas pode ser chamado aqui também se quiser salvar imediatamente.
    }

    private int solicitarIdAgendamento(Scanner sc) {
        System.out.print("ID do agendamento: ");
        try {
            int id = sc.nextInt();
            sc.nextLine(); // Consome a nova linha
            for (Agenda a : agendamentos) {
                if (a.getId() == id) {
                    System.out.println("ID de agendamento já existe. Por favor, escolha outro.");
                    return -1; // Indica ID duplicado/inválido
                }
            }
            return id;
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida para o ID. Por favor, insira um número.");
            sc.nextLine(); // Limpa o buffer do scanner
            return -1;
        }
    }

    private Cliente obterCliente(Scanner sc) {
        // sc.nextLine(); // REMOVIDO: Já deve ter sido consumido pelo método anterior se for o caso
        System.out.print("Cliente ja cadastrado? (s/n): ");
        String resp = sc.nextLine();

        if (resp.equalsIgnoreCase("s")) {
            Cliente c = clienteService.buscarClientePorId(); // Assume que buscarClientePorId já lida com input
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

    // ✅ NOVO MÉTODO: Solicitar TipoServico
    private TipoServico solicitarTipoServico(Scanner sc) {
        System.out.println("--- Tipos de Serviço ---");
        for (int i = 0; i < TipoServico.values().length; i++) {
            System.out.println((i + 1) + ". " + TipoServico.values()[i].getDescricao());
        }
        System.out.print("Escolha o tipo de serviço (número): ");
        try {
            int escolha = sc.nextInt();
            sc.nextLine(); // Consome a quebra de linha
            if (escolha > 0 && escolha <= TipoServico.values().length) {
                return TipoServico.values()[escolha - 1];
            } else {
                System.out.println("Opção inválida.");
                return null;
            }
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, digite um número.");
            sc.nextLine(); // Limpa o buffer do scanner
            return null;
        }
    }


    private String solicitarDescricaoProblema(Scanner sc) {
        // Este método pode ser renomeado para algo como 'solicitarDetalhesServico'
        // pois a categorização principal vem do TipoServico agora.
        System.out.print("Descricao do problema: "); // Usado para detalhes, não para lógica
        return sc.nextLine();
    }

    private Mecanico selecionarMecanico(Scanner sc) {
        System.out.print("Deseja escolher um mecânico especifico? (s/n): ");
        String resp = sc.nextLine();

        if (resp.equalsIgnoreCase("s")) {
            listarMecanicosDisponiveis();
            System.out.print("Digite o ID do mecanico: ");
            try {
                int idMec = sc.nextInt();
                sc.nextLine(); // Consome a nova linha
                Mecanico m = funcionarioService.buscarMecanicoPorId(idMec);
                if (m == null) {
                    System.out.println("Mecanico nao encontrado.");
                }
                return m;
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
                sc.nextLine();
                return null;
            }
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
        } catch (DateTimeParseException e) { // Usar DateTimeParseException mais específico
            System.out.println("Formato invalido. Use DD/MM/YYYY HH:MM.");
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

    // ✅ REMOVIDO: Este método foi movido e refatorado para ElevadorService
    // private Elevador alocarElevador(Veiculo veiculo, String problema) { ... }


    // ✅ CORREÇÃO: Novo construtor com TipoServico
    private void criarAgendamento(int id, Cliente cliente, Veiculo veiculo, String problema,
            Mecanico mecanico, LocalDateTime dataHora, Elevador elevador, TipoServico tipoServico) {

        // ✅ Usar o construtor da Agenda com TipoServico
        Agenda agenda = new Agenda(id, cliente, veiculo, problema, mecanico, dataHora, "Agendado", elevador, tipoServico);
        agendamentos.add(agenda);

        int idOrdem = ordens.size() + 1; // Ou use um gerador de ID mais robusto
        // ✅ Usar o construtor da OrdemServico com TipoServico
        OrdemServico ordem = new OrdemServico(
                idOrdem,
                cliente,
                veiculo,
                mecanico,
                elevador,
                dataHora,
                List.of(), // Lista de itens vazia, pois serão adicionados na OS
                0.0,
                "Aberta",
                tipoServico // ✅ Passar o tipo de serviço
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
        try {
            int id = sc.nextInt();
            sc.nextLine();

            Agenda encontrado = null;
            for (Agenda a : agendamentos) {
                if (a.getId() == id) {
                    encontrado = a;
                    break;
                }
            }

<<<<<<< Updated upstream
            if (encontrado != null) {
                if (encontrado.getStatus().equalsIgnoreCase("Cancelado")) {
                    System.out.println("Agendamento já está cancelado.");
                    return;
                }
                if (encontrado.getStatus().equalsIgnoreCase("Concluído")) {
                    System.out.println("Agendamento já foi concluído e não pode ser cancelado.");
                    return;
                }

                System.out.print("Informe o valor estimado do servico: R$ ");
                double valorEstimado = sc.nextDouble();
                sc.nextLine();

                LocalDate hoje = LocalDate.now();
                LocalDate dataAgendada = encontrado.getDataAgendamento().toLocalDate();
                double multa = 0.0;
                String percentualMulta = "0%";

                // ✅ Lógica da multa corrigida
                if (dataAgendada.isEqual(hoje)) { // Se a data agendada é HOJE
                    multa = valorEstimado * 0.50; // 50% de multa
                    percentualMulta = "50%";
                } else if (dataAgendada.isAfter(hoje)) { // Se a data agendada é FUTURA
                    multa = valorEstimado * 0.20; // 20% de multa
                    percentualMulta = "20%";
                } else { // Se a data agendada já passou (teoricamente, já deveria estar concluído ou em atraso)
                    System.out.println("Este agendamento é de uma data passada e não pode ser cancelado como antecipado.");
                    multa = valorEstimado; // Opcional: ou nenhuma multa, dependendo da regra de negócio
                    percentualMulta = "100% (data passada)";
                }


                System.out.printf("Cancelamento com multa de %s: R$ %.2f%n", percentualMulta, multa);

                encontrado.setStatus("Cancelado"); // ✅ Define o status como Cancelado

                // ✅ Libera o elevador se ele estava alocado
                Elevador elevador = encontrado.getElevador();
                if (elevador != null) {
                    elevadorService.liberarElevadorPorId(elevador.getId()); // Chama o método do serviço
                    System.out.println("Elevador " + elevador.getId() + " liberado.");
                }

                System.out.println("Agendamento " + id + " cancelado com sucesso.");
            } else {
                System.out.println("Agendamento com ID " + id + " nao encontrado.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, digite um número para o ID ou valor.");
            sc.nextLine(); // Limpa o buffer do scanner
=======
            System.out.println("Agendamento removido com sucesso.");
        } else {
            System.out.println("Agendamento com ID " + id + " nao encontrado.");
>>>>>>> Stashed changes
        }
    }

    public void consultarAgendaPorData() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Digite a data (dd/MM/yyyy): ");
        String dataStr = sc.nextLine();

        LocalDate data;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            data = LocalDate.parse(dataStr, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Formato de data invalido. Use dd/MM/yyyy.");
            return;
        }

        boolean encontrado = false;
        System.out.println("\n--- Agendamentos para " + data + " ---");

        for (Agenda a : agendamentos) {
            // Verifica se a data do agendamento está dentro do dia selecionado
            if (!a.getDataAgendamento().toLocalDate().isBefore(data) && !a.getDataAgendamento().toLocalDate().isAfter(data)) {
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
<<<<<<< Updated upstream

    private void salvarDados() {
        PersistenciaUtil.salvarEmArquivo(agendamentos, "agendamentos.json");
        PersistenciaUtil.salvarEmArquivo(ordens, "ordens.json");
    }
}
=======
}
>>>>>>> Stashed changes
