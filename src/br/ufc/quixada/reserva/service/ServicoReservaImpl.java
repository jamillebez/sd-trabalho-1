package br.ufc.quixada.reserva.service;

import br.ufc.quixada.reserva.model.EspacoFisico;

public class ServicoReservaImpl implements Reserva {
    private EspacoFisico espaco;
    
    public ServicoReservaImpl(EspacoFisico espaco) {
        this.espaco = espaco;
    }

    @Override
    public boolean solicitarReserva(String data, String horario) {
        System.out.println("Reserva confirmada para " + espaco.getNome() + " em " + data + " às " + horario);
        return true;
    }

    @Override
    public void cancelarReserva() {
        System.out.println("Reserva cancelada para " + espaco.getNome());
    }
}