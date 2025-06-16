/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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

    public VerificaMecanicoHandler(List<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
    }

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