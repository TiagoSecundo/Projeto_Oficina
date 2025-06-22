package services;

import entities.Funcionario;
import entities.Gerente;
import utils.PersistenciaUtil;

import java.util.List;
import java.util.Scanner;

public class CadastroGeralService {

    private FuncionarioService funcionarioService;
    private GerenteService gerenteService;
    private List<Funcionario> funcionarios;
    private List<Gerente> gerentes;

    public CadastroGeralService(FuncionarioService funcionarioService, GerenteService gerenteService,
                                List<Funcionario> funcionarios, List<Gerente> gerentes) {
        this.funcionarioService = funcionarioService;
        this.gerenteService = gerenteService;
        this.funcionarios = funcionarios;
        this.gerentes = gerentes;
    }

    public void menuCadastroFuncionarioOuGerente() {
        Scanner sc = new Scanner(System.in);

        // Se já houver gerente cadastrado, pedir senha
        if (gerenteService.existeGerente()) {
            System.out.print("Digite a senha do gerente para continuar: ");
            String senha = sc.nextLine();

            if (!gerenteService.validarSenha(senha)) {
                System.out.println("Senha incorreta. Acesso negado.");
                return;
            }
        }

        int opcao;
        do {
            System.out.println("\n--- Menu Cadastro de Funcionário ---");
            System.out.println("1. Cadastrar Mecânico");
            System.out.println("2. Cadastrar Gerente");
            System.out.println("0. Voltar");
            System.out.print("Escolha: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1 -> funcionarioService.cadastrarFuncionario();
                case 2 -> gerenteService.cadastrarGerente();
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida.");
            }

            // Salvar tudo após cada cadastro
            PersistenciaUtil.salvarEmArquivo(funcionarios, "funcionarios.json");
            PersistenciaUtil.salvarEmArquivo(gerentes, "gerentes.json");

        } while (opcao != 0);
    }
}
