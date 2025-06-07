package Utils;
import java.util.Comparator;
import entities.Produto;

public class ProdutoPrecoComparatorUtil implements Comparator<Produto> {

    @Override
    public int compare(Produto p1, Produto p2) {
        // Comparação manual sem usar compareTo()
        if (p1.getPrecoFinal() < p2.getPrecoFinal()) {
            return -1;
        } else if (p1.getPrecoFinal() > p2.getPrecoFinal()) {
            return 1;
        } else {
            return 0;
        }
    }
}