package entities;

import java.util.ArrayList;

public class Mecanico extends Funcionario {

    private String relatorio;
    private String especialidade;

    public Mecanico() {
    }

    public Mecanico(int id, String nome, String cargo, String email, float salario, String especialidade, String relatorio) {
        super(id, nome, cargo, email, salario);
        this.especialidade = especialidade;
        this.relatorio = relatorio;
    }

    public String getEspecialide() {
        return this.especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public String getRlatorio() {
        return this.relatorio;
    }

    public void setRelatorio(String relatorio) {
        this.especialidade = especialidade;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(", Especialidade: %s", especialidade);
    }
   
}
