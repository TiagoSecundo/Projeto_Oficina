package entities;
// ainda preciso comentar

public class Produto {

    private int id;
    private String nome;
    private double precoCusto;
    private double precoFinal;
    private int quantidade;
    private String status;

    public Produto() {
    }

    /**
     *
     * @param id
     * @param nome
     * @param precoCusto
     * @param precoFinal
     * @param quantidade
     * @param status
     */
    public Produto(int id, String nome, double precoCusto, double precoFinal, int quantidade, String status) {
        this.id = id;
        this.nome = nome;
        this.precoCusto = precoCusto;
        this.quantidade = quantidade;
        this.status = status;
    }

    /**
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getNome() {
        return nome;
    }

    /**
     *
     * @param nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     *
     * @return
     */
    public double getPrecoCusto() {
        return precoCusto;
    }

    /**
     *
     * @param precoCusto
     */
    public void setPrecoCusto(double precoCusto) {
        this.precoCusto = precoCusto;
    }

    /**
     *
     * @return
     */
    public double getPrecoFinal() {
        return precoFinal;
    }

    /**
     *
     * @param precoFinal
     */
    public void setPrecoFinal(double precoFinal) {
        this.precoFinal = precoFinal;
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
     * @param quantidade
     */
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    /**
     *
     * @return
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Produto{" + "id=" + id + ", nome=" + nome + ", precoCusto=" + precoCusto + ", precoFinal=" + precoFinal + ", quantidade=" + quantidade + ", status=" + status + '}';
    }

    /**
     *
     * @return
     */

}
