package services;

import entities.*;
import utils.PersistenciaUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class OrdemServicoService {

    private List<OrdemServico> ordensDeServico;
    private ClienteService clienteService;
    private VeiculoService veiculoService;
    private FuncionarioService funcionarioService;
    private ElevadorService elevadorService;
    private ProdutoService produtoService;
    private EstoqueService estoqueService;

    public OrdemServicoService(
            List<OrdemServico> ordensDeServico,
            ClienteService clienteService,
            VeiculoService veiculoService,
            FuncionarioService funcionarioService,
            ElevadorService elevadorService,
            ProdutoService produtoService,
            EstoqueService estoqueService
    ) {
        this.ordensDeServico = ordensDeServico;
        this.clienteService = clienteService;
        this.veiculoService = veiculoService;
        this.funcionarioService = funcionarioService;
        this.elevadorService = elevadorService;
        this.produtoService = produtoService;
        this.estoqueService = estoqueService;
    }

    public void menuOrdemDeServico() {
        Scanner sc = new Scanner(System.in);
        int opcao;
        do {
            System.out.println("\n--- Menu Ordem de Serviço ---");
            System.out.println("1. Criar Ordem de Serviço");
            System.out.println("2. Listar Ordens de Serviço");
            System.out.println("3. Consultar por ID");
            System.out.println("4. Editar Ordem de Serviço");
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
                    editarOrdemDeServico();
                case 0 ->
                    System.out.println("Voltando...");
                default ->
                    System.out.println("Opção inválida.");
            }
            PersistenciaUtil.salvarEmArquivo(ordensDeServico, "ordens.json");

        } while (opcao != 0);
    }

    public void criarOrdemDeServico() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n--- Criar Ordem de Serviço ---");

        int id = solicitarIdOrdemServico(sc);
        if (id == -1) return;

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

        Mecanico mecanico = funcionarioService.buscarMecanicoPorId(solicitarIdMecanico(sc));
        if (mecanico == null) {
            System.out.println("Mecânico não encontrado.");
            return;
        }

        TipoServico tipoServico = solicitarTipoServico(sc);
        if (tipoServico == null) {
            System.out.println("Tipo de serviço inválido. Ordem de Serviço cancelada.");
            return;
        }

        Elevador elevador = null;
        System.out.print("O serviço necessita de elevador? (s/n): ");
        String respElevador = sc.nextLine();

        if (respElevador.equalsIgnoreCase("s")) {
            elevador = elevadorService.alocarElevador(veiculo.getPlaca(), tipoServico);
            if (elevador == null) {
                System.out.println("Nenhum elevador disponível para esta Ordem de Serviço.");
                return;
            }
        }

        LocalDateTime dataHora = LocalDateTime.now();

        List<ItemServico> itens = coletarItensServico(sc);
        double maoDeObra = solicitarValorMaoDeObra(sc);

        OrdemServico os = new OrdemServico(id, cliente, veiculo, mecanico, elevador, dataHora, itens, maoDeObra, "Aberta", tipoServico);
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

                        if (estoqueService != null) {
                            if (produto.getQuantidade() >= quantidade) {
                                os.getItensServico().add(new ItemServico(produto, quantidade));
                                estoqueService.decrementarEstoque(produto, quantidade);
                                System.out.println("Produto adicionado e estoque atualizado!");
                            } else {
                                System.out.println("Estoque insuficiente para o produto: " + produto.getNome() + ". Quantidade disponivel: " + produto.getQuantidade());
                            }
                        } else {
                            System.out.println("Erro: Serviço de Estoque não disponível para decrementar.");
                            os.getItensServico().add(new ItemServico(produto, quantidade));
                        }
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
                if (status.equalsIgnoreCase("Concluída") || status.equalsIgnoreCase("Emitida Nota Fiscal")) {
                    if (os.getElevador() != null) {
                        elevadorService.liberarElevadorPorId(os.getElevador().getId());
                        System.out.println("Elevador " + os.getElevador().getId() + " liberado devido à conclusão da OS.");
                    }
                }
            }

            case 0 ->
                System.out.println("Edição cancelada.");

            default ->
                System.out.println("Opção inválida.");

        }

        System.out.println("\n--- Ordem de Serviço Atualizada ---");
        System.out.println(os);
    }

    // ✅ NOVO MÉTODO AUXILIAR
    private int solicitarIdOrdemServico(Scanner sc) {
        System.out.print("ID da OS: ");
        try {
            int id = sc.nextInt();
            sc.nextLine();
            for (OrdemServico os : ordensDeServico) {
                if (os.getId() == id) {
                    System.out.println("ID de Ordem de Serviço já existe. Por favor, escolha outro.");
                    return -1;
                }
            }
            return id;
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida para o ID. Por favor, insira um número.");
            sc.nextLine();
            return -1;
        }
    }

    // ✅ NOVO MÉTODO AUXILIAR: Reutilizado de AgendamentoService (ou comum)
    private TipoServico solicitarTipoServico(Scanner sc) {
        System.out.println("--- Tipos de Serviço ---");
        for (int i = 0; i < TipoServico.values().length; i++) {
            System.out.println((i + 1) + ". " + TipoServico.values()[i].getDescricao());
        }
        System.out.print("Escolha o tipo de serviço (número): ");
        try {
            int escolha = sc.nextInt();
            sc.nextLine();
            if (escolha > 0 && escolha <= TipoServico.values().length) {
                return TipoServico.values()[escolha - 1];
            } else {
                System.out.println("Opção inválida.");
                return null;
            }
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, digite um número.");
            sc.nextLine();
            return null;
        }
    }

    // ✅ NOVO MÉTODO AUXILIAR
    private int solicitarIdMecanico(Scanner sc) {
        System.out.print("ID do Mecânico: ");
        try {
            int idMec = sc.nextInt();
            sc.nextLine();
            return idMec;
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida para o ID do Mecânico. Por favor, insira um número.");
            sc.nextLine();
            return -1;
        }
    }

    // ✅ CORREÇÃO AQUI: Inicializar 'opcao' antes do loop
    private List<ItemServico> coletarItensServico(Scanner sc) {
        List<ItemServico> itens = new ArrayList<>();
        String opcao = ""; // ✅ Inicializa a variável 'opcao'
        do {
            System.out.print("ID do Produto: ");
            int idProduto = -1;
            try {
                idProduto = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida para o ID do Produto. Por favor, insira um número.");
                sc.nextLine();
                opcao = "s"; // Para continuar o loop após erro de input
                continue;
            }

            Produto produto = produtoService.buscarProdutoPorId(idProduto);
            if (produto == null) {
                System.out.println("Produto não encontrado.");
            } else {
                System.out.print("Quantidade: ");
                int qtd = -1;
                try {
                    qtd = sc.nextInt();
                    sc.nextLine();
                } catch (InputMismatchException e) {
                    System.out.println("Entrada inválida para a Quantidade. Por favor, insira um número.");
                    sc.nextLine();
                    opcao = "s"; // Para continuar o loop após erro de input
                    continue;
                }

                if (estoqueService != null) {
                    if (produto.getQuantidade() >= qtd) {
                        itens.add(new ItemServico(produto, qtd));
                        estoqueService.decrementarEstoque(produto, qtd);
                        System.out.println("Estoque do produto '" + produto.getNome() + "' atualizado.");
                    } else {
                        System.out.println("Estoque insuficiente para o produto: " + produto.getNome() + ". Quantidade disponivel: " + produto.getQuantidade());
                    }
                } else {
                    System.out.println("Erro: Serviço de Estoque não disponível para decrementar.");
                    itens.add(new ItemServico(produto, qtd));
                }
            }
            System.out.print("Adicionar outro produto? (s/n): ");
            opcao = sc.nextLine();
        } while (opcao.equalsIgnoreCase("s"));
        return itens;
    }

    // ✅ NOVO MÉTODO AUXILIAR
    private double solicitarValorMaoDeObra(Scanner sc) {
        System.out.print("Valor da mão de obra (R$): ");
        try {
            double maoDeObra = sc.nextDouble();
            sc.nextLine();
            return maoDeObra;
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida para o valor da mão de obra. Por favor, insira um número.");
            sc.nextLine();
            return 0.0;
        }
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