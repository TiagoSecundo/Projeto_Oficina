package entities;

import java.util.ArrayList;

public class Mecanico extends Funcionario {

    private String especialidade;

    public Mecanico() {
    }
/**
 * Construtor com parâmetros para inicializar todos os atributos do mecanico 
 * @param id
 * @param nome
 * @param cargo
 * @param email
 * @param salario
 * @param especialidade
 * @param relatorio 
 */
    public Mecanico(int id, String nome, String cargo, String email, float salario, String especialidade) {
        super(id, nome, cargo, email, salario);//usado para instanciar os atributos da classe pai na classe filha
        this.especialidade = especialidade;
        
    }
    /**
     * retorna a especialidade do mecanico 
     * @return 
     */
    public String getEspecialide() {
        return this.especialidade;
    }
    /**
     * Define a especialidade do mecanico 
     * @param especialidade 
     */
    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    /**
     * Retorna uma representação em string dos dados do Mecanico
     * @return 
     */
    @Override
    public String toString() {
        return super.toString() + String.format(", Especialidade: %s", especialidade +"\n");
    }
   
}
