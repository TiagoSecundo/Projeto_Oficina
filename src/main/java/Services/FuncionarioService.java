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
    private List<Mecanico> mecanicos;

    public FuncionarioService(List<Funcionario> funcionarios, List<Mecanico> mecanicos) {
        this.funcionarios = funcionarios;
        this.mecanicos = mecanicos;
    }

    public void menuFuncionario() {
        Scanner sc = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n--- Menu de mecanicos ---");
            System.out.println("1. Cadastrar mecanicos");
            System.out.println("2. Editar mecanicos");
            System.out.println("3. Remover mecanicos");
            System.out.println("4. Bater ponto");
            System.out.println("5. Listar mecanicos");
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

        // Verifica se o ID já existe em qualquer das listas
        boolean idExistente = funcionarios.stream().anyMatch(f -> f.getId() == id)
                || mecanicos.stream().anyMatch(m -> m.getId() == id);

        if (idExistente) {
            System.out.println("Ja existe um mecanico com esse ID. Escolha outro ID.");
            return;
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
        mecanicos.add(mecanico);

        System.out.println("Mecanico cadastrado com sucesso!");
    }

    public void editarFuncionario() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n--- Editar Mecanico ---");

        System.out.print("ID do Mecanico: ");
        int id = sc.nextInt();
        sc.nextLine();

        Funcionario encontrado = null;
        for (Funcionario f : funcionarios) {
            if (f.getId() == id && f instanceof Mecanico) {
                encontrado = f;
                break;
            }
        }

        if (encontrado == null) {
            System.out.println("Mecanico com ID " + id + " nao encontrado.");
            return;
        }

        System.out.print("Novo nome: ");
        String novoNome = sc.nextLine();
        System.out.print("Novo email: ");
        String novoEmail = sc.nextLine();
        System.out.print("Novo salario: ");
        float novoSalario = sc.nextFloat();
        sc.nextLine();

        Mecanico mecanico = (Mecanico) encontrado;
        System.out.print("Nova especialidade: ");
        String novaEspecialidade = sc.nextLine();

        // Atualiza os dados
        mecanico.setNome(novoNome);
        mecanico.setEmail(novoEmail);
        mecanico.setSalario(novoSalario);
        mecanico.setEspecialidade(novaEspecialidade);

        // Atualiza também a lista auxiliar, se necessário
        for (int i = 0; i < mecanicos.size(); i++) {
            if (mecanicos.get(i).getId() == id) {
                mecanicos.set(i, mecanico); // garante sincronia
                break;
            }
        }

        System.out.println("Mecanico editado com sucesso!");
    }

    public void removerFuncionario() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n--- Remover Mecanico ---");

        System.out.print("ID do Mecanico: ");
        int id = sc.nextInt();
        sc.nextLine();

        boolean removido = false;

        Iterator<Funcionario> iterator = funcionarios.iterator();
        while (iterator.hasNext()) {
            Funcionario f = iterator.next();
            if (f.getId() == id && f instanceof Mecanico) {
                iterator.remove();
                removido = true;
                break;
            }
        }

        if (removido) {
            mecanicos.removeIf(m -> m.getId() == id); // remove da lista de mecanicos também
            System.out.println("Mecanico removido com sucesso");
        } else {
            System.out.println("Mecanico com ID " + id + " nao encontrado.");
        }
    }

    public void listarFuncionarios() {
        if (mecanicos.isEmpty()) {
            System.out.println("Nenhum Mecanico cadastrado.");
            return;
        }

        System.out.println("\n--- Lista de Mecanicos ---");
        for (Mecanico m : mecanicos) {
            System.out.println("ID: " + m.getId());
            System.out.println("Nome: " + m.getNome());
            System.out.println("Email: " + m.getEmail());
            System.out.printf("Salario: R$ %.2f\n", m.getSalario());
            System.out.println("Especialidade: " + m.getEspecialidade());
            System.out.println("-------------------------------");
        }
    }

    public Mecanico buscarMecanicoPorId(int idMec) {
        for (Mecanico m : mecanicos) {
            if (m.getId() == idMec) {
                return m;
            }
        }
        return null;
    }

    public Mecanico getUltimoMecanicoCadastrado() {
        if (mecanicos.isEmpty()) {
            return null;
        }
        return mecanicos.get(mecanicos.size() - 1);
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
                    System.out.println("Ponto de saida registrado para " + f.getNome() + " em: " + agora);
                } else {
                    System.out.println("Opcao invalida.");
                }
                return;
            }
        }

        System.out.println("Mecanico com ID " + id + " nao encontrado.");
    }

    public List<Mecanico> getMecanicos() {
        return mecanicos;
    }

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }
}
