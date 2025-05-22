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

import services.ClienteService;
import services.ProdutoService;
import services.VeiculoService;
import services.AgendamentoService;
import services.EstoqueService;
import services.FuncionarioService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OficinaController {

    private List<Cliente> clientes;
    private List<Veiculo> veiculos;
    private List<Produto> produtos;

    private ClienteService clienteService;
    private VeiculoService veiculoService;
    private ProdutoService produtoService;
    private EstoqueService estoqueService;

    private List<Funcionario> funcionarios;
    private FuncionarioService funcionarioService;

    private List<Agenda> agendamentos;
    //private List<Funcionario> funcionarios;
    private AgendamentoService agendamentoService;

    public OficinaController() {
        this.clientes = new ArrayList<>();
        this.veiculos = new ArrayList<>();
        this.produtos = new ArrayList<>();

        this.clienteService = new ClienteService(clientes);
        this.veiculoService = new VeiculoService(veiculos, clientes);
        this.produtoService = new ProdutoService(produtos);
        this.estoqueService = new EstoqueService(produtos);

        this.funcionarios = new ArrayList<>();
        this.funcionarioService = new FuncionarioService(funcionarios);

        this.agendamentos = new ArrayList<>();
        //this.funcionarios = new ArrayList<>();
        this.agendamentoService = new AgendamentoService(agendamentos,
        clienteService, veiculoService, funcionarioService);
    }

    public void menuPrincipal() {
        Scanner sc = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1. Clientes");
            System.out.println("2. Veículos");
            System.out.println("3. Produtos");
            System.out.println("4. Agendamentos");
            System.out.println("5. Estoque");
            System.out.println("6. Funcionários");
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
                case 0 ->
                    System.out.println("Encerrando sistema...");
                default ->
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }
}
