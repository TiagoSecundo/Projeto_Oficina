package entities;

import java.time.LocalDate;

public class Despesas {

    private int id;
    private String descricao;
    private double valor;
    private LocalDate data;

    public Despesas(int id, String descricao, double valor, LocalDate data) {
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
     * @return 
     */
    public String getDescricao() {
        return descricao;
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
     * @return 
     */
    public LocalDate getData() {
        return data;
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
     * @param descricao 
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
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
     * @param data 
     */
    public void setData(LocalDate data) {
        this.data = data;
    }
    /**
     * 
     * @return 
     */
    @Override
    public String toString() {
        return "Despesas{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                ", valor=" + valor +
                ", data=" + data +
                '}' + "\n";
    }
}
