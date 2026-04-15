package br.ufc.quixada.reserva.model;

public class Auditorio extends EspacoFisico {
    private boolean possuiSistemaSom;

    public Auditorio(int id, String nome, int capacidade, boolean possuiSistemaSom) {
        super(id, nome, capacidade);
        this.possuiSistemaSom = possuiSistemaSom;
    }

    public boolean getPossuiSistemaSom() { return possuiSistemaSom; }
}