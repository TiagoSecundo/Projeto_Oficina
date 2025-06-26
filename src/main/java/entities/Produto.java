package entities;

public class Produto {

    private int id;
    private String nome;
    private double precoCusto;
    private double precoFinal;
    private int quantidade;
    private String status;
    private String fornecedor;

    public Produto() {
    }

    /**
     * Construtor que já calcula o preço final automaticamente com 30% de lucro.
     * 
     * @param id
     * @param nome
     * @param precoCusto
     * @param quantidade
     * @param status
     * @param fornecedor
     */
    public Produto(int id, String nome, double precoCusto, int quantidade, String status, String fornecedor) {
        this.id = id;
        this.nome = nome;
        this.precoCusto = precoCusto;
        this.precoFinal = precoCusto * 1.3; // ✅ Calcula o preço final automaticamente
        this.quantidade = quantidade;
        this.status = status;
        this.fornecedor = fornecedor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPrecoCusto() {
        return precoCusto;
    }

    public void setPrecoCusto(double precoCusto) {
        this.precoCusto = precoCusto;
        this.precoFinal = precoCusto * 1.3; // ✅ Sempre atualiza preço final quando custo muda
    }

    public double getPrecoFinal() {
        return precoFinal;
    }



    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", precoCusto=" + precoCusto +
                ", precoFinal=" + precoFinal +
                ", quantidade=" + quantidade +
                ", status='" + status + '\'' +
                ", fornecedor='" + fornecedor + '\'' +
                '}'+"\n";
    }
}
