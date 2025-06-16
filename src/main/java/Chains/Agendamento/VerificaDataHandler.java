/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Chains.Agendamento;

import entities.Agenda;

import java.time.LocalDateTime;
/**
 * Apenas garante que a data/hora do agendamento ainda não passou.
 * @author Tiago Secundo
 */
public class VerificaDataHandler extends AgendamentoBaseHandler {

    @Override
    public boolean handle(Agenda agenda) {
        System.out.println("[Handler Data] Verificando se a data do agendamento e futuro...");

        if (agenda.getDataAgendamento().isAfter(LocalDateTime.now())) {
            System.out.println("[Handler Data] Data valida.");
            return super.handle(agenda);
        }

        System.out.println("[Handler Data] Data invalida. Deve ser futura!");
        return false;
    }
}