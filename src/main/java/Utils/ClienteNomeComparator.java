// Em um novo arquivo: utils/ClienteNomeComparator.java
package utils;

import entities.Cliente;
import java.util.Comparator;

public class ClienteNomeComparator implements Comparator<Cliente> {
<<<<<<< Updated upstream
=======

>>>>>>> Stashed changes
    @Override
    public int compare(Cliente c1, Cliente c2) {
        return c1.getNome().compareTo(c2.getNome());
    }
<<<<<<< Updated upstream
}
=======
}
>>>>>>> Stashed changes
