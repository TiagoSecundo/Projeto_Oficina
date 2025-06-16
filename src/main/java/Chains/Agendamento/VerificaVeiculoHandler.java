package Chains.Agendamento;

import entities.Agenda;
import entities.Cliente;
import entities.Veiculo;

import java.util.List;
/**
 * Handler responsável por validar se o veículo informado no agendamento
 * realmente pertence ao cliente selecionado.
 * Faz parte da cadeia de responsabilidade para validação de agendamentos.
 * 
 * @author Tiago Secundo
 */
public class VerificaVeiculoHandler extends AgendamentoBaseHandler {

    private List<Cliente> clientes;
    /**
     * Construtor do handler.
     * 
     * @param clientes Lista de clientes cadastrados no sistema.
     */
    public VerificaVeiculoHandler(List<Cliente> clientes) {
        this.clientes = clientes;
    }
    /**
     * Executa a validação do veículo.
     * 
     * @param agenda Objeto de agendamento que será validado.
     * @return true se o veículo pertence ao cliente e passa para o próximo handler;
     *         false se não pertence, encerrando a cadeia.
     */
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