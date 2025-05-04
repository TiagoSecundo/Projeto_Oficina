package Program;

import entities.Cliente;
import entities.Oficina;
import java.util.Scanner;
import entities.Veiculo;

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
        System.out.println("Veículos do cliente:");
        for (Veiculo v : cliente1.getVeiculo()) {
            System.out.println(v); // deve mostrar o Uno da Fiat

            System.out.println(cliente1);
            sc.close();
        }
    }
}