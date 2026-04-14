package br.ufc.quixada.reserva.model;

import java.io.Serializable;

public abstract class EspacoFisico implements Serializable {
    protected int id;
    protected String nome;
    protected int capacidade;

    public EspacoFisico(int id, String nome, int capacidade) {
        this.id = id;
        this.nome = nome;
        this.capacidade = capacidade;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public int getCapacidade() { return capacidade; }
}