package br.ufc.quixada.reserva.model;

public class Laboratorio extends EspacoFisico {
    private int quantidadeComputadores;

    public Laboratorio(int id, String nome, int capacidade, int quantidadeComputadores) {
        super(id, nome, capacidade);
        this.quantidadeComputadores = quantidadeComputadores;
    }

    public int getQuantidadeComputadores() { return quantidadeComputadores; }
}