package Chains;

import entities.Cliente;

public class VerificaVeiculoDoClienteHandler extends ClienteBaseHandler {

    /**
     * Verifica se o cliente possui veículos cadastrados em seu nome.
     *
     * @param cliente O cliente a ser validado.
     * @return false se o cliente não tiver veículos; true caso contrário.
     */
    @Override
    public boolean verificar(Cliente cliente) {
        System.out.println("[Handler 2] Verificando se o cliente tem veiculos...");

        if (cliente.getVeiculo() == null || cliente.getVeiculo().isEmpty()) {
            System.out.println("[Handler 2] Cliente nao possui veiculos cadastrados!");
            return false;
        }

        System.out.println("[Handler 2] Cliente possui veiculos. Passando para o proximo handler...");
        return super.verificar(cliente);
    }

}
