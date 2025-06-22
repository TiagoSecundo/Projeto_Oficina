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
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1 -> criarOrdemDeServico();
                case 2 -> listarOrdemDeServico();
                case 3 -> consultarPorId();
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida.");
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
