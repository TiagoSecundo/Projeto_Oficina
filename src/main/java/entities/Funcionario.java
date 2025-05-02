package entities;

import java.util.ArrayList;

public class Funcionario {

    private int id;
    private String nome;
    private String cargo;
    private String email;
    private float salario;
    
    Funcionario (){}
    
    Funcionario (int id, String nome, String cargo, String email, float salario){
    this.id = id;
    this.nome = nome;
    this.cargo = cargo;
    this.email = email;
    this.salario = salario;
    }
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public float getSalario() {
        return salario;
    }

    public void Salario(float salario) {
        this.salario = salario;
    }

    @Override
    public String toString() {
        return String.format(
                "Funcionario [Id: %d, Nome: %s, Cargo: %s, email: %s]",
                id, nome, cargo, email);
    }
}
