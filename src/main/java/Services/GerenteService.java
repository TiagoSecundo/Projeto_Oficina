package services;

import entities.Funcionario;
import entities.Gerente;

import java.util.List;
import java.util.Scanner;

public class GerenteService {

    private List<Gerente> gerentes;

    public GerenteService(List<Gerente> gerentes) {
        this.gerentes = gerentes;
    }

    public void cadastrarGerente() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n--- Cadastro de Gerente ---");

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
        String senha = sc.nextLine();

        Gerente gerente = new Gerente(id, nome, "Gerente", email, salario, senha);
        gerentes.add(gerente);

        System.out.println("Gerente cadastrado com sucesso!");
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
