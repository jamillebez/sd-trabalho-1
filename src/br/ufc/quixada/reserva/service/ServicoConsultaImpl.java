package br.ufc.quixada.reserva.service;

import br.ufc.quixada.reserva.model.EspacoFisico;

public class ServicoConsultaImpl implements Consulta {
    public boolean verificarDisponibilidade(EspacoFisico espaco, String data) {
        return true;
    }
}