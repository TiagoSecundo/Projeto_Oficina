package services;

import entities.*;

import java.time.LocalDateTime;
import java.util.*;

public class OrdemServicoService {

    private List<OrdemServico> ordensDeServico;
    private ClienteService clienteService;
    private VeiculoService veiculoService;
    private FuncionarioService funcionarioService;
    private ElevadorService elevadorService;
    private ProdutoService produtoService;

    public OrdemServicoService(
            List<OrdemServico> ordensDeServico,
            ClienteService clienteService,
            VeiculoService veiculoService,
            FuncionarioService funcionarioService,
            ElevadorService elevadorService,
            ProdutoService produtoService
    ) {
        this.ordensDeServico = ordensDeServico;
        this.clienteService = clienteService;
        this.veiculoService = veiculoService;
        this.funcionarioService = funcionarioService;
        this.elevadorService = elevadorService;
        this.produtoService = produtoService;
    }

    public void menuOrdemDeServico() {
        Scanner sc = new Scanner(System.in);
        int opcao;
        do {
            System.out.println("\n--- Menu Ordem de Serviço ---");
            System.out.println("1. Criar Ordem de Serviço");
            System.out.println("2. Listar Ordens de Serviço");
            System.out.println("3. Consultar por ID");
            System.out.println("4. Editar Ordem de Servico");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1 ->
                    criarOrdemDeServico();
                case 2 ->
                    listarOrdemDeServico();
                case 3 ->
                    consultarPorId();
                    case 4 -> editarOrdemDeServico();
                case 0 ->
                    System.out.println("Voltando...");
                default ->
                    System.out.println("Opção inválida.");
            }

        } while (opcao != 0);
    }

    public void criarOrdemDeServico() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n--- Criar Ordem de Serviço ---");

        System.out.print("ID da OS: ");
        int id = sc.nextInt();
        sc.nextLine();

        Cliente cliente = clienteService.buscarClientePorId();
        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }

        System.out.print("Placa do veiculo: ");
        String placa = sc.nextLine();
        Veiculo veiculo = veiculoService.buscarVeiculoPorPlacaECliente(placa, cliente);
        if (veiculo == null) {
            System.out.println("Veículo não encontrado para este cliente.");
            return;
        }

        System.out.print("ID do Mecânico: ");
        int idMec = sc.nextInt();
        sc.nextLine();
        Mecanico mecanico = funcionarioService.buscarMecanicoPorId(idMec);
        if (mecanico == null) {
            System.out.println("Mecânico não encontrado.");
            return;
        }

        // Verifica se precisa de elevador
        System.out.print("O serviço necessita de elevador? (s/n): ");
        String resp = sc.nextLine();
        Elevador elevador = null;

        if (resp.equalsIgnoreCase("s")) {
            System.out.print("Tipo do serviço: ");
            String tipoServico = sc.nextLine();

            elevador = elevadorService.buscarElevadorDisponivelParaServico(tipoServico);
            if (elevador == null) {
                System.out.println("Nenhum elevador disponível para este tipo de serviço.");
                return;
            }
            elevador.setStatus("Ocupado");
            elevador.setVeiculoNaPlataforma(veiculo.getPlaca());
            elevador.setServicoEmExecucao("OS ID: " + id);
        }

        LocalDateTime dataHora = LocalDateTime.now();
        List<ItemServico> itens = new ArrayList<>();
        String opcao;
        do {
            System.out.print("ID do Produto: ");
            int idProduto = sc.nextInt();
            sc.nextLine();
            Produto produto = produtoService.buscarProdutoPorId(idProduto);
            if (produto == null) {
                System.out.println("Produto não encontrado.");
            } else {
                System.out.print("Quantidade: ");
                int qtd = sc.nextInt();
                sc.nextLine();
                itens.add(new ItemServico(produto, qtd));
            }
            System.out.print("Adicionar outro produto? (s/n): ");
            opcao = sc.nextLine();
        } while (opcao.equalsIgnoreCase("s"));

        System.out.print("Valor da mão de obra (R$): ");
        double maoDeObra = sc.nextDouble();
        sc.nextLine();

        OrdemServico os = new OrdemServico(
                id, cliente, veiculo, mecanico, elevador, dataHora, itens, maoDeObra, "Aberta"
        );
        ordensDeServico.add(os);

        System.out.println("Ordem de Serviço criada com sucesso!");
        System.out.println(os);
    }

    public void editarOrdemDeServico() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n--- Editar Ordem de Serviço ---");
        System.out.print("Informe o ID da OS: ");
        int id = sc.nextInt();
        sc.nextLine();

        OrdemServico os = null;
        for (OrdemServico ordem : ordensDeServico) {
            if (ordem.getId() == id) {
                os = ordem;
                break;
            }
        }

        if (os == null) {
            System.out.println("Ordem de Serviço com ID " + id + " não encontrada.");
            return;
        }

        System.out.println("\nOrdem Selecionada:");
        System.out.println(os);

        int opcao;
        do {
            System.out.println("\nO que deseja fazer?");
            System.out.println("1. Adicionar Produto");
            System.out.println("2. Alterar Valor de Mão de Obra");
            System.out.println("3. Alterar Status");
            System.out.println("0. Cancelar");
            System.out.print("Escolha: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1 ->
                    adicionarProdutoNaOS(os, sc);
                case 2 ->
                    alterarValorMaoDeObra(os, sc);
                case 3 ->
                    alterarStatus(os, sc);
                case 0 ->
                    System.out.println("Edição finalizada.");
                default ->
                    System.out.println("Opção inválida.");
            }

        } while (opcao != 0);

        double totalProdutos = os.getItensServico().stream()
                .filter(item -> item.getProduto() != null)
                .mapToDouble(item -> item.getProduto().getPrecoFinal() * item.getQuantidade())
                .sum();

        os.setValorTotal(totalProdutos + os.getValorMaoDeObra());
        System.out.println("\n--- Ordem de Serviço Atualizada ---");
        System.out.println(os);
    }

    public void adicionarProdutoNaOS(OrdemServico os, Scanner sc) {
        String continuar = "s";
        do {
            System.out.print("ID do Produto: ");
            int idProduto = sc.nextInt();
            sc.nextLine();

            Produto produto = produtoService.buscarProdutoPorId(idProduto);

            if (produto == null) {
                System.out.println("Produto não encontrado.");
                continue;
            }

            System.out.print("Quantidade: ");
            int quantidade = sc.nextInt();
            sc.nextLine();

            if (produto.getQuantidade() < quantidade) {
                System.out.println("Estoque insuficiente! Disponível: " + produto.getQuantidade());
            } else {
                produtoService.decrementarEstoque(produto, quantidade);

                // Adiciona à OS
                os.getItensServico().add(new ItemServico(produto, quantidade));
                System.out.println("Produto adicionado à OS e estoque atualizado.");
            }

            System.out.print("Adicionar outro produto? (s/n): ");
            continuar = sc.nextLine();
        } while (continuar.equalsIgnoreCase("s"));
    }

    public void alterarValorMaoDeObra(OrdemServico os, Scanner sc) {
        System.out.print("Novo valor de mão de obra (R$): ");
        double novoValor = sc.nextDouble();
        sc.nextLine();
        os.setValorMaoDeObra(novoValor);
        System.out.println("Valor de mão de obra atualizado.");
    }

    public void alterarStatus(OrdemServico os, Scanner sc) {
        System.out.print("Novo status (Aberta, Concluída, Emitida Nota Fiscal): ");
        String status = sc.nextLine();
        os.setStatus(status);
        System.out.println("Status atualizado.");
    }

    public void listarOrdemDeServico() {
        if (ordensDeServico.isEmpty()) {
            System.out.println("Nenhuma ordem de serviço cadastrada.");
            return;
        }
        for (OrdemServico os : ordensDeServico) {
            System.out.println(os);
        }
    }

    public void consultarPorId() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Informe o ID da OS: ");
        int id = sc.nextInt();
        sc.nextLine();

        for (OrdemServico os : ordensDeServico) {
            if (os.getId() == id) {
                System.out.println(os);
                return;
            }
        }
        System.out.println("Ordem de Serviço com ID " + id + " não encontrada.");
    }

    public List<OrdemServico> getOrdensDeServico() {
        return ordensDeServico;
    }
}
