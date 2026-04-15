package br.ufc.quixada.reserva.service;

import br.ufc.quixada.reserva.model.EspacoFisico;

public interface Consulta {
    boolean verificarDisponibilidade(EspacoFisico espaco, String data);

}