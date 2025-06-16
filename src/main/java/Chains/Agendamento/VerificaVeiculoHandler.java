/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Chains.Agendamento;

import entities.Agenda;
import entities.Cliente;
import entities.Veiculo;

import java.util.List;
/**
 * Verifica se o veículo informado realmente pertence ao cliente que está sendo agendado. Isso evita inconsistência nos dados.
 * @author Tiago Secundo
 */
public class VerificaVeiculoHandler extends AgendamentoBaseHandler {

    private List<Cliente> clientes;

    public VerificaVeiculoHandler(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    @Override
    public boolean handle(Agenda agenda) {
        System.out.println("[Handler Veiculo] Verificando se o veiculo pertence ao cliente...");

        Cliente cliente = agenda.getCliente();
        Veiculo veiculo = agenda.getVeiculo();

        for (Cliente c : clientes) {
            if (c.getIdCliente() == cliente.getIdCliente()) {
                for (Veiculo v : c.getVeiculo()) {
                    if (v.getPlaca().equalsIgnoreCase(veiculo.getPlaca())) {
                        System.out.println("[Handler Veiculo] Veiculo encontrado.");
                        return super.handle(agenda);
                    }
                }
            }
        }

        System.out.println("[Handler Veículo] Veículo nao pertence ao cliente! Cancelando agendamento.");
        return false;
    }
}