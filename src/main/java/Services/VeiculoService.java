package services;

import entities.Cliente;
import entities.Veiculo;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class VeiculoService {

    private List<Veiculo> veiculos;
    private List<Cliente> clientes;

    public VeiculoService(List<Veiculo> veiculos, List<Cliente> clientes) {
        this.veiculos = veiculos;
        this.clientes = clientes;
    }

    public void menuVeiculo() {
        Scanner sc = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n--- Menu de Veículos ---");
            System.out.println("1. Cadastrar veiculo");
            System.out.println("2. Editar veiculo");
            System.out.println("3. Remover veiculo");
            System.out.println("4. Listar veículos por cliente");
            System.out.println("5. Atualizar status de veiculo");
            System.out.println("6. Atualizar relatório do veículo");
            System.out.println("7. Listar todos veiculos");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opcao: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1 ->
                    cadastrarVeiculo();
                case 2 ->
                    editarVeiculo();
                case 3 ->
                    removerVeiculo();
                case 4 ->
                    listarVeiculosDoCliente();
                case 5 ->
                    atualizarStatusVeiculo();
                case 6 ->
                    atualizarRelatorioVeiculo();
                case 7 ->
                    listarTodosVeiculos();
                case 0 ->
                    System.out.println("Voltando ao menu principal...");
                default ->
                    System.out.println("Opcao invalida.");
            }
        } while (opcao != 0);
    }

    public void cadastrarVeiculo() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n--- Cadastro de Veiculo ---");

        System.out.print("Placa: ");
        String placa = sc.nextLine();

        for (Veiculo v : veiculos) {
            if (v.getPlaca().equalsIgnoreCase(placa)) {
                System.out.println("Veiculo com essa placa já está cadastrado.");
                return;
            }
        }

        System.out.print("Marca: ");
        String marca = sc.nextLine();
        System.out.print("Modelo: ");
        String modelo = sc.nextLine();
        System.out.print("Ano: ");
        int ano = sc.nextInt();
        sc.nextLine();
        System.out.print("Status do veiculo: ");
        String status = sc.nextLine();
        System.out.print("Relatorio preliminar: ");
        String relatorio = sc.nextLine();

        Veiculo novo = new Veiculo(placa, marca, modelo, ano, status, relatorio);
        veiculos.add(novo);

        System.out.print("Informe o ID do cliente dono do veiculo: ");
        int idCliente = sc.nextInt();
        sc.nextLine();

        for (Cliente cliente : clientes) {
            if (cliente.getIdCliente() == idCliente) {
                cliente.getVeiculo().add(novo);
                novo.setProprietario(cliente);
                System.out.println("Veiculo vinculado ao cliente " + cliente.getNome());
                return;
            }
        }

        System.out.println("Cliente nao encontrado. Veículo foi cadastrado, mas sem dono.");
        System.out.println("Total de veículos cadastrados: " + Veiculo.getTotalVeiculos());
    }

    public void editarVeiculo() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n--- Editar Veículo ---");

        System.out.print("Digite a placa do veiculo a editar: ");
        String placa = sc.nextLine();

        for (Veiculo v : veiculos) {
            if (v.getPlaca().equalsIgnoreCase(placa)) {
                System.out.print("Nova marca: ");
                v.setMarca(sc.nextLine());
                System.out.print("Novo modelo: ");
                v.setModelo(sc.nextLine());
                System.out.print("Novo ano: ");
                v.setAno(sc.nextInt());
                sc.nextLine();
                System.out.print("Nova cor: ");
                v.setStatus(sc.nextLine());

                System.out.println("Veiculo atualizado com sucesso!");
                return;
            }
        }

        System.out.println("Veiculo com placa " + placa + " nao encontrado.");
    }

    public void listarTodosVeiculos() {
        System.out.println("\n--- Lista de TODOS os veículos cadastrados ---");

        if (veiculos.isEmpty()) {
            System.out.println("Nenhum veículo foi encontrado (nem do JSON, nem local).");
            return;
        }

        int contador = 1;
        for (Veiculo v : veiculos) {
            System.out.println("[" + contador + "] " + v);
            contador++;
        }
    }

    public void removerVeiculo() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n--- Remover Veiculo ---");

        System.out.print("Digite a placa do veiculo a remover: ");
        String placa = sc.nextLine();

        Iterator<Veiculo> iterator = veiculos.iterator();
        while (iterator.hasNext()) {
            Veiculo v = iterator.next();
            if (v.getPlaca().equalsIgnoreCase(placa)) {
                iterator.remove();

                // Remove do cliente também
                for (Cliente c : clientes) {
                    c.getVeiculo().removeIf(veiculo -> veiculo.getPlaca().equalsIgnoreCase(placa));
                }

                System.out.println("Veiculo removido com sucesso!");
                return;
            }
        }

        System.out.println("Veiculo com placa " + placa + " nao encontrado.");
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

    public void atualizarStatusVeiculo() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n--- Atualizar Status do Veículo ---");

        System.out.print("Digite a placa do veiculo: ");
        String placa = sc.nextLine();

        for (Veiculo v : veiculos) {
            if (v.getPlaca().equalsIgnoreCase(placa)) {
                System.out.print("Novo status: ");
                String status = sc.nextLine();
                v.setStatus(status);
                System.out.println("Status do veiculo atualizado com sucesso!");
                return;
            }
        }

        System.out.println("Veiculo com placa " + placa + " nao encontrado.");
    }

    public void atualizarRelatorioVeiculo() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n--- Atualizar Relatorio do Veiculo ---");

        System.out.print("Digite a placa do veiculo: ");
        String placa = sc.nextLine();

        for (Veiculo v : veiculos) {
            if (v.getPlaca().equalsIgnoreCase(placa)) {
                System.out.print("Novo relatorio: ");
                String relatorio = sc.nextLine();
                v.setRelatorio(relatorio);
                System.out.println("Relatorio do veículo atualizado com sucesso!");
                return;
            }
        }

        System.out.println("Veiculo com placa " + placa + " não encontrado.");
    }

    public Veiculo buscarVeiculoPorCliente(Cliente cliente) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Informe a placa do veiculo: ");
        String placa = sc.nextLine();

        return cliente.getVeiculo().stream()
                .filter(v -> v.getPlaca().equalsIgnoreCase(placa))
                .findFirst().orElse(null);
    }

    public void cadastrarVeiculoParaCliente(Cliente cliente) {
        cadastrarVeiculo(); // já adiciona ao cliente dentro do método
    }

    public Veiculo getUltimoVeiculoCadastrado() {
        if (!veiculos.isEmpty()) {
            return veiculos.get(veiculos.size() - 1);
        }
        return null;
    }

    public List<Veiculo> getVeiculos() {
        return veiculos;
    }
}
