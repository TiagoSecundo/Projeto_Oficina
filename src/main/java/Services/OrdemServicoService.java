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
            System.out.println("4. Editar Ordem de Serviço");  // ✅ Novo
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
                case 4 ->
                    editarOrdemDeServico();  // ✅ Novo
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

        Veiculo veiculo = veiculoService.buscarVeiculoPorCliente(cliente);
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

        Elevador elevador = elevadorService.buscarElevadorDisponivel();
        if (elevador == null) {
            System.out.println("Nenhum elevador disponível.");
            return;
        }
        elevador.setStatus("Ocupado");
        elevador.setVeiculoNaPlataforma(veiculo.getPlaca());
        elevador.setServicoEmExecucao("OS ID: " + id);

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

        OrdemServico os = new OrdemServico(id, cliente, veiculo, mecanico, elevador, dataHora, itens, maoDeObra, "Aberta");
        ordensDeServico.add(os);

        System.out.println("Ordem de Serviço criada com sucesso!");
        System.out.println(os);
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

        System.out.println("\nO que deseja fazer?");
        System.out.println("1. Adicionar Produto");
        System.out.println("2. Alterar Valor de Mão de Obra");
        System.out.println("3. Alterar Status");
        System.out.println("0. Cancelar");
        System.out.print("Escolha: ");
        int opcao = sc.nextInt();
        sc.nextLine();

        switch (opcao) {
            case 1 -> {
                String continuar;
                do {
                    System.out.print("ID do Produto: ");
                    int idProduto = sc.nextInt();
                    sc.nextLine();
                    Produto produto = produtoService.buscarProdutoPorId(idProduto);
                    if (produto == null) {
                        System.out.println("Produto não encontrado.");
                    } else {
                        System.out.print("Quantidade: ");
                        int quantidade = sc.nextInt();
                        sc.nextLine();
                        os.getItensServico().add(new ItemServico(produto, quantidade));
                        System.out.println("Produto adicionado!");
                    }
                    System.out.print("Adicionar outro produto? (s/n): ");
                    continuar = sc.nextLine();
                } while (continuar.equalsIgnoreCase("s"));
            }

            case 2 -> {
                System.out.print("Novo valor de mão de obra: R$ ");
                double novoValor = sc.nextDouble();
                sc.nextLine();
                os.setValorMaoDeObra(novoValor);
                System.out.println("Valor de mão de obra atualizado.");
            }

            case 3 -> {
                System.out.print("Novo status (Aberta, Concluída, Emitida Nota Fiscal): ");
                String status = sc.nextLine();
                os.setStatus(status);
                System.out.println("Status atualizado.");
            }

            case 0 ->
                System.out.println("Edição cancelada.");

            default ->
                System.out.println("Opção inválida.");
                
        }



        System.out.println("\n--- Ordem de Serviço Atualizada ---");
        System.out.println(os);
    }

    private int gerarIdOrdemServicoUnico() {
        int id = 1;
        while (true) {
            boolean existe = false;
            for (OrdemServico o : ordensDeServico) {
                if (o.getId() == id) {
                    existe = true;
                    id++;
                    break;
                }
            }
            if (!existe) {
                break;
            }
        }
        return id;
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
