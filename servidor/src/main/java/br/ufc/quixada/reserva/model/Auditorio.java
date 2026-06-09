package br.ufc.quixada.reserva.model;

public class Auditorio extends EspacoFisico implements java.io.Serializable {
    private boolean possuiSistemaSom;

    public Auditorio() {
        super(0, "", 0, "auditorio");
        this.possuiSistemaSom = false;
    }

    public Auditorio(int id, String nome, int capacidade, boolean possuiSistemaSom) {
        super(id, nome, capacidade, "auditorio");
        this.possuiSistemaSom = possuiSistemaSom;
    }

    public boolean getPossuiSistemaSom() { return possuiSistemaSom; }
}