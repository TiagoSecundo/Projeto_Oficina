package services;

import entities.*;
import utils.PersistenciaUtil;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Serviço responsável pela geração de balanços e relatórios financeiros.
 * Requer autenticação de gerente para acesso às funcionalidades.
 */
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

    /**
     * Exibe o menu principal de relatórios e balanços.
     * Controla o fluxo de navegação e autenticação do gerente.
     */
    public void exibirMenuRelatorios() {
        Scanner sc = new Scanner(System.in);

        if (gerentes.isEmpty()) {
            System.out.println("Nenhum gerente cadastrado. Acesso ao módulo de relatórios negado.");
            return;
        }

        System.out.println("\n=== MÓDULO DE RELATÓRIOS E BALANÇO ===");
        System.out.print("Para acessar, digite a senha do gerente: ");
        String senha = sc.nextLine();

        boolean senhaValida = gerentes.stream().anyMatch(g -> g.getSenha().equals(senha));

        if (!senhaValida) {
            System.out.println("Acesso negado. Senha incorreta.");
            return;
        }

        int opcao;
        do {
            System.out.println("\n--- Menu de Relatórios ---");
            System.out.println("1. Gerar Balanço Mensal Completo");
            System.out.println("2. Gerar Relatório de Vendas Diário");
            System.out.println("3. Gerar Relatório de Vendas Mensal");
            System.out.println("4. Gerar Relatório de Despesas Mensal");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            opcao = sc.nextInt();
            sc.nextLine(); // Limpar buffer

            switch (opcao) {
                case 1:
                    gerarBalancoMensal();
                    break;
                case 2:
                    System.out.print("Digite a data (yyyy-MM-dd): ");
                    LocalDate dataDiario = LocalDate.parse(sc.nextLine());
                    gerarRelatorioVendasDiario(dataDiario);
                    break;
                case 3:
                     System.out.print("Digite o mês (1-12): ");
                    int mesVendas = sc.nextInt();
                    System.out.print("Digite o ano: ");
                    int anoVendas = sc.nextInt();
                    sc.nextLine();
                    gerarRelatorioVendasMensal(mesVendas, anoVendas);
                    break;
                case 4:
                    System.out.print("Digite o mês (1-12): ");
                    int mesDespesas = sc.nextInt();
                    System.out.print("Digite o ano: ");
                    int anoDespesas = sc.nextInt();
                    sc.nextLine();
                    gerarRelatorioDespesasMensal(mesDespesas, anoDespesas);
                    break;
                case 0:
                    System.out.println("Retornando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        } while (opcao != 0);
    }

    /**
     * Gera e salva um balanço completo do mês corrente.
     */
    private void gerarBalancoMensal() {
        // Receitas do mês atual
        double receitaTotal = ordens.stream()
                .filter(os -> os.getDataHora().getMonth() == LocalDate.now().getMonth() && os.getDataHora().getYear() == LocalDate.now().getYear())
                .mapToDouble(OrdemServico::getValorTotal)
                .sum();

        // Despesas Operacionais do mês atual
        double despesasOperacionais = despesas.stream()
                .filter(d -> d.getData().getMonth() == LocalDate.now().getMonth() && d.getData().getYear() == LocalDate.now().getYear())
                .mapToDouble(Despesas::getValor)
                .sum();

        // Salários
        double totalSalarios = funcionarios.stream().mapToDouble(Funcionario::getSalario).sum()
                + gerentes.stream().mapToDouble(Gerente::getSalario).sum();

        double despesasTotais = despesasOperacionais + totalSalarios;

        // Lucro
        double lucroBruto = receitaTotal;
        double lucroLiquido = lucroBruto - despesasTotais;

        BalancoMensal balanco = new BalancoMensal(LocalDate.now(), receitaTotal, despesasOperacionais, totalSalarios, despesasTotais, lucroBruto, lucroLiquido);

        balancos.add(balanco);
        PersistenciaUtil.salvarEmArquivo(balancos, "balancoMensal.json");

        System.out.println("\nBalanço Gerado e Salvo com Sucesso!");
        System.out.println(balanco);
    }

    /**
     * Gera um relatório de vendas para uma data específica.
     * @param data A data para a qual o relatório será gerado.
     */
    public void gerarRelatorioVendasDiario(LocalDate data) {
        System.out.println("\n--- Relatório de Vendas para " + data + " ---");
        double totalVendas = 0.0;

        List<OrdemServico> vendasDoDia = ordens.stream()
                .filter(os -> os.getDataHora().toLocalDate().isEqual(data) && "Emitida Nota Fiscal".equalsIgnoreCase(os.getStatus()))
                .collect(Collectors.toList());

        if (vendasDoDia.isEmpty()) {
            System.out.println("Nenhuma venda registrada para esta data.");
        } else {
            for (OrdemServico os : vendasDoDia) {
                System.out.println(os);
                totalVendas += os.getValorTotal();
            }
            System.out.printf("Total de Vendas do Dia: R$ %.2f%n", totalVendas);
        }
    }

    /**
     * Gera um relatório de vendas para um mês e ano específicos.
     * @param mes O mês (1-12) do relatório.
     * @param ano O ano do relatório.
     */
    public void gerarRelatorioVendasMensal(int mes, int ano) {
        System.out.printf("\n--- Relatório de Vendas para %s de %d ---\n", Month.of(mes), ano);
        double totalVendas = 0.0;

         List<OrdemServico> vendasDoMes = ordens.stream()
                .filter(os -> os.getDataHora().getMonthValue() == mes && os.getDataHora().getYear() == ano && "Emitida Nota Fiscal".equalsIgnoreCase(os.getStatus()))
                .collect(Collectors.toList());

        if (vendasDoMes.isEmpty()){
            System.out.println("Nenhuma venda registrada para este mês.");
        } else {
             for (OrdemServico os : vendasDoMes) {
                System.out.println(os);
                totalVendas += os.getValorTotal();
            }
            System.out.printf("Total de Vendas do Mês: R$ %.2f%n", totalVendas);
        }
    }

     /**
     * Gera um relatório de despesas para um mês e ano específicos.
     * @param mes O mês (1-12) do relatório.
     * @param ano O ano do relatório.
     */
    public void gerarRelatorioDespesasMensal(int mes, int ano) {
        System.out.printf("\n--- Relatório de Despesas para %s de %d ---\n", Month.of(mes), ano);
        double totalDespesas = 0.0;

        List<Despesas> despesasDoMes = despesas.stream()
                .filter(d -> d.getData().getMonthValue() == mes && d.getData().getYear() == ano)
                .collect(Collectors.toList());
        
        if (despesasDoMes.isEmpty()){
            System.out.println("Nenhuma despesa registrada para este mês.");
        } else {
            for(Despesas d : despesasDoMes){
                System.out.println(d);
                totalDespesas += d.getValor();
            }
            System.out.printf("Total de Despesas do Mês: R$ %.2f%n", totalDespesas);
        }
    }

    public List<BalancoMensal> getBalancos() {
        return balancos;
    }
}