package br.ufc.quixada.reserva.model;

public class Sala extends EspacoFisico implements java.io.Serializable {
    private boolean possuiProjetor;

        public Sala() {
            super(0, "", 0, "sala");
            this.possuiProjetor = false;
        }

    public Sala(int id, String nome, int capacidade, boolean possuiProjetor) {
        super(id, nome, capacidade, "sala");
        this.possuiProjetor = possuiProjetor;
    }


    public boolean getPossuiProjetor() { return possuiProjetor; }
}