package entities;

import java.util.ArrayList;
import java.time.LocalDate;

public class Servico {

    private int id;
    private String descricao;
    private double valor;
    private LocalDate data;
    private Veiculo veiculo;
    private Funcionario responsavel;
    private ArrayList<Produto> pecasUtilizadas;
    //preciso ainda vincular o servico ao funcionario e ao carro 

    public Servico() {
    }

    /**
     *
     * @param id
     * @param descricao
     * @param valor
     * @param data
     */
    public Servico(int id, String descricao, double valor, LocalDate data) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
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
    public String getDescricao() {
        return descricao;
    }

    /**
     *
     * @param descricao
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     *
     * @return
     */
    public double getValor() {
        return valor;
    }

    /**
     *
     * @param valor
     */
    public void setValor(double valor) {
        this.valor = valor;
    }

    /**
     *
     * @return
     */
    public LocalDate getData() {
        return data;
    }

    /**
     *
     * @param data
     */
    public void setData(LocalDate data) {
        this.data = data;
    }

    /**
     *
     * @return
     */
    // toString
    @Override
    public String toString() {
        return String.format(
                "Servico [ID: %d, Descricao: %s, Valor: R$%.2f, Data: %s]",
                id, descricao, valor, data
        );
    }
}
