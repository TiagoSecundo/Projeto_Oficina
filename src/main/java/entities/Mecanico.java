package entities;

import java.util.ArrayList;

public class Mecanico extends Funcionario {

    private String especialidade;

    public Mecanico() {
    }

    public Mecanico(String especialidade) {
        this.especialidade = especialidade;
    }

    public String getEspecialidde() {
        return this.especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(", Especialidade: %s", especialidade);
    }
}
