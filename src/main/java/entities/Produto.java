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

    public Produto(int id, String nome, double precoCusto, double precoFinal, int quantidade, String status) {
        this.id = id;
        this.nome = nome;
        this.precoCusto = precoCusto;
        this.quantidade = quantidade;
        this.status = status;
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
    }

    public double getPrecoFinal() {
        return precoFinal;
    }

    public void setPrecoFinal(double precoFinal) {
        this.precoFinal = precoFinal;
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
        @Override
    public String toString() {
        return "Produto{" + "id=" + id + ", nome=" + nome + ", precoCusto=" + precoCusto + 
                ", precoFinal=" + precoFinal + ", quantidade=" + quantidade + ", status=" + status + '}';
    }
}
