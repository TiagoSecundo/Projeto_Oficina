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

  
    private List<Cliente> clientes;
    private List<Veiculo> veiculos;
    private List<Produto> produtos;
    private List<Despesas> despesas;
    private List<Funcionario> funcionarios;
    private List<Gerente> gerentes;
    private List<Agenda> agendamentos;
    private List<Elevador> elevadores;
    private List<OrdemServico> ordens;
    private List<BalancoMensal> balancos;


    private ClienteService clienteService;
    private VeiculoService veiculoService;
    private ProdutoService produtoService;
    private EstoqueService estoqueService;
    private DespesaService despesaService;
    private FuncionarioService funcionarioService;
    private GerenteService gerenteService;
    private AgendamentoService agendamentoService;
    private ElevadorService elevadorService;
    private OrdemServicoService ordemServicoService;
    private BalancoMensalService balancoMensalService;

    public OficinaController() {

        this.clientes = carregar("clientes.json", new TypeToken<List<Cliente>>() {
        }.getType());
        this.veiculos = carregar("veiculos.json", new TypeToken<List<Veiculo>>() {
        }.getType());
        List<Mecanico> mecanicos = carregar("mecanicos.json", new TypeToken<List<Mecanico>>() {
        }.getType());
        List<Gerente> gerentesCarregados = carregar("gerentes.json", new TypeToken<List<Gerente>>() { // Renomeado para evitar conflito
        }.getType());

        this.funcionarios = new ArrayList<>();
        if (mecanicos != null) {
            this.funcionarios.addAll(mecanicos);
        }
        if (gerentesCarregados != null) { 
            this.funcionarios.addAll(gerentesCarregados);
        }

        this.gerentes = gerentesCarregados != null ? gerentesCarregados : new ArrayList<>();

        this.produtos = carregar("produtos.json", new TypeToken<List<Produto>>() {
        }.getType());
        this.agendamentos = carregar("agendamentos.json", new TypeToken<List<Agenda>>() {
        }.getType());
        this.despesas = carregar("despesas.json", new TypeToken<List<Despesas>>() {
        }.getType());
        this.elevadores = carregar("elevadores.json", new TypeToken<List<Elevador>>() {
        }.getType());
        this.ordens = carregar("ordens.json", new TypeToken<List<OrdemServico>>() {
        }.getType());
        this.balancos = carregar("balancoMensal.json", new TypeToken<List<BalancoMensal>>() {
        }.getType());

        if (this.clientes == null) { 
            this.clientes = new ArrayList<>();
        }
        if (this.veiculos == null) { 
            this.veiculos = new ArrayList<>();
        }
        if (this.funcionarios == null) { 
            this.funcionarios = new ArrayList<>();
        }
        if (this.gerentes == null) { 
            this.gerentes = new ArrayList<>();
        }
        if (this.produtos == null) { 
            this.produtos = new ArrayList<>();
        }
        if (this.agendamentos == null) { 
            this.agendamentos = new ArrayList<>();
        }
        if (this.despesas == null) { // Usar this.despesas
            this.despesas = new ArrayList<>();
        }
        if (this.elevadores == null || this.elevadores.isEmpty()) { // Usar this.elevadores
            this.elevadores = new ArrayList<>();

            Elevador elevador1 = new Elevador(1);
            Elevador elevador2 = new Elevador(2);
            Elevador elevador3 = new Elevador(3);
            elevador3.setExclusivoBalanceamento(true);

            this.elevadores.add(elevador1); // Usar this.elevadores
            this.elevadores.add(elevador2); // Usar this.elevadores
            this.elevadores.add(elevador3); // Usar this.elevadores
            System.out.println("Elevadores padrao criados e inicializados.");
            PersistenciaUtil.salvarEmArquivo(this.elevadores, "elevadores.json");
        } else {
            boolean foundBalanceamentoElevator = false;
            for (Elevador elevador : this.elevadores) {
                if (elevador.getId() == 3) { // Supondo que ID 3 é o elevador de balanceamento
                    if (!elevador.isExclusivoBalanceamento()) {
                        elevador.setExclusivoBalanceamento(true);
                        System.out.println("Elevador 3 (balanceamento) marcado como exclusivo.");
                    }
                    foundBalanceamentoElevator = true;
                }
            }
            // Se por algum motivo o elevador de balanceamento (ID 3) não foi encontrado no JSON carregado,
            // você pode optar por criá-lo aqui, ou considerar que o arquivo JSON está malformado.
            if (!foundBalanceamentoElevator) {
                System.out.println("Atenção: Elevador de balanceamento (ID 3) não encontrado no arquivo carregado. Criando...");
                Elevador elevador3 = new Elevador(3);
                elevador3.setExclusivoBalanceamento(true);
                this.elevadores.add(elevador3);
                // Pode ser necessário reordenar ou verificar duplicatas após adicionar
            }
            // Salvar para persistir a correção (se houver)
            PersistenciaUtil.salvarEmArquivo(this.elevadores, "elevadores.json");
        }


        if (this.ordens == null) { // Usar this.ordens
            this.ordens = new ArrayList<>();
        }
        if (this.balancos == null) { // Usar this.balancos
            this.balancos = new ArrayList<>();
        }

        // Instanciar serviços
        this.clienteService = new ClienteService(this.clientes);
        this.veiculoService = new VeiculoService(this.veiculos, this.clientes);
        this.produtoService = new ProdutoService(this.produtos);
        this.estoqueService = new EstoqueService(this.produtos);
        this.funcionarioService = new FuncionarioService(this.funcionarios);
        this.gerenteService = new GerenteService(this.gerentes);
        this.despesaService = new DespesaService(this.despesas);
        this.elevadorService = new ElevadorService(this.elevadores); // Passar a lista de elevadores corrigida
        this.agendamentoService = new AgendamentoService(
                this.agendamentos, this.ordens,
                this.clienteService, this.veiculoService,
                this.funcionarioService, this.elevadorService,
                this.produtoService
        );
        this.ordemServicoService = new OrdemServicoService(
                this.ordens, this.clienteService, this.veiculoService, this.funcionarioService, this.elevadorService, this.produtoService, this.estoqueService
        );
        this.balancoMensalService = new BalancoMensalService(
                this.ordens, this.despesas, this.funcionarios, this.gerentes, this.balancos
        );
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
            System.out.println("6. Funcionarios");
            System.out.println("7. Gerente");
            System.out.println("8. Despesas");
            System.out.println("9. Elevadores");
            System.out.println("10. Ordem de Servico");
            System.out.println("11. Balanco Mensal");
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
                    balancoMensalService.exibirMenuRelatorios();
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
        List<Mecanico> mecanicos = funcionarios.stream()
                .filter(f -> f instanceof Mecanico)
                .map(f -> (Mecanico) f)
                .toList();

        List<Gerente> gerentes = funcionarios.stream()
                .filter(f -> f instanceof Gerente)
                .map(f -> (Gerente) f)
                .toList();


        PersistenciaUtil.salvarEmArquivo(mecanicos, "mecanicos.json");
        PersistenciaUtil.salvarEmArquivo(gerentes, "gerentes.json");
        PersistenciaUtil.salvarEmArquivo(produtos, "produtos.json");
        PersistenciaUtil.salvarEmArquivo(agendamentos, "agendamentos.json");
        PersistenciaUtil.salvarEmArquivo(despesas, "despesas.json");
        PersistenciaUtil.salvarEmArquivo(elevadores, "elevadores.json"); // Salvar a lista de elevadores
        PersistenciaUtil.salvarEmArquivo(ordens, "ordens.json");
        PersistenciaUtil.salvarEmArquivo(balancos, "balancoMensal.json");
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