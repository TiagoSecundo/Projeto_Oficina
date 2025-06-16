/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Chains.Agendamento;

import entities.Agenda;
import entities.Cliente;

import java.util.List;
/**
 * Valida se o cliente existe na lista de clientes. Imprime mensagens para deixar o passo-a-passo claro durante a execução.
 * @author Tiago Secundo
 */
public class VerificaClienteHandler extends AgendamentoBaseHandler {

    private List<Cliente> clientes;

    public VerificaClienteHandler(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    @Override
    public boolean handle(Agenda agenda) {
        System.out.println("[Handler Cliente] Verificando se o cliente existe...");

        for (Cliente c : clientes) {
            if (c.getIdCliente() == agenda.getCliente().getIdCliente()) {
                System.out.println("[Handler Cliente] Cliente encontrado.");
                return super.handle(agenda); // Chama o próximo da cadeia
            }
        }

        System.out.println("[Handler Cliente] Cliente nao encontrado! Cancelando agendamento.");
        return false;
    }
}