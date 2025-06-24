package services;

import entities.Gerente;
import utils.PersistenciaUtil;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class GerenteService {

    private List<Gerente> gerentes;

    public GerenteService(List<Gerente> gerentes) {
        this.gerentes = gerentes;
    }

    // ✅ Menu principal
    public void menuGerente() {
        Scanner sc = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n=== Menu de Gerente ===");
            System.out.println("1. Cadastrar Gerente");
            System.out.println("2. Editar Gerente");
            System.out.println("3. Remover Gerente");
            System.out.println("4. Listar Gerentes");
            System.out.println("0. Voltar");
            System.out.print("Escolha: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1 -> cadastrarGerente();
                case 2 -> editarGerente();
                case 3 -> removerGerente();
                case 4 -> listarGerentes();
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida.");
            }

            PersistenciaUtil.salvarEmArquivo(gerentes, "gerentes.json");

        } while (opcao != 0);
    }

    // ✅ Cadastrar gerente (só pede senha se já houver gerente cadastrado)
    public void cadastrarGerente() {
        Scanner sc = new Scanner(System.in);

        if (!gerentes.isEmpty()) {
            if (!autenticar()) return;
        }

        System.out.println("\n--- Cadastro de Gerente ---");
        System.out.print("ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        if (buscarPorId(id) != null) {
            System.out.println("Gerente com esse ID já existe.");
            return;
        }

        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Salário: ");
        float salario = sc.nextFloat();
        sc.nextLine();
        System.out.print("Senha: ");
        String senha = sc.nextLine();

        Gerente gerente = new Gerente(id, nome, "Gerente", email, salario, senha);
        gerentes.add(gerente);

        System.out.println("Gerente cadastrado com sucesso!");
    }

    // ✅ Editar gerente
    public void editarGerente() {
        if (gerentes.isEmpty()) {
            System.out.println("Nenhum gerente cadastrado.");
            return;
        }

        if (!autenticar()) return;

        Scanner sc = new Scanner(System.in);
        System.out.print("ID do gerente para editar: ");
        int id = sc.nextInt();
        sc.nextLine();

        Gerente gerente = buscarPorId(id);
        if (gerente == null) {
            System.out.println("Gerente com ID " + id + " não encontrado.");
            return;
        }

        System.out.print("Novo nome: ");
        gerente.setNome(sc.nextLine());
        System.out.print("Novo email: ");
        gerente.setEmail(sc.nextLine());
        System.out.print("Novo salário: ");
        gerente.setSalario(sc.nextFloat());
        sc.nextLine();
        System.out.print("Nova senha: ");
        gerente.setSenha(sc.nextLine());

        System.out.println("Gerente editado com sucesso!");
    }

    // ✅ Remover gerente
    public void removerGerente() {
        if (gerentes.isEmpty()) {
            System.out.println("Nenhum gerente cadastrado.");
            return;
        }

        if (!autenticar()) return;

        Scanner sc = new Scanner(System.in);
        System.out.print("ID do gerente para remover: ");
        int id = sc.nextInt();
        sc.nextLine();

        Iterator<Gerente> iterator = gerentes.iterator();
        while (iterator.hasNext()) {
            Gerente g = iterator.next();
            if (g.getId() == id) {
                iterator.remove();
                System.out.println("Gerente removido com sucesso!");
                return;
            }
        }

        System.out.println("Gerente com ID " + id + " não encontrado.");
    }

    // ✅ Listar
    public void listarGerentes() {
        if (gerentes.isEmpty()) {
            System.out.println("Nenhum gerente cadastrado.");
            return;
        }

        System.out.println("\n--- Lista de Gerentes ---");
        for (Gerente g : gerentes) {
            System.out.println(g);
        }
    }

    // 🔐 ✅ Autenticação geral
    private boolean autenticar() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Digite a senha do gerente para continuar: ");
        String senha = sc.nextLine();

        if (!validarSenha(senha)) {
            System.out.println("Senha incorreta. Acesso negado.");
            return false;
        }
        return true;
    }

    // ✅ Verificar se a senha está correta para qualquer gerente
    public boolean validarSenha(String senha) {
        for (Gerente g : gerentes) {
            if (g.getSenha().equals(senha)) {
                return true;
            }
        }
        return false;
    }

    // ✅ Buscar gerente por ID
    private Gerente buscarPorId(int id) {
        for (Gerente g : gerentes) {
            if (g.getId() == id) {
                return g;
            }
        }
        return null;
    }

    // ✅ Verificar se existe pelo menos um gerente
    public boolean existeGerente() {
        return !gerentes.isEmpty();
    }

    public List<Gerente> getGerentes() {
        return gerentes;
    }
}
