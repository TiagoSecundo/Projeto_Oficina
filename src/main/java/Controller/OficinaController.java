package controller;

import entities.*;
import services.*;
import utils.PersistenciaUtil;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OficinaController {

    // Listas
    private List<Cliente> clientes;
    private List<Veiculo> veiculos;
    private List<Produto> produtos;
    private List<Despesas> despesas;
    private List<Funcionario> funcionarios;
    private List<Mecanico> mecanicos;
    private List<Agenda> agendamentos;
    private List<Elevador> elevadores;
    private List<OrdemServico> ordens;
    private List<BalancoMensal> balancos;
    private List<Gerente> gerentes;

    // Serviços
    private ClienteService clienteService;
    private VeiculoService veiculoService;
    private ProdutoService produtoService;
    private EstoqueService estoqueService;
    private DespesaService despesaService;
    private FuncionarioService funcionarioService;
    private AgendamentoService agendamentoService;
    private ElevadorService elevadorService;
    private OrdemServicoService ordemServicoService;
    private BalancoMensalService balancoMensalService;
    private GerenteService gerenteService;

    public OficinaController() {
        // Carregamento dos dados
        this.clientes = carregar("clientes.json", new TypeToken<List<Cliente>>() {
        }.getType());
        this.veiculos = carregar("veiculos.json", new TypeToken<List<Veiculo>>() {
        }.getType());
        this.funcionarios = carregar("funcionarios.json", new TypeToken<List<Funcionario>>() {
        }.getType());
        this.produtos = carregar("produtos.json", new TypeToken<List<Produto>>() {
        }.getType());
        this.agendamentos = carregar("agendamentos.json", new TypeToken<List<Agenda>>() {
        }.getType());
        this.despesas = carregar("despesas.json", new TypeToken<List<Despesas>>() {
        }.getType());
        this.ordens = carregar("ordens.json", new TypeToken<List<OrdemServico>>() {
        }.getType());
        this.balancos = carregar("balancoMensal.json", new TypeToken<List<BalancoMensal>>() {
        }.getType());
        this.gerentes = carregar("gerentes.json", new TypeToken<List<Gerente>>() {
        }.getType());
        this.mecanicos = carregar("mecanicos.json", new TypeToken<List<Mecanico>>() {
        }.getType());

        // Inicialização caso estejam nulos
        if (mecanicos == null) {
            mecanicos = new ArrayList<>();
        }
        if (gerentes == null) {
            gerentes = new ArrayList<>();
        }
        if (clientes == null) {
            clientes = new ArrayList<>();
        }
        if (veiculos == null) {
            veiculos = new ArrayList<>();
        }
        if (funcionarios == null) {
            funcionarios = new ArrayList<>();
        }
        if (produtos == null) {
            produtos = new ArrayList<>();
        }
        if (agendamentos == null) {
            agendamentos = new ArrayList<>();
        }
        if (despesas == null) {
            despesas = new ArrayList<>();
        }
        this.elevadores = new ArrayList<>();
        elevadores.add(new Elevador(1, "Balanceamento")); // Só para balanceamento
        elevadores.add(new Elevador(2, "Geral"));
        elevadores.add(new Elevador(3, "Geral"));
        if (ordens == null) {
            ordens = new ArrayList<>();
        }
        if (balancos == null) {
            balancos = new ArrayList<>();
        }

        this.clienteService = new ClienteService(clientes);
        this.veiculoService = new VeiculoService(veiculos, clientes);
        this.produtoService = new ProdutoService(produtos);
        this.estoqueService = new EstoqueService(produtos);
        this.funcionarios = new ArrayList<>();
        this.funcionarios.addAll(mecanicos);
        this.funcionarios.addAll(gerentes);
        this.funcionarioService = new FuncionarioService(funcionarios, mecanicos);
        this.despesaService = new DespesaService(despesas);
        this.elevadorService = new ElevadorService(elevadores);

        this.ordemServicoService = new OrdemServicoService(
                ordens,
                clienteService,
                veiculoService,
                funcionarioService,
                elevadorService,
                produtoService
        );
                this.agendamentoService = new AgendamentoService(
                agendamentos,
                clienteService,
                veiculoService,
                funcionarioService,
                elevadorService, 
                ordemServicoService
        );
        this.gerenteService = new GerenteService(gerentes);
    }

    public void menuPrincipal() {
        Scanner sc = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1. Clientes");
            System.out.println("2. Veiculos");
            System.out.println("3. Produtos");
            System.out.println("4. Agendamentos");
            System.out.println("5. Estoque");
            System.out.println("6. Mecanicos");
            System.out.println("7. Gerente");
            System.out.println("8. Despesas");
            System.out.println("9. Elevadores");
            System.out.println("10. Ordem de Serviço");
            System.out.println("11. Balanço Mensal");
            System.out.println("0. Sair");
            System.out.print("Escolha: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1 ->
                    clienteService.menuCliente();
                case 2 ->
                    veiculoService.menuVeiculo();
                case 3 ->
                    produtoService.menuProdutos();
                case 4 ->
                    agendamentoService.menuAgendamentos();
                case 5 ->
                    estoqueService.menuEstoque();
                case 6 ->
                    funcionarioService.menuFuncionario();
                case 7 ->
                    gerenteService.menuGerente();
                case 8 ->
                    despesaService.menuDespesas();
                case 9 ->
                    elevadorService.menuElevadores();
                case 10 ->
                    ordemServicoService.menuOrdemDeServico();
                case 11 ->
                    balancoMensalService.gerarBalancoMensal();
                case 0 -> {
                    salvarTudo();
                    System.out.println("Encerrando sistema e salvando dados...");
                }
                default ->
                    System.out.println("Opcao invalida.");
            }
        } while (opcao != 0);
    }

    public void salvarTudo() {
        PersistenciaUtil.salvarEmArquivo(clientes, "clientes.json");
        PersistenciaUtil.salvarEmArquivo(veiculos, "veiculos.json");
        PersistenciaUtil.salvarEmArquivo(funcionarios, "funcionarios.json");
        PersistenciaUtil.salvarEmArquivo(gerentes, "gerentes.json");
        PersistenciaUtil.salvarEmArquivo(produtos, "produtos.json");
        PersistenciaUtil.salvarEmArquivo(agendamentos, "agendamentos.json");
        PersistenciaUtil.salvarEmArquivo(despesas, "despesas.json");;
        PersistenciaUtil.salvarEmArquivo(ordens, "ordens.json");
        PersistenciaUtil.salvarEmArquivo(balancos, "balancoMensal.json");
        PersistenciaUtil.salvarEmArquivo(mecanicos, "mecanicos.json");
        PersistenciaUtil.salvarEmArquivo(gerentes, "gerentes.json");
        System.out.println("Funcionarios salvos com sucesso.");
    }

    private <T> List<T> carregar(String nomeArquivo, Type tipoLista) {
        List<T> dados = PersistenciaUtil.carregarDeArquivo(nomeArquivo, tipoLista);
        if (dados == null) {
            System.out.println("Iniciando lista vazia para " + nomeArquivo);
            return new ArrayList<>();
        } else {
            System.out.println("Dados carregados de " + nomeArquivo + ": " + dados.size());
            return dados;
        }
    }
}
