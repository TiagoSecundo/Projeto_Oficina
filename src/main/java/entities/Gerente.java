package entities;

import java.util.ArrayList;

public class Gerente extends Funcionario {

    private String senha;

    public Gerente() {
    }

    /**
     * Construtor com parâmetros para inicializar todos os atributos do gerente
     *
     * @param id
     * @param nome
     * @param cargo
     * @param email
     * @param salario
     * @param senha
     */
    public Gerente(int id, String nome, String cargo, String email, float salario, String senha) {
        super(id, nome, cargo, email, salario); //usado para instanciar os atributos da classe pai na classe filha
        this.senha = senha;
    }

    /**
     * retorna a senha do gerente
     *
     * @return
     */
    public String getSenha() {
        return senha;
    }

    /**
     * Define a senha do Gerente
     *
     * @param senha
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     * Retorna uma representação em string dos dados do Gerente
     *
     * @return
     */
    @Override
    public String toString() {
        return super.toString() + " | Cargo: Gerente\n";
    }

}
