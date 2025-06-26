package services;

import entities.Funcionario;
import entities.Mecanico;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class FuncionarioService {

    private List<Funcionario> funcionarios;

    public FuncionarioService(List<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
    }

    public void menuFuncionario() {
        Scanner sc = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n--- Menu de Mecanicos ---");
            System.out.println("1. Cadastrar Mecanico");
            System.out.println("2. Editar Mecanico");
            System.out.println("3. Remover Mecanico");
            System.out.println("4. Bater ponto");
            System.out.println("5. Listar Mecanico");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opcao: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1 ->
                    cadastrarFuncionario();
                case 2 ->
                    editarFuncionario();
                case 3 ->
                    removerFuncionario();
                case 4 ->
                    baterPonto();
                case 5 ->
                    listarFuncionarios();
                case 0 ->
                    System.out.println("Voltando...");
                default ->
                    System.out.println("Opcao invalida.");
            }

        } while (opcao != 0);
    }

    public void cadastrarFuncionario() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n--- Cadastro de Mecanico ---");

        System.out.print("ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        for (Funcionario f : funcionarios) {
            if (f.getId() == id) {
                System.out.println("Mecanico com esse ID ja existe.");
                return;
            }
        }

        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Salario: ");
        float salario = sc.nextFloat();
        sc.nextLine();
        System.out.print("Especialidade: ");
        String especialidade = sc.nextLine();

        Mecanico mecanico = new Mecanico(id, nome, "Mecanico", email, salario, especialidade);
        funcionarios.add(mecanico);
        System.out.println("Mecanico cadastrado com sucesso!");
    }

    public void editarFuncionario() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n--- Editar Mecanico ---");

        System.out.print("ID do Mecanico: ");
        int id = sc.nextInt();
        sc.nextLine();

        for (Funcionario f : funcionarios) {
            if (f.getId() == id) {
                System.out.print("Novo nome: ");
                f.setNome(sc.nextLine());
                System.out.print("Novo email: ");
                f.setEmail(sc.nextLine());
                System.out.print("Novo salário: ");
                f.setSalario(sc.nextFloat());
                sc.nextLine();

                if (f instanceof Mecanico) {
                    Mecanico m = (Mecanico) f;
                    System.out.print("Nova especialidade: ");
                    m.setEspecialidade(sc.nextLine());
                }

                System.out.println("Mecanico editado com sucesso!");
                return;
            }
        }

        System.out.println("Mecanico com ID " + id + " nao encontrado.");
    }

    public void removerFuncionario() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n--- Remover Mecanico ---");

        System.out.print("ID do Mecanico: ");
        int id = sc.nextInt();
        sc.nextLine();

        Iterator<Funcionario> iterator = funcionarios.iterator();
        while (iterator.hasNext()) {
            Funcionario f = iterator.next();
            if (f.getId() == id) {
                iterator.remove();
                System.out.println("Mecanico removido com sucesso");
                return;
            }
        }

        System.out.println("Mecanico com ID " + id + " nao encontrado.");
    }

    public void listarFuncionarios() {
        if (funcionarios.isEmpty()) {
            System.out.println("Nenhum Mecanico cadastrado.");
            return;
        }

        System.out.println("\n--- Lista de Mecanico ---");
        for (Funcionario f : funcionarios) {
            System.out.println(f);
        }
    }

    public Mecanico buscarMecanicoPorId(int idMec) {
        for (Funcionario f : funcionarios) {
            if (f instanceof Mecanico && f.getId() == idMec) {
                return (Mecanico) f;
            }
        }
        return null;
    }

    public Mecanico getUltimoMecanicoCadastrado() {
        for (int i = funcionarios.size() - 1; i >= 0; i--) {
            Funcionario f = funcionarios.get(i);
            if (f instanceof Mecanico) {
                return (Mecanico) f;
            }
        }
        return null;
    }

    public void baterPonto() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n--- Bater Ponto ---");

        System.out.print("ID do Mecanico: ");
        int id = sc.nextInt();
        sc.nextLine();

        for (Funcionario f : funcionarios) {
            if (f.getId() == id) {
                System.out.print("Bater ponto de (1) Entrada ou (2) Saida? ");
                int tipo = sc.nextInt();
                sc.nextLine();

                LocalDateTime agora = LocalDateTime.now();

                if (tipo == 1) {
                    f.getPonto().setEntrada(agora);
                    System.out.println("Ponto de entrada registrado para " + f.getNome() + " em: " + agora);
                } else if (tipo == 2) {
                    f.getPonto().setSaida(agora);
                    System.out.println("Ponto de saída registrado para " + f.getNome() + " em: " + agora);
                } else {
                    System.out.println("Opcao invalida.");
                }
                return;
            }
        }

        System.out.println("Mecanico com ID " + id + " nao encontrado.");
    }

    public List<Mecanico> listarMecanicos() {
        List<Mecanico> mecanicos = new ArrayList<>();
        for (Funcionario f : funcionarios) {
            if (f instanceof Mecanico) {
                mecanicos.add((Mecanico) f);
            }
        }
        return mecanicos;
    }

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }
}
