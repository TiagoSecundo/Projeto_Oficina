package services;

import entities.Funcionario;
import entities.Gerente;
import entities.Mecanico;
import entities.Ponto;
import java.time.LocalDateTime;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class FuncionarioService {

    private List<Funcionario> funcionarios;

    public FuncionarioService(List<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
    }

    public void menuFuncionario() {
        Scanner sc = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n--- Menu de Funcionários ---");
            System.out.println("1. Cadastrar funcionário");
            System.out.println("2. Editar funcionário");
            System.out.println("3. Remover funcionário");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1 ->
                    cadastrarFuncionario();
                case 2 ->
                    editarFuncionario();
                case 3 ->
                    removerFuncionario();
                case 0 ->
                    System.out.println("Voltando ao menu principal...");
                default ->
                    System.out.println("Opção inválida.");
            }

        } while (opcao != 0);
    }

    public void cadastrarFuncionario() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n--- Cadastro de Funcionário ---");

        System.out.print("ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        for (Funcionario f : funcionarios) {
            if (f.getId() == id) {
                System.out.println("Funcionário com esse ID já existe.");
                return;
            }
        }

        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("Cargo (Gerente/Mecanico): ");
        String cargo = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Salário: ");
        float salario = sc.nextFloat();
        sc.nextLine();

        Funcionario funcionario;

        if (cargo.equalsIgnoreCase("Gerente")) {
            System.out.print("Senha: ");
            String senha = sc.nextLine();
            funcionario = new Gerente(id, nome, cargo, email, salario, senha);
        } else if (cargo.equalsIgnoreCase("Mecanico")) {
            System.out.print("Especialidade: ");
            String especialidade = sc.nextLine();
            funcionario = new Mecanico(id, nome, cargo, email, salario, especialidade);
        } else {
            System.out.println("Cargo inválido.");
            return;
        }

        funcionarios.add(funcionario);
        System.out.println("Funcionário cadastrado com sucesso!");
    }

    public void editarFuncionario() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n--- Editar Funcionário ---");

        System.out.print("ID do funcionário: ");
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
                } else if (f instanceof Gerente) {
                    System.out.print("Nova senha: ");
                    ((Gerente) f).setSenha(sc.nextLine());
                }

                System.out.println("Funcionário editado com sucesso!");
                return;
            }
        }

        System.out.println("Funcionário com ID " + id + " não encontrado.");
    }

    public void removerFuncionario() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n--- Remover Funcionário ---");

        System.out.print("ID do funcionário: ");
        int id = sc.nextInt();
        sc.nextLine();

        Iterator<Funcionario> iterator = funcionarios.iterator();
        while (iterator.hasNext()) {
            Funcionario f = iterator.next();
            if (f.getId() == id) {
                if (f instanceof Gerente) {
                    System.out.print("Digite a senha do gerente: ");
                    String senha = sc.nextLine();
                    if (!((Gerente) f).getSenha().equals(senha)) {
                        System.out.println("Senha incorreta. Remoção cancelada.");
                        return;
                    }
                }

                iterator.remove();
                System.out.println("Funcionário removido com sucesso!");
                return;
            }
        }

        System.out.println("Funcionário com ID " + id + " não encontrado.");
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

        System.out.print("ID do funcionário: ");
        int id = sc.nextInt();
        sc.nextLine();

        for (Funcionario f : funcionarios) {
            if (f.getId() == id) {
                System.out.print("Bater ponto de (1) Entrada ou (2) Saída? ");
                int tipo = sc.nextInt();
                sc.nextLine();

                LocalDateTime agora = LocalDateTime.now();

                if (tipo == 1) {
                    f.setPontoEntrada(agora);
                    System.out.println("Ponto de entrada registrado para " + f.getNome() + " em: " + agora);
                } else if (tipo == 2) {
                    f.setPontoSaida(agora);
                    System.out.println("Ponto de saída registrado para " + f.getNome() + " em: " + agora);
                } else {
                    System.out.println("Opção inválida.");
                }
                return;
            }
        }

        System.out.println("Funcionário com ID " + id + " não encontrado.");
    }

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }
}
