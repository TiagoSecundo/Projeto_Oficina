package services;

import entities.Elevador;
import entities.TipoServico; // ✅ Importar TipoServico
import utils.PersistenciaUtil;

import java.util.List;
import java.util.Scanner;

public class ElevadorService {

    private List<Elevador> elevadores;

    public ElevadorService(List<Elevador> elevadores) {
        this.elevadores = elevadores;
    }

    public void menuElevadores() {
        Scanner sc = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n--- Menu de Elevadores ---");
            System.out.println("1. Listar elevadores");
            System.out.println("2. Liberar elevador");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opcao: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1 ->
                    listarElevadores();
                case 2 ->
                    liberarElevadorViaMenu();
                case 0 ->
                    System.out.println("Voltando...");
                default ->
                    System.out.println("Opcao invalida.");
            }
        } while (opcao != 0);
    }

    public void liberarElevadorViaMenu() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Digite o ID do elevador para liberar: ");
        int id = sc.nextInt();
        sc.nextLine();
        liberarElevadorPorId(id);
    }

    // ✅ CORREÇÃO: Método alocarElevador agora recebe TipoServico
    public Elevador alocarElevador(String veiculoPlaca, TipoServico tipoServico) {
        // 1. Tentar alocar elevador de balanceamento se o serviço for de BALANCEAMENTO
        if (tipoServico == TipoServico.BALANCEAMENTO) {
            for (Elevador elevador : elevadores) {
                if (elevador.isExclusivoBalanceamento()) {
                    if (elevador.getStatus().equalsIgnoreCase("Livre")) {
                        elevador.setStatus("Ocupado - Balanceamento");
                        elevador.setVeiculoNaPlataforma(veiculoPlaca);
                        elevador.setServicoEmExecucao(tipoServico.getDescricao()); // Usa a descrição do enum
                        PersistenciaUtil.salvarEmArquivo(elevadores, "elevadores.json"); //
                        System.out.println("Elevador " + elevador.getId() + " alocado para Balanceamento.");
                        return elevador;
                    } else {
                        System.out.println("Elevador de Balanceamento (ID " + elevador.getId() + ") não está livre.");
                        return null;
                    }
                }
            }
        }

        // 2. Se não for balanceamento OU o elevador de balanceamento não estiver disponível/não existir
        // Tentar alocar um elevador geral
        for (Elevador elevador : elevadores) {
            if (!elevador.isExclusivoBalanceamento() && elevador.getStatus().equalsIgnoreCase("Livre")) {
                elevador.setStatus("Ocupado");
                elevador.setVeiculoNaPlataforma(veiculoPlaca);
                elevador.setServicoEmExecucao(tipoServico.getDescricao()); // Usa a descrição do enum
                PersistenciaUtil.salvarEmArquivo(elevadores, "elevadores.json"); //
                System.out.println("Elevador " + elevador.getId() + " alocado para serviço geral.");
                return elevador;
            }
        }

        System.out.println("Nenhum elevador disponível para esse tipo de serviço.");
        return null;
    }

    public void liberarElevadorPorId(int id) {
        for (Elevador e : elevadores) {
            if (e.getId() == id) {
                e.setStatus("Livre");
                e.setVeiculoNaPlataforma(null);
                e.setServicoEmExecucao(null);
                System.out.println("Elevador " + id + " liberado.");
                PersistenciaUtil.salvarEmArquivo(elevadores, "elevadores.json"); //
                return;
            }
        }
        System.out.println("Elevador com ID " + id + " nao encontrado.");
    }

    public Elevador buscarElevadorDisponivel() { // Manter este método se for usado em outro lugar, mas ele não considera o tipo de serviço.
        for (Elevador e : elevadores) {
            if (e.getStatus().equalsIgnoreCase("Livre")) {
                return e;
            }
        }
        return null;
    }

    public void listarElevadores() {
        if (elevadores.isEmpty()) {
            System.out.println("Nenhum elevador cadastrado.");
            return;
        }

        System.out.println("\n--- Lista de Elevadores ---");
        for (Elevador e : elevadores) {
            System.out.println("ID: " + e.getId()
                               + " | Status: " + e.getStatus()
                               + " | Veículo: " + (e.getVeiculoNaPlataforma() != null ? e.getVeiculoNaPlataforma() : "N/A")
                               + " | Serviço: " + (e.getServicoEmExecucao() != null ? e.getServicoEmExecucao() : "N/A")
                               + " | Exclusivo Balanceamento: " + e.isExclusivoBalanceamento());
        }
    }

    public Elevador buscarElevadorPorId(int id) {
        for (Elevador e : elevadores) {
            if (e.getId() == id) {
                return e;
            }
        }
        return null;
    }

    public List<Elevador> getElevadores() {
        return elevadores;
    }
}