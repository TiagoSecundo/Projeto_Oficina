package entities;
// ainda preciso comentar

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Funcionario {

    private int id;
    private String nome;
    private String cargo;
    private String email;
    private double salario;
    private Ponto ponto = new Ponto();

    public Funcionario() {
    }

    /**
     * Construtor com parâmetros para inicializar todos os atributos dao
     * funcionario
     *
     * @param id
     * @param nome
     * @param cargo
     * @param email
     * @param salario
     */
    public Funcionario(int id, String nome, String cargo, String email, float salario) {
        this.id = id;
        this.nome = nome;
        this.cargo = cargo;
        this.email = email;
        this.salario = salario;

    }

    /**
     * retorna o id do funcionario
     *
     * @return
     */
    public int getId() {
        return this.id;
    }

    /**
     * Defina o ID da agenda
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retorna o cargo do funcionario
     *
     * @return
     */
    public String getCargo() {
        return cargo;
    }

    /**
     * Define o cargo do funcionario
     *
     * @param cargo
     */
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    /**
     * Retorna o nome do funcionario
     *
     * @return
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do funcionario
     *
     * @param nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna o email do funcionario
     *
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     * define o email do funcionario
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * retorna o salario do funcionario
     *
     * @return
     */
    public double getSalario() {
        return salario;
    }

    /**
     * Define salario do funcionario
     *
     * @param salario
     */
    public void setSalario(float salario) {
        this.salario = salario;
    }

    public Ponto getPonto() {
        return ponto;
    }

    public void setPonto(Ponto ponto) {
        this.ponto = ponto;
    }

    /**
     * RRetorna uma representação em string dos dados do funcionario
     *
     * @return
     */

    @Override
    public String toString() {
        return String.format(
                "Funcionario [Id: %d, Nome: %s, Cargo: %s, email: %s]",
                id, nome, cargo, email);
    }
}
