package services;

import entities.Produto;
import Utils.ProdutoPrecoComparatorUtil;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class ProdutoService {

    private List<Produto> produtos;
    private final double LUCRO = 0.3;

    public ProdutoService(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public void menuProdutos() {
        Scanner sc = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n--- Menu de Produtos ---");
            System.out.println("1. Cadastrar produto");
            System.out.println("2. Editar produto");
            System.out.println("3. Remover produto");
            System.out.println("4. Listar produtos");
            System.out.println("5. Listar produtos por preço final");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1 -> cadastrarProduto();
                case 2 -> editarProduto();
                case 3 -> removerProduto();
                case 4 -> listarProdutos();
                case 5 -> listarProdutosOrdenadosPorPreco();
                case 0 -> System.out.println("Voltando ao menu principal...");
                default -> System.out.println("Opcao inválida.");
            }

        } while (opcao != 0);
    }

    public void cadastrarProduto() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n--- Cadastro de Produto ---");

        System.out.print("ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        for (Produto p : produtos) {
            if (p.getId() == id) {
                System.out.println("Produto ja cadastrado com este ID.");
                return;
            }
        }

        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.print("Preco de Custo: ");
        double precoCusto = sc.nextDouble();
        sc.nextLine();

        System.out.print("Quantidade: ");
        int quantidade = sc.nextInt();
        sc.nextLine();

        System.out.print("Status: ");
        String status = sc.nextLine();

        double precoFinal = precoCusto * (1 + LUCRO);

        Produto novo = new Produto(id, nome, precoCusto, precoFinal, quantidade, status);
        produtos.add(novo);
        System.out.println("Produto cadastrado com sucesso!");
    }

    public void editarProduto() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n--- Editar Produto ---");

        System.out.print("Digite o ID do produto: ");
        int id = sc.nextInt();
        sc.nextLine();

        for (Produto produto : produtos) {
            if (produto.getId() == id) {
                System.out.print("Novo nome: ");
                produto.setNome(sc.nextLine());

                System.out.print("Novo preco de custo: ");
                double novoPrecoCusto = sc.nextDouble();
                sc.nextLine();
                produto.setPrecoCusto(novoPrecoCusto);
                produto.setPrecoFinal(novoPrecoCusto * (1 + LUCRO));

                System.out.print("Nova quantidade: ");
                produto.setQuantidade(sc.nextInt());
                sc.nextLine();

                System.out.print("Novo status: ");
                produto.setStatus(sc.nextLine());

                System.out.println("Produto editado com sucesso:\n" + produto);
                return;
            }
        }

        System.out.println("Produto com ID " + id + " nao encontrado.");
    }

    public void removerProduto() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n--- Remover Produto ---");

        System.out.print("Digite o ID do produto a remover: ");
        int id = sc.nextInt();
        sc.nextLine();

        Iterator<Produto> iterator = produtos.iterator();
        while (iterator.hasNext()) {
            Produto produto = iterator.next();
            if (produto.getId() == id) {
                iterator.remove();
                System.out.println("Produto removido com sucesso.");
                return;
            }
        }

        System.out.println("Produto com ID " + id + " nao encontrado.");
    }

    public void listarProdutos() {
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
            return;
        }

        System.out.println("\n--- Lista de Produtos ---");
        for (Produto produto : produtos) {
            System.out.println(produto);
        }
    }

    public Produto buscarProdutoPorId(int id) {
        for (Produto p : produtos) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }
    
    public void listarProdutosOrdenadosPorPreco() {
    if (produtos.isEmpty()) {
        System.out.println("Nenhum produto cadastrado.");
        return;
    }

    produtos.sort(new ProdutoPrecoComparatorUtil());

    System.out.println("\n--- Produtos ordenados por preco final ---");
    for (Produto p : produtos) {
        System.out.println(p.getNome() + " - R$ " + p.getPrecoFinal());
    }
}
}
