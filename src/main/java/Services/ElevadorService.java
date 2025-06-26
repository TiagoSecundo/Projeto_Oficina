package services;

import entities.Elevador;
import java.util.List;
import java.util.Scanner;

public class ElevadorService {

    private List<Elevador> elevadores;
    
    public void menuElevadores() {
    Scanner sc = new Scanner(System.in);
    int opcao;

    do {
        System.out.println("\n--- Menu de Elevadores ---");
        System.out.println("1. Listar elevadores");
        System.out.println("2. Liberar elevador");
        System.out.println("0. Voltar");
        System.out.print("Escolha uma opção: ");
        opcao = sc.nextInt();
        sc.nextLine();

        switch (opcao) {
            case 1 -> listarElevadores();
            case 2 -> liberarElevador();
            case 0 -> System.out.println("Voltando...");
            default -> System.out.println("Opção inválida.");
        }
    } while (opcao != 0);
}


    public ElevadorService(List<Elevador> elevadores) {
        this.elevadores = elevadores;
    }

    public Elevador buscarElevadorDisponivelParaServico(String tipoServico) {
    for (Elevador e : elevadores) {
        if (e.getStatus().equalsIgnoreCase("Livre")) {
            if (tipoServico.equalsIgnoreCase("balanceamento") && e.getTipo().equalsIgnoreCase("Balanceamento")) {
                return e;
            } else if (!tipoServico.equalsIgnoreCase("balanceamento") && e.getTipo().equalsIgnoreCase("Geral")) {
                return e;
            }
        }
    }
    System.out.println("Nenhum elevador disponível para este tipo de serviço.");
    return null;
}


    public void liberarElevador() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Digite o ID do elevador para liberar: ");
        int id = sc.nextInt();
        sc.nextLine();

        for (Elevador e : elevadores) {
            if (e.getId() == id) {
                if (e.getStatus().equalsIgnoreCase("Livre")) {
                    System.out.println("Elevador já está livre.");
                    return;
                }
                e.setStatus("Livre");
                e.setVeiculoNaPlataforma(null);
                e.setServicoEmExecucao(null);
                System.out.println("Elevador " + id + " liberado.");
                return;
            }
        }
        System.out.println("Elevador com ID " + id + " não encontrado.");
    }

    public void liberarElevadorPorId(int id) {
        for (Elevador e : elevadores) {
            if (e.getId() == id) {
                e.setStatus("Livre");
                e.setVeiculoNaPlataforma(null);
                e.setServicoEmExecucao(null);
                return;
            }
        }
    }

    public void listarElevadores() {
        if (elevadores.isEmpty()) {
            System.out.println("Nenhum elevador cadastrado.");
            return;
        }

        System.out.println("\n--- Lista de Elevadores ---");
        for (Elevador e : elevadores) {
            System.out.println(e);
        }
    }
    public Elevador alocarElevador(String tipoServico) {
    for (Elevador e : elevadores) {
        if (e.getStatus().equalsIgnoreCase("Livre")) {
            // Balanceamento só no elevador 1
            if (tipoServico.equalsIgnoreCase("Balanceamento") && e.getId() == 1) {
                return e;
            }

            // Outros serviços apenas nos elevadores 2 e 3
            if (!tipoServico.equalsIgnoreCase("Balanceamento") && e.getId() != 1) {
                return e;
            }
        }
    }

    return null; // Nenhum elevador adequado disponível
}

    public List<Elevador> getElevadores() {
        return elevadores;
    }
}
