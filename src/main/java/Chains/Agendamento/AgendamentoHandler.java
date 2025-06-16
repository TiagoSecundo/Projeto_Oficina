package Chains.Agendamento;

import entities.Agenda;

/**
 * Interface que define o contrato da cadeia de responsabilidade para agendamento.
 * Todos os handlers devem implementá-la.
 * 
 * Função principal:
 * - Definir quem é o próximo na cadeia (setNext).
 * - Processar a solicitação (handle).
 */

public interface AgendamentoHandler {
    /**
     * Define quem será o próximo handler na cadeia.
     * @param next Próximo handler.
     */
    void setNext(AgendamentoHandler next);
    /**
     * Método que executa a validação ou processamento.
     * Se a validação passar, deve repassar para o próximo da cadeia.
     * 
     * @param agenda Objeto que contém os dados do agendamento.
     * @return true se a validação for bem sucedida, false caso contrário.
     */
    boolean handle(Agenda agenda); // Cada handler recebe o agendamento e decide se continua
}
