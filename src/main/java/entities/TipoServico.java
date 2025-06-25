// src/main/java/entities/TipoServico.java
package entities;

public enum TipoServico {
    BALANCEAMENTO("Balanceamento e Alinhamento de Rodas"),
    REVISAO("Revisão Periódica e Manutenção Preventiva"),
    TROCA_OLEO("Troca de Óleo e Filtros"),
    FREIOS("Manutenção de Freios"),
    SUSPENSAO("Manutenção de Suspensão"),
    DIAGNOSTICO("Diagnóstico de Falhas e Eletrônica"),
    PNEUS("Troca e Reparo de Pneus (sem balanceamento exclusivo)"),
    OUTROS("Outros Serviços Não Listados");

    private String descricao;

    TipoServico(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return this.name() + " - " + this.descricao;
    }
}