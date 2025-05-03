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

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int id) {
        this.idCliente = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public ArrayList<Veiculo> getVeiculo(){
        return veiculo;
    }

    @Override //sobrescrevendo toString da classe object
    public String toString() {
        return String.format(
                "Cliente [ID: %d, Nome: %s, Telefone: %s, Endereço: %s, CPF: %s, Email: %s]",
                idCliente, nome, telefone, endereco, cpf, email
        );
    }
}
