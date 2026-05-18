package br.ufc.quixada.reserva.service;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class ServicoConsultaImpl extends UnicastRemoteObject implements ServicoConsultaRemote {
    public ServicoConsultaImpl() throws RemoteException { super(); }

    @Override
    public String verificarDisponibilidade() throws RemoteException {
        return "O espaço está disponível para a data solicitada.";
    }

    @Override
    public String consultarDetalhes(String detalhes) throws RemoteException {
        return "Detalhes: " + detalhes;
    }
}
