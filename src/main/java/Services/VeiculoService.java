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
            System.out.println("1. Cadastrar veículo");
            System.out.println("2. Editar veículo");
            System.out.println("3. Remover veículo");
            System.out.println("4. Listar veículos por cliente");
            System.out.println("5. Atualizar status de veículo");
            System.out.println("6. Atualizar relatório do veículo");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
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
                case 0 ->
                    System.out.println("Voltando ao menu principal...");
                default ->
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    public void cadastrarVeiculo() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n--- Cadastro de Veículo ---");

        System.out.print("Placa: ");
        String placa = sc.nextLine();

        for (Veiculo v : veiculos) {
            if (v.getPlaca().equalsIgnoreCase(placa)) {
                System.out.println("Veículo com essa placa já está cadastrado.");
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
        System.out.print("Status do veículo: ");
        String status = sc.nextLine();
        System.out.print("Relatório preliminar: ");
        String relatorio = sc.nextLine();

        Veiculo novo = new Veiculo(placa, marca, modelo, ano, status, relatorio);
        veiculos.add(novo);

        System.out.print("Informe o ID do cliente dono do veículo: ");
        int idCliente = sc.nextInt();
        sc.nextLine();

        for (Cliente cliente : clientes) {
            if (cliente.getIdCliente() == idCliente) {
                cliente.getVeiculo().add(novo);
                novo.setProprietario(cliente);
                System.out.println("Veículo vinculado ao cliente " + cliente.getNome());
                return;
            }
        }

        System.out.println("Cliente não encontrado. Veículo foi cadastrado, mas sem dono.");
    }

    public void editarVeiculo() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n--- Editar Veículo ---");

        System.out.print("Digite a placa do veículo a editar: ");
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

                System.out.println("Veículo atualizado com sucesso!");
                return;
            }
        }

        System.out.println("Veículo com placa " + placa + " não encontrado.");
    }

    public void removerVeiculo() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n--- Remover Veículo ---");

        System.out.print("Digite a placa do veículo a remover: ");
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

                System.out.println("Veículo removido com sucesso!");
                return;
            }
        }

        System.out.println("Veículo com placa " + placa + " não encontrado.");
    }

    public void listarVeiculosDoCliente() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Digite o ID do cliente: ");
        int clienteId = sc.nextInt();
        sc.nextLine();

        for (Cliente cliente : clientes) {
            if (cliente.getIdCliente() == clienteId) {
                System.out.println("\n--- Veículos do cliente: " + cliente.getNome() + " ---");
                if (cliente.getVeiculo().isEmpty()) {
                    System.out.println("Nenhum veículo cadastrado.");
                } else {
                    for (Veiculo v : cliente.getVeiculo()) {
                        System.out.println(v);
                    }
                }
                return;
            }
        }
        System.out.println("Cliente com ID " + clienteId + " não encontrado.");
    }

    public void atualizarStatusVeiculo() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n--- Atualizar Status do Veículo ---");

        System.out.print("Digite a placa do veículo: ");
        String placa = sc.nextLine();

        for (Veiculo v : veiculos) {
            if (v.getPlaca().equalsIgnoreCase(placa)) {
                System.out.print("Novo status: ");
                String status = sc.nextLine();
                v.setStatus(status);
                System.out.println("Status do veículo atualizado com sucesso!");
                return;
            }
        }

        System.out.println("Veículo com placa " + placa + " não encontrado.");
    }

    public void atualizarRelatorioVeiculo() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n--- Atualizar Relatório do Veículo ---");

        System.out.print("Digite a placa do veículo: ");
        String placa = sc.nextLine();

        for (Veiculo v : veiculos) {
            if (v.getPlaca().equalsIgnoreCase(placa)) {
                System.out.print("Novo relatório: ");
                String relatorio = sc.nextLine();
                v.setRelatorio(relatorio);
                System.out.println("Relatório do veículo atualizado com sucesso!");
                return;
            }
        }

        System.out.println("Veículo com placa " + placa + " não encontrado.");
    }

    public Veiculo buscarVeiculoPorCliente(Cliente cliente) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Informe a placa do veículo: ");
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
