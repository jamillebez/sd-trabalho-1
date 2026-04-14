package br.ufc.quixada.reserva.model;

public class Sala extends EspacoFisico {
    private boolean possuiProjetor;

    public Sala(int id, String nome, int capacidade, boolean possuiProjetor) {
        super(id, nome, capacidade);
        this.possuiProjetor = possuiProjetor;
    }
}