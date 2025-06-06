package utils;

import entities.Cliente;
import java.util.Comparator;

public class ClienteComparatorUtil implements Comparator<Cliente> {

    @Override
    public int compare(Cliente c1, Cliente c2) {
        return c1.getNome().compareToIgnoreCase(c2.getNome());
    }
}
