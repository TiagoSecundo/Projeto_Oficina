package Program;

import entities.Cliente;
import entities.Oficina;
import java.util.Scanner;
import entities.Veiculo;
import entities.Funcionario;
import entities.Gerente;
import entities.Mecanico;
import entities.Produto;
import entities.Servico;
import java.time.LocalDate;
import java.time.LocalDateTime;
import entities.Agenda;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Oficina oficina = new Oficina();
        int opcao;

 do {
            System.out.println("\n===== MENU PRINCIPAL =====");
            System.out.println("1 - Cadastrar Cliente");
            System.out.println("2 - Editar Cliente");
            System.out.println("3 - Remover Cliente");
            System.out.println("4 - Listar Clientes");
            System.out.println("5 - Cadastrar Veículo");
            System.out.println("6 - Editar Veículo");
            System.out.println("7 - Remover Veículo");
            System.out.println("8 - Cadastrar Funcionário");
            System.out.println("9 - Editar Funcionário");
            System.out.println("10 - Remover Funcionário");
            // Futuras implementações:
            // System.out.println("11 - Registrar Produto");
            // System.out.println("12 - Registrar Serviço");
            // System.out.println("13 - Agendar Serviço");
            // System.out.println("14 - Consultar Agenda por Data");
            // System.out.println("15 - Gerar Relatório Mensal");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = sc.nextInt();
            sc.nextLine(); // limpar buffer

            switch (opcao) {
                case 1:
                    oficina.cadastrarCliente();
                    break;
                case 2:
                    oficina.editarCliente();
                    break;
                case 3:
                    oficina.removerCliente();
                    break;
                case 4:
                    oficina.listarClientes();
                    break;
                case 5:
                    oficina.cadastrarVeiculo();
                    break;
                case 6:
                    oficina.editarVeiculo();
                    break;
                case 7:
                    oficina.removerVeiculo();
                    break;
                case 8:
                    oficina.cadastrarFuncionario();
                    break;
                case 9:
                    oficina.editarFuncionario();
                    break;
                case 10:
                    oficina.removerFuncionario();
                    break;
                case 0:
                    System.out.println("Encerrando o sistema...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }

        } while (opcao != 0);

        sc.close();
    }
}
