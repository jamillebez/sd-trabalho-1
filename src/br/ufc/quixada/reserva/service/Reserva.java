package br.ufc.quixada.reserva.service;

public interface Reserva {
    boolean solicitarReserva(String data, String horario);
    void cancelarReserva();
}