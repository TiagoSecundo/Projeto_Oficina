package Chains;

import entities.Cliente;
import java.util.List;

public class VerificaClienteExistenteHandler extends ClienteBaseHandler {

    private List<Cliente> clientes;
    /**
     * Construtor que recebe a lista de clientes do sistema para realizar a verificação.
     * @param clientes lista de clientes cadastrados.
     */
    public VerificaClienteExistenteHandler(List<Cliente> clientes) {
        this.clientes = clientes;
    }
    /**
     * Verifica se o cliente fornecido existe na lista de clientes cadastrados.
     * @param cliente cliente O cliente a ser verificado.
     * @return  false caso o cliente não exista. Caso exista, passa a responsabilidade para o próximo handler.
     */
    @Override
    public boolean verificar(Cliente cliente) {
        System.out.println("[Handler 1] Verificando se o cliente existe na lista...");

        boolean existe = clientes.contains(cliente);
        if (!existe) {
            System.out.println("[Handler 1] Cliente nao encontrado na base!");
            return false;
        }

        System.out.println("[Handler 1] Cliente encontrado. Passando para o proximo handler...");
        return super.verificar(cliente);
    }
}
