package entities;

import java.util.ArrayList;
import java.util.List;

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

    /**
     *
     * @param cliente
     * @return
     */
    public boolean cadastrarCliente(Cliente cliente) {
        for (Cliente c : clientes) {
            if (c.getIdCliente() == cliente.getIdCliente()) {
                return false; // Já cadastrado
            }
        }
        clientes.add(cliente);
        return true;
    }

    /**
     *
     * @param veiculo
     * @param dono
     * @return
     */
    public boolean cadastrarVeiculo(Veiculo veiculo, Cliente dono) {
        // Verifica se o veículo já está cadastrado para o cliente (com base na placa, por exemplo)
        for (Veiculo v : dono.getVeiculo()) {
            if (v.getPlaca().equalsIgnoreCase(veiculo.getPlaca())) {
                return false; // Veículo já cadastrado
            }
        }
        // Adiciona o veículo à lista do cliente
        dono.getVeiculo().add(veiculo);
        return true;
    }

    public boolean cadastrarFuncionario(Funcionario funcionario) {
        // Verifica se já existe funcionário com mesmo ID
        for (Funcionario f : funcionarios) {
            if (f.getId() == funcionario.getId()) {
                System.out.println("Funcionário com ID " + funcionario.getId() + " já está cadastrado.");
                return false;
            }
        }
        funcionarios.add(funcionario);
        // Identifica e exibe o tipo do funcionário
        if (funcionario instanceof Gerente) {
            System.out.println("Gerente cadastrado: " + funcionario.getNome());
        } else if (funcionario instanceof Mecanico) {
            System.out.println("Mecanico cadastrado: " + funcionario.getNome());
        } else {
            System.out.println("Funcionário cadastrado: " + funcionario.getNome());
        }
        return true;
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
    /* Métodos de cadastro;;
    public boolean registrarServico(Servico servico) {}
    public boolean agendarServico(Agenda agendamento) {}

    // Métodos de listagem/consulta
    public List<Cliente> listarClientes() {}
    public List<Veiculo> listarVeiculosDoCliente(int clienteId) {}
    public List<Servico> listarServicosRealizados() {}
    public List<Agenda> consultarAgendaPorData(LocalDate data) {}
    public String gerarRelatorioMensal() {}

    // Métodos para gerenciar clientes
    public boolean editarCliente(int id, Cliente novosDados) {}
    public boolean removerCliente(int id) {}
    public Cliente buscarClientePorId(int id) {}

    // Métodos para gerenciar funcionários
    public boolean editarFuncionario(int id, Funcionario novosDados) {}
    public boolean removerFuncionario(int id) {}
    public Funcionario buscarFuncionarioPorId(int id) {}

    // Métodos para gerenciar veículos
    public boolean editarVeiculo(String placa, Veiculo novosDados) {}
    public boolean removerVeiculo(String placa) {}
    public Veiculo buscarVeiculoPorPlaca(String placa) {}
     */
}
