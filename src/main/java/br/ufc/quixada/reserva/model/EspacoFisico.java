package br.ufc.quixada.reserva.model;

import java.io.Serializable;

public abstract class EspacoFisico implements Serializable {
    protected int id;
    protected String nome;
    protected int capacidade;
    protected String tipo;

    public EspacoFisico(int id, String nome, int capacidade, String tipo) {
        this.id = id;
        this.nome = nome;
        this.capacidade = capacidade;
        this.tipo = tipo;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public int getCapacidade() { return capacidade; }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}