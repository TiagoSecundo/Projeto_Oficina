package Chains.Agendamento;

import entities.Agenda;
/**
 * Classe base que fornece a implementação padrão para o método handle(), que apenas passa o controle para o próximo handler. Reutilizável e evita repetição de código.
 * @author Tiago Secundo
 */
public abstract class AgendamentoBaseHandler implements AgendamentoHandler {
    private AgendamentoHandler next;

    @Override
    public void setNext(AgendamentoHandler next) {
        this.next = next;
    }

    @Override
    public boolean handle(Agenda agenda) {
        // Se não tiver próximo, chegou ao fim da cadeia
        if (next != null) {
            return next.handle(agenda);
        }
        return true;
    }
}