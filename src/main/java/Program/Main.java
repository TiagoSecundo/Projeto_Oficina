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

        Cliente cliente1 = new Cliente(1, "Tiago Secundo", "99999-0000", "Rua A, 123", "123.456.789-00", "tiago.secundo@ufvjm.edu.br");
        Veiculo veiculo1 = new Veiculo("ABC-1234", "Fiat", "Uno", 2015, "Branco");
        oficina.cadastrarVeiculo(veiculo1, cliente1);

        boolean cadastrado = oficina.cadastrarCliente(cliente1);

        if (cadastrado) {
            System.out.println("Cliente cadastrado com sucesso!");
        } else {
            System.out.println("Cliente já está cadastrado.");
        }
        System.out.println("Veiculos do cliente:");
        for (Veiculo v : cliente1.getVeiculo()) {
            System.out.println(v); // deve mostrar o Uno da Fiat

            System.out.println(cliente1);

        }
        Gerente gerente = new Gerente(10, "Joao Gerente", "Gerente", "joao@email.com", 7000f, "senha123");
        Mecanico mecanico = new Mecanico(20, "Carlos Mecanico", "Mecanico", "carlos@email.com", 4000f, "Motor", "Relatório OK");

        oficina.cadastrarFuncionario(gerente);
        oficina.cadastrarFuncionario(mecanico);

        Produto p1 = new Produto(1, "Oleo de motor", 25.0, 40.0, 10, "disponivel");
        oficina.registrarProduto(p1);
        
        
        Servico servico = new Servico(1, "troca de pneu", 200.75, LocalDate.of(2025, 5, 13));
        oficina.registrarServico(servico);
        
                // Criando agenda
        Agenda agenda1 = new Agenda(
            1,
            cliente1,
            veiculo1,
            "Barulho no motor ao ligar",
            mecanico,
            LocalDateTime.now().plusDays(1),
            "Agendado"
        );

        // Agendando o serviço
        boolean agendado = oficina.agendarServico(agenda1);
        if (agendado) {
            System.out.println("Agendamento realizado com sucesso!");
        } else {
            System.out.println("Agendamento falhou.");
        }
        sc.close();
    }
}
