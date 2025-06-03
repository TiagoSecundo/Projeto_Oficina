package utils;

import entities.Agenda;
import java.util.Comparator;

public class AgendamentoComparator implements Comparator<Agenda> {

    @Override
    public int compare(Agenda a1, Agenda a2) {
        // Primeiro compara pela data e hora do agendamento
        return a1.getDataAgendamento().compareTo(a2.getDataAgendamento());
    }
}
