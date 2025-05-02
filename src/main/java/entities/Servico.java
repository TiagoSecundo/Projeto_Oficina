package entities;

import java.util.ArrayList;

import java.time.LocalDate;


public class Servico {

    private int id;
    private String descricao;
    private double valor;
    private LocalDate data;
    //preciso ainda vincular o servico ao funcionario e ao carro 

    public Servico() {
    }

    public Servico(int id, String descricao, double valor, LocalDate data) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    // toString
    @Override
    public String toString() {
        return String.format(
                "Serviço [ID: %d, Descrição: %s, Valor: R$%.2f, Data: %s]",
                id, descricao, valor, data
        );
    }
}
