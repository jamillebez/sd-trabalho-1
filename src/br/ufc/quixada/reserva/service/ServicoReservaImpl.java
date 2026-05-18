package br.ufc.quixada.reserva.service;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class ServicoReservaImpl extends UnicastRemoteObject implements ServicoReservaRemote {
    public ServicoReservaImpl() throws RemoteException { super(); }

    @Override
    public String solicitarReserva(String argumentos) throws RemoteException {
        return "Sucesso: Reserva confirmada!";
    }

    @Override
    public String cancelarReserva(String argumentos) throws RemoteException {
        return "Sucesso: Reserva cancelada!";
    }
}
