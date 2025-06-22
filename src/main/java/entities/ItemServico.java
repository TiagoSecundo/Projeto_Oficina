package entities;

public class ItemServico {

    private Produto produto;
    private int quantidade;
    
    public ItemServico(){
        
    }
    /**
     * 
     * @param produto
     * @param quantidade 
     */
    public ItemServico(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }
    /**
     * 
     * @return 
     */
    public Produto getProduto() {
        return produto;
    }
    /**
     * 
     * @return 
     */
    public int getQuantidade() {
        return quantidade;
    }
    /**
     * 
     * @return 
     */
    @Override
    public String toString() {
        return "\n - Produto: " + produto.getNome() + 
               " | Quantidade: " + quantidade +
               " | Preço Unit.: R$" + produto.getPrecoFinal();
    }
}
