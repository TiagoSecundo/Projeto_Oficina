package utils;

import entities.Agenda;
import java.util.Comparator;

public class AgendamentoIdComparatorUtil implements Comparator<Agenda> {
    @Override
    public int compare(Agenda a1, Agenda a2) {
        return Integer.compare(a1.getId(), a2.getId());
    }
}
