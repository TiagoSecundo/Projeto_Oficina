package Chains.Agendamento;

import entities.Agenda;
/**
 * Classe base que fornece a implementação padrão para o método handle(), que apenas passa o controle para o próximo handler. Reutilizável e evita repetição de código.
 * @author Tiago Secundo
 */
public abstract class AgendamentoBaseHandler implements AgendamentoHandler {
    private AgendamentoHandler next; // Referência para o próximo handler na cadeia
    /**
     * Método que define quem será o próximo handler da cadeia.
     * Permite montar a sequência de validações.
     * 
     * @param next Próximo handler na cadeia
     */
    @Override
    public void setNext(AgendamentoHandler next) {
        this.next = next;
    }
    /**
     * Implementação padrão do método handle().
     * Caso o handler atual não trate a solicitação (ou após tratar), 
     * ele repassa para o próximo handler, se houver.
     * 
     * @param agenda Objeto que contém os dados do agendamento a ser validado.
     * @return true se todos os handlers da cadeia aprovarem, 
     *         ou false se algum handler reprovar.
     */
    @Override
    public boolean handle(Agenda agenda) {
        // Se não tiver próximo, chegou ao fim da cadeia
        if (next != null) {
            return next.handle(agenda);
        }
        return true;
    }
}