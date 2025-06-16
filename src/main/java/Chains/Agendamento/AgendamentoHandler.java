/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Chains.Agendamento;

import entities.Agenda;

public interface AgendamentoHandler {
    /**
     * Interface base da cadeia. Todos os handlers devem implementá-la. Define o contrato: cada handler pode passar a requisição para o próximo.
     * @param next 
     */
    void setNext(AgendamentoHandler next);
    /**
     * 
     * @param agenda
     * @return 
     */
    boolean handle(Agenda agenda); // Cada handler recebe o agendamento e decide se continua
}
