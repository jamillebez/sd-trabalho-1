package br.ufc.quixada.reserva.model;

public class Laboratorio extends EspacoFisico implements java.io.Serializable{
    private int quantidadeComputadores;

    public Laboratorio() {
        super(0, "", 0, "laboratorio");
        this.quantidadeComputadores = 0;
    }

    public Laboratorio(int id, String nome, int capacidade, int quantidadeComputadores) {
        super(id, nome, capacidade, "laboratorio");
        this.quantidadeComputadores = quantidadeComputadores;
    }

    public int getQuantidadeComputadores() { return quantidadeComputadores; }
}