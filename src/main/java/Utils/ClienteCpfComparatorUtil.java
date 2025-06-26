package utils;

import entities.Cliente;
import java.util.Comparator;

public class ClienteCpfComparatorUtil implements Comparator<Cliente> {
    @Override
    public int compare(Cliente c1, Cliente c2) {
        return c1.getCpf().compareTo(c2.getCpf());
    }
}