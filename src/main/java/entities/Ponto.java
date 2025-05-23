package entities;

import java.time.LocalDateTime;

public class Ponto {

    private LocalDateTime entrada;
    private LocalDateTime saida;

    public LocalDateTime getEntrada() {
        return entrada;
    }

    public void setEntrada(LocalDateTime entrada) {
        this.entrada = entrada;
    }

    public LocalDateTime getSaida() {
        return saida;
    }

    public void setSaida(LocalDateTime saida) {
        this.saida = saida;
    }

    @Override
    public String toString() {
        return "Ponto{" + "entrada=" + entrada + ", saida=" + saida + '}';
    }
}
