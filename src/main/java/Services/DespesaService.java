package services;

import entities.Despesas;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class DespesaService {

    private List<Despesas> despesas;

    public DespesaService(List<Despesas> despesas) {
        this.despesas = despesas;
    }

    public void menuDespesas() {
        Scanner sc = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n--- Menu de Despesas ---");
            System.out.println("1. Cadastrar despesa");
            System.out.println("2. Listar despesas");
            System.out.println("3. Remover despesa");
            System.out.println("4. Total de despesas");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1 -> cadastrarDespesa();
                case 2 -> listarDespesas();
                case 3 -> removerDespesa();
                case 4 -> totalDespesas();
                case 0 -> System.out.println("Voltando ao menu principal...");
                default -> System.out.println("Opcao invalida.");
            }
        } while (opcao != 0);
    }

    public void cadastrarDespesa() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n--- Cadastro de Despesa ---");

        System.out.print("ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Descricao: ");
        String descricao = sc.nextLine();

        System.out.print("Valor: ");
        double valor = sc.nextDouble();
        sc.nextLine();

        System.out.print("Data (dd/MM/yyyy): ");
        String dataStr = sc.nextLine();
        LocalDate data = LocalDate.parse(dataStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        Despesas nova = new Despesas(id, descricao, valor, data);
        despesas.add(nova);
        System.out.println("Despesa cadastrada com sucesso!");
    }

    public void listarDespesas() {
        if (despesas.isEmpty()) {
            System.out.println("Nenhuma despesa cadastrada.");
            return;
        }

        System.out.println("\n--- Lista de Despesas ---");
        for (Despesas d : despesas) {
            System.out.println(d);
        }
    }

    public void removerDespesa() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n--- Remover Despesa ---");

        System.out.print("Digite o ID da despesa a remover: ");
        int id = sc.nextInt();
        sc.nextLine();

        Iterator<Despesas> iterator = despesas.iterator();
        while (iterator.hasNext()) {
            Despesas d = iterator.next();
            if (d.getId() == id) {
                iterator.remove();
                System.out.println("Despesa removida com sucesso.");
                return;
            }
        }

        System.out.println("Despesa com ID " + id + " nao encontrada.");
    }

    public void totalDespesas() {
        double total = 0;
        for (Despesas d : despesas) {
            total += d.getValor();
        }
        System.out.printf("Total de despesas: R$ %.2f\n", total);
    }
}
