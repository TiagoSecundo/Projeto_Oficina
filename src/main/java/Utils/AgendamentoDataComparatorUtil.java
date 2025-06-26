package utils;

import entities.Agenda;
import java.util.Comparator;

public class AgendamentoDataComparatorUtil implements Comparator<Agenda> {
    @Override
    public int compare(Agenda a1, Agenda a2) {
        return a1.getDataAgendamento().compareTo(a2.getDataAgendamento());
    }
}
