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
    /* Métodos de cadastro;;
    public boolean cadastrarVeiculo(Veiculo veiculo, Cliente dono) {}
    public boolean contratarFuncionario(Funcionario funcionario) {}
    public boolean registrarProduto(Produto produto) {}
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
