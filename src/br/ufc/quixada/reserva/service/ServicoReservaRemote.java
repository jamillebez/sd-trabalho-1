package br.ufc.quixada.reserva.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServicoReservaRemote extends Remote {
    String solicitarReserva(String argumentos) throws RemoteException;
    String cancelarReserva(String argumentos) throws RemoteException;
}
