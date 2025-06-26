package services;

import entities.BalancoMensal;
import entities.Despesas;
import entities.Funcionario;
import entities.Gerente;
import entities.OrdemServico;
import utils.PersistenciaUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class BalancoMensalService {

    private List<OrdemServico> ordens;
    private List<Despesas> despesas;
    private List<Funcionario> funcionarios;
    private List<BalancoMensal> balancos;

    public BalancoMensalService(
            List<OrdemServico> ordens,
            List<Despesas> despesas,
            List<Funcionario> funcionarios,
            List<BalancoMensal> balancos
    ) {
        this.ordens = ordens;
        this.despesas = despesas;
        this.funcionarios = funcionarios;
        this.balancos = balancos;
    }

    public void gerarBalancoMensal() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n=== Gerar Balanço Mensal ===");

        // ✅ Validação da senha do gerente
        if (!validarSenhaGerente(sc)) {
            System.out.println("Acesso negado. Senha incorreta.");
            return;
        }

        // 🔢 Cálculo das Receitas
        double receitaTotal = 0;
        for (OrdemServico os : ordens) {
            receitaTotal += os.getValorTotal();
        }

        // 🔻 Despesas operacionais (Despesas gerais)
        double despesasOperacionais = 0;
        for (Despesas d : despesas) {
            despesasOperacionais += d.getValor();
        }

        // 💰 Salários (inclui mecânicos e gerentes)
        double totalSalarios = 0;
        for (Funcionario f : funcionarios) {
            totalSalarios += f.getSalario();
        }

        double despesasTotais = despesasOperacionais + totalSalarios;

        // 🔥 Lucros
        double lucroBruto = receitaTotal;
        double lucroLiquido = lucroBruto - despesasTotais;

        // 📜 Criação do objeto balanço
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

        System.out.println("\n✅ Balanço Gerado e Salvo com Sucesso!");
        System.out.println(balanco);
    }


    private boolean validarSenhaGerente(Scanner sc) {
        System.out.print("Digite a senha do gerente: ");
        String senha = sc.nextLine();

        for (Funcionario f : funcionarios) {
            if (f instanceof Gerente) {
                Gerente gerente = (Gerente) f;
                if (gerente.getSenha().equals(senha)) {
                    return true;
                }
            }
        }
        return false;
    }
}
