package entities;

import java.util.ArrayList;

public class Gerente extends Funcionario {

    private String senha;

    public Gerente() {
    }

    public Gerente(String senha) {
        this.senha = senha;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
