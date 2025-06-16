package Chains;

import entities.Cliente;

public abstract class ClienteBaseHandler implements ClienteHandler {

    protected ClienteHandler proximo;

    /**
     * Define qual será o próximo handler na cadeia de responsabilidades. Ao
     * fazer isso, conectamos dinamicamente uma nova etapa de validação ao final
     * da corrente. Isso permite que as validações sejam executadas em ordem,
     * uma após a outra.
     */
    @Override
    public void setNext(ClienteHandler proximo) {
        this.proximo = proximo;
    }

    /**
     *
     * Este método representa o ponto em que a requisição passa para o próximo
     * handler. Caso não haja mais ninguém na cadeia, significa que todas as
     * validações foram concluídas com sucesso.
     * @param cliente
     * @return
     */
    @Override
    public boolean verificar(Cliente cliente) {
        if (proximo != null) {
            return proximo.verificar(cliente);
        }
        return true;
    }
}
