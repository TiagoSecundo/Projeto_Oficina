package services;

import entities.Produto;

import java.util.List;
import java.util.Scanner;

public class EstoqueService {

    private List<Produto> produtos;

    public EstoqueService(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public void menuEstoque() {
        Scanner sc = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n--- Menu de Estoque ---");
            System.out.println("1. Adicionar ao estoque");
            System.out.println("2. Dar baixa no estoque");
            System.out.println("3. Consultar estoque atual");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opcao: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1 ->
                    adicionarEstoque();
                case 2 ->
                    darBaixaEstoque();
                case 3 ->
                    consultarEstoque();
                case 0 ->
                    System.out.println("Voltando ao menu principal...");
                default ->
                    System.out.println("Opcao invalida.");
            }

        } while (opcao != 0);
    }

    private void adicionarEstoque() {
        Scanner sc = new Scanner(System.in);
        System.out.print("ID do produto: ");
        int id = sc.nextInt();
        sc.nextLine();

        Produto produto = buscarProdutoPorId(id);
        if (produto == null) {
            System.out.println("Produto nao encontrado.");
            return;
        }

        System.out.print("Quantidade a adicionar: ");
        int quantidade = sc.nextInt();
        sc.nextLine();

        produto.setQuantidade(produto.getQuantidade() + quantidade);
        System.out.println("Estoque atualizado com sucesso!");
    }

    public void decrementarEstoque(Produto produto, int quantidade) {
        if (produto != null) {
            if (quantidade > 0 && produto.getQuantidade() >= quantidade) {
                produto.setQuantidade(produto.getQuantidade() - quantidade);
            } else if (quantidade <= 0) {
                System.out.println("Quantidade para decremento deve ser positiva.");
            } else {
                System.out.println("Estoque insuficiente para o produto: " + produto.getNome());
            }
        }
    }

    private void darBaixaEstoque() {
        Scanner sc = new Scanner(System.in);
        System.out.print("ID do produto: ");
        int id = sc.nextInt();
        sc.nextLine();

        Produto produto = buscarProdutoPorId(id);
        if (produto == null) {
            System.out.println("Produto nao encontrado.");
            return;
        }

        System.out.print("Quantidade a dar baixa: ");
        int quantidade = sc.nextInt();
        sc.nextLine();

        if (quantidade > produto.getQuantidade()) {
            System.out.println("Estoque insuficiente.");
            return;
        }

        produto.setQuantidade(produto.getQuantidade() - quantidade);
        System.out.println("Baixa realizada com sucesso!");
    }

    private void consultarEstoque() {
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
            return;
        }

        System.out.println("\n--- Estoque Atual ---");
        for (Produto p : produtos) {
            System.out.println("ID: " + p.getId() + " | Nome: " + p.getNome() + " | Quantidade: " + p.getQuantidade());
        }
    }

    private Produto buscarProdutoPorId(int id) {
        for (Produto p : produtos) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }
}
