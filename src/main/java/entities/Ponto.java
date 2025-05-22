/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.util.ArrayList;
import java.time.LocalDateTime;

public class Ponto {

    private LocalDateTime pontoEntrada;
    private LocalDateTime pontoSaida;

// Getters e Setters
    public LocalDateTime getPontoEntrada() {
        return pontoEntrada;
    }

    public void setPontoEntrada(LocalDateTime pontoEntrada) {
        this.pontoEntrada = pontoEntrada;
    }

    public LocalDateTime getPontoSaida() {
        return pontoSaida;
    }

    public void setPontoSaida(LocalDateTime pontoSaida) {
        this.pontoSaida = pontoSaida;
    }

}
