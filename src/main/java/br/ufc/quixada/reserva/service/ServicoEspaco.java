package br.ufc.quixada.reserva.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServicoEspaco extends Remote {

    String listarEspacos() throws RemoteException;

    String buscarPorId(int id) throws RemoteException;
}