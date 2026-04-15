package br.ufc.quixada.reserva.model;

import java.util.ArrayList;
import java.util.List;

public class Campus {
    private String nomeCampus;
    private List<EspacoFisico> espacos;

    public Campus(String nomeCampus) {
        this.nomeCampus = nomeCampus;
        this.espacos = new ArrayList<>();
    }

    public void adicionarEspaco(EspacoFisico espaco) {
        espacos.add(espaco);
    }

    public String getNomeCampus() { return nomeCampus; }
    public List<EspacoFisico> getEspacos() { return espacos; }
}