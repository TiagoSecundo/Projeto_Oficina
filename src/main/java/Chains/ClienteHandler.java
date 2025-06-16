package Chains;

import entities.Cliente;

public interface ClienteHandler {

    /**
     * Este método define quem será o próximo responsável (handler) a tratar a
     * solicitação. A ideia do padrão Chain of Responsibility é encadear vários
     * objetos do tipo Handler, de modo que se um não souber ou não puder tratar
     * a requisição, ele passa a responsabilidade para o próximo.
     *
     * @param proximo O próximo handler na cadeia de responsabilidade
     */
    void setNext(ClienteHandler proximo);

    /**
     * Este método representa a verificação a ser feita. Cada handler da cadeia
     * pode realizar uma verificação específica (ex: verificar se o cliente
     * existe, se está ativo, etc). Se o handler atual não puder ou não quiser
     * tratar, ele repassa para o próximo handler, se existir.
     *
     * @param cliente O cliente que está sendo verificado na cadeia
     * @return true se a verificação passar por toda a cadeia com sucesso, false
     * se falhar em algum ponto
     */
    boolean verificar(Cliente cliente);
}
