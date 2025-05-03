package entities;

import java.util.ArrayList;

public class Gerente extends Funcionario {

    private String senha;

    public Gerente() {
    }

    public Gerente(int id, String nome, String cargo, String email, float salario, String senha) {
        super(id, nome, cargo, email, salario);
        this.senha = senha;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
/**
 * Utilizando o tostring da classe pai Funcionario 
 * @return 
 */
@Override
    public String toString() {
        return super.toString();
    }

}
