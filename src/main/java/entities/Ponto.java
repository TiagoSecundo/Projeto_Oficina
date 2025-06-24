package entities;

import java.time.LocalDateTime;

public class Ponto {

    private LocalDateTime entrada;
    private LocalDateTime saida;
    /**
     * 
     * @return 
     */
    public LocalDateTime getEntrada() {
        return entrada;
    }
    /**
     * 
     * @param entrada 
     */
    public void setEntrada(LocalDateTime entrada) {
        this.entrada = entrada;
    }
    /**
     * 
     * @return 
     */
    public LocalDateTime getSaida() {
        return saida;
    }
    /**
     * 
     * @param saida 
     */
    public void setSaida(LocalDateTime saida) {
        this.saida = saida;
    }
    /**
     * 
     * @return 
     */
    @Override
    public String toString() {
        return "Ponto{" + "entrada=" + entrada + ", saida=" + saida + '}'+"\n";
    }
}
