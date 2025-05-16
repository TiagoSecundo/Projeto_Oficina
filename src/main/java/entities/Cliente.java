package entities;
//ainda preciso comentar
import java.util.ArrayList;

public class Cliente {

    private int idCliente;
    private String nome;
    private String telefone;
    private String endereco;
    private String cpf;
    private String email;
    private static ArrayList<Veiculo> veiculo;


    public Cliente(){
       veiculo = new ArrayList<>();
    }

    ;//Deixando dois construtores pensando na flexibilidade do código 
    
    public Cliente(int id, String nome, String telefone, String endereco, String cpf, String email) {
        this.idCliente = id;
        this.nome = nome;
        this.telefone = telefone;
        this.endereco = endereco;
        this.cpf = cpf;
        this.email = email;
        this.veiculo = new ArrayList<>();
    }
    /**
     * o ID do cliente
     * @return 
     */
    public int getIdCliente() {
        return idCliente;
    }
    /**
     * Define o ID do cliente
     * @param id 
     */
    public void setIdCliente(int id) {
        this.idCliente = id;
    }
    /**
     * Retorna o nome do cliente
     * @return 
     */
    public String getNome() {
        return nome;
    }
    /**
     * Define o nome do cliente
     * @param nome 
     */
    public void setNome(String nome) {
        this.nome = nome;
    }
    /**
     * Retorna o telefone do cliente
     * @return 
     */
    public String getTelefone() {
        return telefone;
    }
    /**
     * Define o telefone do cliente
     * @param telefone 
     */
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    /**
     * Retorna o CPF do cliente
     * @return 
     */
    public String getCpf() {
        return cpf;
    }
    /**
     * Define o CPF do cliente
     * @param cpf 
     */
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    /**
     * Retorna o e-mail do cliente
     * @return 
     */
    public String getEmail() {
        return email;
    }
    /**
     * Retorna a lista de veículos do cliente
     * @param email 
     */
    public void setEmail(String email) {
        this.email = email;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    
    
    /**
     * Retorna a lista de veículos do cliente
     * @return 
     */
    public ArrayList<Veiculo> getVeiculo(){
        return veiculo;
    }
    /**
     * Retorna uma representação em string dos dados do cliente
     * @return 
     */
    @Override //sobrescrevendo toString da classe object
    public String toString() {
        return String.format(
                "Cliente [ID: %d, Nome: %s, Telefone: %s, Endereco: %s, CPF: %s, Email: %s]",
                idCliente, nome, telefone, endereco, cpf, email
        );
    }
}
