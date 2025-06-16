package Chains.Agendamento;

import entities.Agenda;
import entities.Funcionario;
import entities.Mecanico;

import java.util.List;
/**
 * Garante que o funcionário atribuído realmente é um mecânico existente no sistema.
 * @author Tiago Secundo
 */
public class VerificaMecanicoHandler extends AgendamentoBaseHandler {

    private List<Funcionario> funcionarios;
    /**
     * Construtor do handler.
     * 
     * @param funcionarios Lista de funcionários cadastrados no sistema.
     */
    public VerificaMecanicoHandler(List<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
    }
    /**
     * Executa a validação do mecânico.
     * 
     * @param agenda Objeto de agendamento que será validado.
     * @return true se o mecânico for válido e passa para o próximo handler;
     *         false se o mecânico não for encontrado, encerrando a cadeia.
     */

    @Override
    public boolean handle(Agenda agenda) {
        System.out.println("[Handler Mecanico] Verificando mecanico...");

        for (Funcionario f : funcionarios) {
            if (f instanceof Mecanico && f.getId() == agenda.getMecanicoResponsavel().getId()) {
                System.out.println("[Handler Mecanico] Mecanico encontrado.");
                return super.handle(agenda);
            }
        }

        System.out.println("[Handler Mecanico] Mecanico nao encontrado! Cancelando agendamento.");
        return false;
    }
}