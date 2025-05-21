package entities;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.time.format.DateTimeFormatter;

public class Oficina {

    private List<Cliente> clientes;
    private List<Veiculo> veiculos;
    private List<Funcionario> funcionarios;
    private List<Servico> servicos;
    private List<Agenda> agendamentos;
    private List<Produto> produtos;

    public Oficina() {
        this.clientes = new ArrayList<>();
        this.veiculos = new ArrayList<>();
        this.funcionarios = new ArrayList<>();
        this.servicos = new ArrayList<>();
        this.agendamentos = new ArrayList<>();
        this.produtos = new ArrayList<>();

    }

// TODOS OS METODOS DE CLIENTE
    public void cadastrarCliente() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n--- Cadastro de Cliente ---");
        System.out.print("ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("Telefone: ");
        String telefone = sc.nextLine();
        System.out.print("Endereço: ");
        String endereco = sc.nextLine();
        System.out.print("CPF: ");
        String cpf = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();

        for (Cliente c : clientes) {
            if (c.getIdCliente() == id) {
                System.out.println("Cliente já está cadastrado.");
                return;
            }
        }

        Cliente novo = new Cliente(id, nome, telefone, endereco, cpf, email);
        clientes.add(novo);
        System.out.println("Cliente cadastrado com sucesso!");
    }

    public void editarCliente() {
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
                System.out.print("Novo endereço: ");
                cliente.setEndereco(sc.nextLine());
                System.out.print("Novo CPF: ");
                cliente.setCpf(sc.nextLine());
                System.out.print("Novo email: ");
                cliente.setEmail(sc.nextLine());

                System.out.println("Cliente editado com sucesso:\n" + cliente);
                return;
            }
        }

        System.out.println("Cliente com ID " + id + " não encontrado.");
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

        System.out.println("Cliente com ID " + id + " não encontrado.");
    }

    public Cliente buscarClientePorId(int id) {
        for (Cliente cliente : clientes) {
            if (cliente.getIdCliente() == id) {
                return cliente;
            }
        }
        System.out.println("Cliente com ID " + id + " não encontrado.");
        return null;
    }

// TODOS OS METODOS DE VEICULO
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
        System.out.print("Cor: ");
        String cor = sc.nextLine();

        Veiculo novo = new Veiculo(placa, marca, modelo, ano, cor);
        veiculos.add(novo);

        System.out.print("Informe o ID do cliente dono do veículo: ");
        int idCliente = sc.nextInt();
        sc.nextLine();

        Cliente cliente = buscarClientePorId(idCliente);
        if (cliente != null) {
            cliente.getVeiculo().add(novo);
            System.out.println("Veículo vinculado ao cliente " + cliente.getNome());
        } else {
            System.out.println("Cliente não encontrado. Veículo foi cadastrado, mas sem dono.");
        }
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

                // Remover também do cliente vinculado, se houver
                for (Cliente c : clientes) {
                    c.getVeiculo().removeIf(veiculo -> veiculo.getPlaca().equalsIgnoreCase(placa));
                }

                System.out.println("Veículo removido com sucesso!");
                return;
            }
        }

        System.out.println("Veículo com placa " + placa + " não encontrado.");
    }

    public void listarVeiculosDoCliente(int clienteId) {
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

    public void listarServicosRealizados() {
        if (servicos.isEmpty()) {
            System.out.println("Nenhum serviço realizado ainda.");
            return;
        }

        System.out.println("\n--- Serviços realizados ---");
        for (Servico s : servicos) {
            System.out.println(s);
        }
    }

    // TODOS OS METODOS DE FUNCIONARIO
    public void cadastrarFuncionario() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n--- Cadastro de Funcionário ---");

        System.out.print("ID: ");
        int id = sc.nextInt();
        sc.nextLine();
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
            System.out.print("Relatório: ");
            String relatorio = sc.nextLine();
            funcionario = new Mecanico(id, nome, cargo, email, salario, especialidade, relatorio);
        } else {
            System.out.println("Cargo inválido.");
            return;
        }

        for (Funcionario f : funcionarios) {
            if (f.getId() == id) {
                System.out.println("Funcionário já está cadastrado.");
                return;
            }
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

        System.out.print("Cargo (Gerente/Mecanico): ");
        String cargo = sc.nextLine();

        for (Funcionario f : funcionarios) {
            if (f.getId() == id && f.getCargo().equalsIgnoreCase(cargo)) {

                if (f instanceof Gerente) {
                    System.out.print("Digite a senha do gerente: ");
                    String senhaInformada = sc.nextLine();
                    Gerente gerente = (Gerente) f;
                    if (!gerente.getSenha().equals(senhaInformada)) {
                        System.out.println("Senha incorreta. Edição cancelada.");
                        return;
                    }
                }

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
                    System.out.print("Novo relatório: ");
                    m.setRelatorio(sc.nextLine());
                } else if (f instanceof Gerente) {
                    System.out.print("Nova senha: ");
                    ((Gerente) f).setSenha(sc.nextLine());
                }

                System.out.println("Funcionário atualizado com sucesso!");
                return;
            }
        }

        System.out.println("Funcionário não encontrado.");
    }

    public void removerFuncionario() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n--- Remover Funcionário ---");

        System.out.print("ID do funcionário: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Cargo (Gerente/Mecanico): ");
        String cargo = sc.nextLine();

        Iterator<Funcionario> iterator = funcionarios.iterator();
        while (iterator.hasNext()) {
            Funcionario f = iterator.next();
            if (f.getId() == id && f.getCargo().equalsIgnoreCase(cargo)) {

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

        System.out.println("Funcionário com ID " + id + " e cargo " + cargo + " não encontrado.");
    }

    public boolean registrarProduto(Produto produto) {
        // Verifica se o produto com o mesmo ID já foi cadastrado
        for (Produto p : produtos) {
            if (p.getId() == produto.getId()) {
                System.out.println("Produto com ID " + produto.getId() + " já está registrado.");
                return false;
            }
        }

        produtos.add(produto);
        System.out.println("Produto registrado com sucesso: " + produto);
        return true;
    }

    public void agendarServico() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n--- Agendar Serviço ---");

        System.out.print("ID do agendamento: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("ID do cliente: ");
        int idCliente = sc.nextInt();
        sc.nextLine();
        Cliente cliente = buscarClientePorId(idCliente);
        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }

        System.out.print("Placa do veículo: ");
        String placa = sc.nextLine();
        Veiculo veiculo = null;
        for (Veiculo v : cliente.getVeiculo()) {
            if (v.getPlaca().equalsIgnoreCase(placa)) {
                veiculo = v;
                break;
            }
        }
        if (veiculo == null) {
            System.out.println("Veículo não encontrado para este cliente.");
            return;
        }

        System.out.print("Descrição do problema: ");
        String problema = sc.nextLine();

        System.out.print("ID do mecânico: ");
        int idMec = sc.nextInt();
        sc.nextLine();
        Mecanico mecanico = null;
        for (Funcionario f : funcionarios) {
            if (f instanceof Mecanico && f.getId() == idMec) {
                mecanico = (Mecanico) f;
                break;
            }
        }
        if (mecanico == null) {
            System.out.println("Mecânico não encontrado.");
            return;
        }

        System.out.print("Data e hora do agendamento (ex: 20/05/2025 14:30): ");
        String dataHoraStr = sc.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime dataHora = LocalDateTime.parse(dataHoraStr, formatter);
        try {
            dataHora = LocalDateTime.parse(dataHoraStr, formatter);
        } catch (Exception e) {
            System.out.println("Formato de data e hora inválido.");
            return;
        }

        Agenda nova = new Agenda(id, cliente, veiculo, problema, mecanico, dataHora, "Agendado");
        agendamentos.add(nova);
        System.out.println("Agendamento realizado com sucesso!");
    }

    // Métodos de listagem/consulta
    public void consultarAgendaPorData(LocalDate data) {
        boolean encontrado = false;

        System.out.println("\n--- Agendamentos para " + data + " ---");
        for (Agenda a : agendamentos) {
            if (a.getDataAgendamento().toLocalDate().equals(data)) {
                System.out.println(a);
                encontrado = true;
            }
        }

        if (!encontrado) {
            System.out.println("Nenhum agendamento encontrado para essa data.");
        }
    }

    public void gerarRelatorioMensal() {
        double totalServicos = 0;
        int quantidadeServicos = 0;

        System.out.println("\n--- Relatório Mensal de Serviços ---");
        for (Servico s : servicos) {
            totalServicos += s.getValor();
            quantidadeServicos++;
            System.out.println(s);
        }

        System.out.println("\nTotal de serviços realizados: " + quantidadeServicos);
        System.out.printf("Receita total: R$ %.2f\n", totalServicos);
    }

}
