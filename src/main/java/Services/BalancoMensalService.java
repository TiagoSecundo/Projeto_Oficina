package services;

import entities.*;

import utils.PersistenciaUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class BalancoMensalService {

    private List<OrdemServico> ordens;
    private List<Despesas> despesas;
    private List<Funcionario> funcionarios;
    private List<Gerente> gerentes;
    private List<BalancoMensal> balancos;

    public BalancoMensalService(
            List<OrdemServico> ordens,
            List<Despesas> despesas,
            List<Funcionario> funcionarios,
            List<Gerente> gerentes,
            List<BalancoMensal> balancos
    ) {
        this.ordens = ordens;
        this.despesas = despesas;
        this.funcionarios = funcionarios;
        this.gerentes = gerentes;
        this.balancos = balancos;
    }

    public void gerarBalancoMensal() {
        Scanner sc = new Scanner(System.in);

        if (gerentes.isEmpty()) {
            System.out.println("Nenhum gerente cadastrado. Cadastre um gerente primeiro.");
            return;
        }

        System.out.println("\n=== Gerar Balanço Mensal ===");
        System.out.print("Digite a senha do gerente: ");
        String senha = sc.nextLine();

        boolean senhaValida = false;
        for (Gerente g : gerentes) {
            if (g.getSenha().equals(senha)) {
                senhaValida = true;
                break;
            }
        }

        if (!senhaValida) {
            System.out.println("Acesso negado. Senha incorreta.");
            return;
        }

        // Receitas
        double receitaTotal = 0;
        for (OrdemServico os : ordens) {
            receitaTotal += os.getValorTotal();
        }

        // Despesas Operacionais
        double despesasOperacionais = 0;
        for (Despesas d : despesas) {
            despesasOperacionais += d.getValor();
        }

        // Salários
        double totalSalarios = 0;
        for (Funcionario f : funcionarios) {
            totalSalarios += f.getSalario();
        }
        for (Gerente g : gerentes) {
            totalSalarios += g.getSalario();
        }

        double despesasTotais = despesasOperacionais + totalSalarios;

        // Lucro
        double lucroBruto = receitaTotal;
        double lucroLiquido = lucroBruto - despesasTotais;

        // Criar balanço
        BalancoMensal balanco = new BalancoMensal(
                LocalDate.now(),
                receitaTotal,
                despesasOperacionais,
                totalSalarios,
                despesasTotais,
                lucroBruto,
                lucroLiquido
        );

        balancos.add(balanco);
        PersistenciaUtil.salvarEmArquivo(balancos, "balancoMensal.json");

        System.out.println("\nBalanço Gerado e Salvo com Sucesso!");
        System.out.println(balanco);
    }

    public List<BalancoMensal> getBalancos() {
        return balancos;
    }
}
