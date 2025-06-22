package entities;

import java.time.LocalDate;

public class BalancoMensal {

    private LocalDate data;
    private double receitaTotal;
    private double despesasOperacionais;
    private double salarios;
    private double despesasTotais;
    private double lucroBruto;
    private double lucroLiquido;
    /**
     * 
     * @param data
     * @param receitaTotal
     * @param despesasOperacionais
     * @param salarios
     * @param despesasTotais
     * @param lucroBruto
     * @param lucroLiquido 
     */
    public BalancoMensal(LocalDate data, double receitaTotal, double despesasOperacionais, double salarios,
                          double despesasTotais, double lucroBruto, double lucroLiquido) {
        this.data = data;
        this.receitaTotal = receitaTotal;
        this.despesasOperacionais = despesasOperacionais;
        this.salarios = salarios;
        this.despesasTotais = despesasTotais;
        this.lucroBruto = lucroBruto;
        this.lucroLiquido = lucroLiquido;
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
     * @return 
     */
    public double getReceitaTotal() {
        return receitaTotal;
    }
    /**
     * 
     * @return 
     */
    public double getDespesasOperacionais() {
        return despesasOperacionais;
    }
    /**
     * 
     * @return 
     */
    public double getSalarios() {
        return salarios;
    }
    /**
     * 
     * @return 
     */
    public double getDespesasTotais() {
        return despesasTotais;
    }
    /**
     * 
     * @return 
     */
    public double getLucroBruto() {
        return lucroBruto;
    }
    /**
     * 
     * @return 
     */
    public double getLucroLiquido() {
        return lucroLiquido;
    }
    /**
     * 
     * @return 
     */
    @Override
    public String toString() {
        return "\n=== BALANÇO MENSAL (" + data + ") ===" +
                "\nReceita Total........: R$ " + receitaTotal +
                "\nDespesas Operacionais: R$ " + despesasOperacionais +
                "\nSalários Funcionários: R$ " + salarios +
                "\nDespesas Totais......: R$ " + despesasTotais +
                "\nLucro Bruto..........: R$ " + lucroBruto +
                "\nLucro Líquido........: R$ " + lucroLiquido +
                "\n==============================";
    }
}
