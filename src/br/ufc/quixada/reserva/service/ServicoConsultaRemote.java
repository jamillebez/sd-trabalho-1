package br.ufc.quixada.reserva.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServicoConsultaRemote extends Remote {
    String verificarDisponibilidade() throws RemoteException;
    String consultarDetalhes(String detalhes) throws RemoteException;
}
