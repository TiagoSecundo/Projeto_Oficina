package services;

import entities.Funcionario;
import entities.Gerente;

import java.util.List;
import java.util.Scanner;
import java.util.Iterator;


public class GerenteService {

    private List<Gerente> gerentes;

    public GerenteService(List<Gerente> gerentes) {
        this.gerentes = gerentes;
    }

    public void menuGerente() {
        Scanner sc = new Scanner(System.in);

        // Proteção por senha se já houver gerente
        if (existeGerente()) {
            System.out.print("Digite a senha de um gerente para continuar: ");
            String senha = sc.nextLine();
            if (!validarSenha(senha)) {
                System.out.println("Senha incorreta. Acesso negado.");
                return;
            }
        }

        int opcao;
        do {
            System.out.println("\n--- Menu de Gerente ---");
            System.out.println("1. Cadastrar gerente");
            System.out.println("2. Editar gerente");
            System.out.println("3. Remover gerente");
            System.out.println("0. Voltar");
            System.out.print("Escolha: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1 ->
                    cadastrarGerente();
                case 2 ->
                    editarGerente();
                case 3 ->
                    removerGerente();
                case 0 ->
                    System.out.println("Voltando...");
                default ->
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    public void cadastrarGerente() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n--- Cadastro de Gerente ---");

        if (existeGerente()) {
            System.out.print("Digite a senha de um gerente para autorizar o cadastro: ");
            String senha = sc.nextLine();
            if (!validarSenha(senha)) {
                System.out.println("Senha incorreta. Cadastro cancelado.");
                return;
            }
        }

        System.out.print("ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        for (Gerente g : gerentes) {
            if (g.getId() == id) {
                System.out.println("Gerente com esse ID já existe.");
                return;
            }
        }

        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Salário: ");
        float salario = sc.nextFloat();
        sc.nextLine();
        System.out.print("Senha: ");
        String senhaNova = sc.nextLine();

        Gerente gerente = new Gerente(id, nome, "Gerente", email, salario, senhaNova);
        gerentes.add(gerente);

        System.out.println("Gerente cadastrado com sucesso!");
    }

    public void editarGerente() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n--- Editar Gerente ---");

        System.out.print("Digite a senha para continuar: ");
        String senha = sc.nextLine();
        if (!validarSenha(senha)) {
            System.out.println("Senha incorreta.");
            return;
        }

        System.out.print("ID do gerente: ");
        int id = sc.nextInt();
        sc.nextLine();

        for (Gerente g : gerentes) {
            if (g.getId() == id) {
                System.out.print("Novo nome: ");
                g.setNome(sc.nextLine());
                System.out.print("Novo email: ");
                g.setEmail(sc.nextLine());
                System.out.print("Novo salário: ");
                g.setSalario(sc.nextFloat());
                sc.nextLine();
                System.out.print("Nova senha: ");
                g.setSenha(sc.nextLine());

                System.out.println("Gerente editado com sucesso!");
                return;
            }
        }

        System.out.println("Gerente com ID " + id + " não encontrado.");
    }

    public void removerGerente() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n--- Remover Gerente ---");

        System.out.print("Digite a senha de um gerente: ");
        String senha = sc.nextLine();
        if (!validarSenha(senha)) {
            System.out.println("Senha incorreta.");
            return;
        }

        System.out.print("ID do gerente a remover: ");
        int id = sc.nextInt();
        sc.nextLine();

        Iterator<Gerente> iterator = gerentes.iterator();
        while (iterator.hasNext()) {
            Gerente g = iterator.next();
            if (g.getId() == id) {
                iterator.remove();
                System.out.println("Gerente removido com sucesso.");
                return;
            }
        }

        System.out.println("Gerente com ID " + id + " não encontrado.");
    }

    public boolean validarSenha(String senha) {
        for (Gerente g : gerentes) {
            if (g.getSenha().equals(senha)) {
                return true;
            }
        }
        return false;
    }

    public boolean existeGerente() {
        return !gerentes.isEmpty();
    }

    public List<Gerente> getGerentes() {
        return gerentes;
    }
}
