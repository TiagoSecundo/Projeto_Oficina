package controller;

import entities.Cliente;
import entities.Produto;
import entities.Veiculo;
import entities.Agenda;
import entities.Produto;
import entities.Funcionario;
import entities.Mecanico;
import entities.Gerente;
import entities.Servico;
import entities.Despesas;

import services.ClienteService;
import services.ProdutoService;
import services.VeiculoService;
import services.AgendamentoService;
import services.EstoqueService;
import services.FuncionarioService;
import services.DespesaService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.reflect.TypeToken;
import utils.PersistenciaUtil;
import java.lang.reflect.Type;

public class OficinaController {

    private List<Cliente> clientes;
    private List<Veiculo> veiculos;
    private List<Produto> produtos;
    private List<Despesas> despesas;
    private List<Funcionario> funcionarios;
    private List<Agenda> agendamentos;

    private ClienteService clienteService;
    private VeiculoService veiculoService;
    private ProdutoService produtoService;
    private EstoqueService estoqueService;
    private DespesaService despesaService;
    private FuncionarioService funcionarioService;
    private AgendamentoService agendamentoService;

    public OficinaController() {

        // ✅ Etapa 1 - Carregar os dados salvos em JSON
        this.clientes = carregar("clientes.json", new TypeToken<List<Cliente>>() {}.getType());
        this.veiculos = carregar("veiculos.json", new TypeToken<List<Veiculo>>() {}.getType());
        this.funcionarios = carregar("funcionarios.json", new TypeToken<List<Funcionario>>() {}.getType());
        this.produtos = carregar("produtos.json", new TypeToken<List<Produto>>() {}.getType());
        this.agendamentos = carregar("agendamentos.json", new TypeToken<List<Agenda>>() {}.getType());
        this.despesas = carregar("despesas.json", new TypeToken<List<Despesas>>() {}.getType());

        // ✅ Etapa 2 - Inicializar os serviços com as listas carregadas
        this.clienteService = new ClienteService(clientes);
        this.veiculoService = new VeiculoService(veiculos, clientes);
        this.produtoService = new ProdutoService(produtos);
        this.estoqueService = new EstoqueService(produtos);
        this.funcionarioService = new FuncionarioService(funcionarios);
        this.despesaService = new DespesaService(despesas);
        this.agendamentoService = new AgendamentoService(agendamentos, clienteService, veiculoService, funcionarioService);
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
            System.out.println("7. Despesas");
            System.out.println("0. Sair");
            System.out.print("Escolha: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1 -> clienteService.menuCliente();
                case 2 -> veiculoService.menuVeiculo();
                case 3 -> produtoService.menuProdutos();
                case 4 -> agendamentoService.menuAgendamentos();
                case 5 -> estoqueService.menuEstoque();
                case 6 -> funcionarioService.menuFuncionario();
                case 7 -> despesaService.menuDespesas();
                case 0 -> {
                    salvarTudo();
                    System.out.println("Encerrando sistema e salvando dados...");
                }
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    public void salvarTudo() {
        PersistenciaUtil.salvarEmArquivo(clientes, "clientes.json");
        PersistenciaUtil.salvarEmArquivo(veiculos, "veiculos.json");
        PersistenciaUtil.salvarEmArquivo(funcionarios, "funcionarios.json");
        PersistenciaUtil.salvarEmArquivo(produtos, "produtos.json");
        PersistenciaUtil.salvarEmArquivo(agendamentos, "agendamentos.json");
        PersistenciaUtil.salvarEmArquivo(despesas, "despesas.json");
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
