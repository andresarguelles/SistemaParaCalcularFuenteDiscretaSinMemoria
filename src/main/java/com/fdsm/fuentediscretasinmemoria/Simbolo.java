package com.fdsm.fuentediscretasinmemoria;

public class Simbolo {
    private char simbolo;
    private int frecuencia;
    private double probabilidad;

    public Simbolo(char simbolo, int frecuencia, double probabilidad) {
        this.simbolo = simbolo;
        this.frecuencia = frecuencia;
        this.probabilidad = probabilidad;
    }

    public char getSimbolo() {
        return simbolo;
    }

    public int getFrecuencia() {
        return frecuencia;
    }

    public double getProbabilidad() {
        return probabilidad;
    }
}
