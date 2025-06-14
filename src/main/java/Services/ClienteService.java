package services;

import entities.Cliente;
import entities.Veiculo;

import Chains.VerificaClienteExistenteHandler;
import Chains.VerificaVeiculoDoClienteHandler;
import Chains.ClienteHandler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class ClienteService {

    private List<Cliente> clientes;

    public ClienteService(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public void menuCliente() {
        Scanner sc = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n--- Menu de Clientes ---");
            System.out.println("1. Cadastrar cliente");
            System.out.println("2. Editar cliente");
            System.out.println("3. Remover cliente");
            System.out.println("4. Listar clientes");
            System.out.println("5. Ver veiculos do cliente");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opcao: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1 ->
                    cadastrarCliente();
                case 2 ->
                    editarCliente();
                case 3 ->
                    removerCliente();
                case 4 ->
                    listarClientes();
                case 5 ->
                    listarVeiculosDoCliente();
                case 0 ->
                    System.out.println("Voltando ao menu principal...");
                default ->
                    System.out.println("Opcao invalida.");
            }
        } while (opcao != 0);
    }

    public void cadastrarCliente() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n--- Cadastro de Cliente ---");
        System.out.print("ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        for (Cliente c : clientes) {
            if (c.getIdCliente() == id) {
                System.out.println("Cliente ja está cadastrado.");
                return;
            }
        }

        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("Telefone: ");
        String telefone = sc.nextLine();
        System.out.print("Endereco: ");
        String endereco = sc.nextLine();
        System.out.print("CPF: ");
        String cpf = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();

        Cliente novo = new Cliente(id, nome, telefone, endereco, cpf, email);
        clientes.add(novo);
        System.out.println("Cliente cadastrado com sucesso!");
    }
    

    /**
     *
     */
    public void editarCliente() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n--- Editar Cliente ---");
        System.out.print("Digite o ID do cliente: ");
        int id = sc.nextInt();
        sc.nextLine();

        Cliente cliente = buscarClientePorId(id);

        // Aplica a cadeia de validações ANTES de permitir edição
        if (cliente == null || !validarClienteComChain(cliente)) {
            System.out.println(" Cliente nao passou nas validacoes. Edicao cancelada.");
            return;
        }

        System.out.println(" Cliente validado. Iniciando edicao...");

        System.out.print("Novo nome: ");
        cliente.setNome(sc.nextLine());
        System.out.print("Novo telefone: ");
        cliente.setTelefone(sc.nextLine());
        System.out.print("Novo endereco: ");
        cliente.setEndereco(sc.nextLine());
        System.out.print("Novo CPF: ");
        cliente.setCpf(sc.nextLine());
        System.out.print("Novo email: ");
        cliente.setEmail(sc.nextLine());

        System.out.println("Cliente editado com sucesso.");
    }
    
    
    
    

    /*public void editarCliente() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n--- Editar Cliente ---");
        System.out.print("Digite o ID do cliente: ");
        int id = sc.nextInt();
        sc.nextLine();

        for (Cliente cliente : clientes) {
            if (cliente.getIdCliente() == id) {
                System.out.print("Novo nome: ");
                cliente.setNome(sc.nextLine());
                System.out.print("Novo telefone: ");
                cliente.setTelefone(sc.nextLine());
                System.out.print("Novo endereco: ");
                cliente.setEndereco(sc.nextLine());
                System.out.print("Novo CPF: ");
                cliente.setCpf(sc.nextLine());
                System.out.print("Novo email: ");
                cliente.setEmail(sc.nextLine());

                System.out.println("Cliente editado com sucesso:\n" + cliente);
                return;
            }
        }

        System.out.println("Cliente com ID " + id + " nao encontrado.");
    }*/

    public void removerCliente() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n--- Remover Cliente ---");
        System.out.print("Digite o ID do cliente a remover: ");
        int id = sc.nextInt();
        sc.nextLine();

        Iterator<Cliente> iterator = clientes.iterator();
        while (iterator.hasNext()) {
            Cliente cliente = iterator.next();
            if (cliente.getIdCliente() == id) {
                iterator.remove();
                System.out.println("Cliente removido com sucesso.");
                return;
            }
        }

        System.out.println("Cliente com ID " + id + " nao encontrado.");
    }

    public void listarClientes() {
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }

        System.out.println("\n--- Lista de Clientes ---");
        for (Cliente cliente : clientes) {
            System.out.println(cliente);
        }
    }

    public void listarVeiculosDoCliente() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Digite o ID do cliente: ");
        int clienteId = sc.nextInt();
        sc.nextLine();

        for (Cliente cliente : clientes) {
            if (cliente.getIdCliente() == clienteId) {
                System.out.println("\n--- Veiculos do cliente: " + cliente.getNome() + " ---");
                if (cliente.getVeiculo().isEmpty()) {
                    System.out.println("Nenhum veiculo cadastrado.");
                } else {
                    for (Veiculo v : cliente.getVeiculo()) {
                        System.out.println(v);
                    }
                }
                return;
            }
        }
        System.out.println("Cliente com ID " + clienteId + " nao encontrado.");
    }

    public Cliente buscarClientePorId(int id) {
        for (Cliente cliente : clientes) {
            if (cliente.getIdCliente() == id) {
                return cliente;
            }
        }
        return null;
    }

    public Cliente buscarClientePorId() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Informe o ID do cliente: ");
        int id = sc.nextInt();
        sc.nextLine();
        for (Cliente c : clientes) {
            if (c.getIdCliente() == id) {
                return c;
            }
        }
        return null;
    }

    public Cliente getUltimoClienteCadastrado() {
        if (!clientes.isEmpty()) {
            return clientes.get(clientes.size() - 1);
        }
        return null;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }
    
    
    

    /**
     * Monta a cadeia de responsabilidade com dois handlers: 1. Verifica se o
     * cliente existe 2. Verifica se o cliente possui veículos
     *
     * @param cliente O cliente que será validado.
     * @return true se todas as etapas forem concluídas com sucesso.
     */
    public boolean validarClienteComChain(Cliente cliente) {
        System.out.println("=== Iniciando validacao de cliente com Chain of Responsibility ===");

        ClienteHandler handler1 = new VerificaClienteExistenteHandler(clientes);
        ClienteHandler handler2 = new VerificaVeiculoDoClienteHandler();

        handler1.setNext(handler2);

        boolean resultado = handler1.verificar(cliente);

        if (resultado) {
            System.out.println("---TODAS as validacoes da cadeia foram concluidas com sucesso!---");
        } else {
            System.out.println("XXX A validacao falhou em algum ponto da cadeia. XXX");
        }

        System.out.println("=== Fim da validacao ===");
        return resultado;
    }
}
