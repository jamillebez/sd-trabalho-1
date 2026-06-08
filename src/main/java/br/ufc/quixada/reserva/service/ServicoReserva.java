package br.ufc.quixada.reserva.service;

import br.ufc.quixada.reserva.rmi.RemoteObjectRef;

import java.rmi.Remote;
import java.net.InetAddress;
import java.rmi.RemoteException;

public interface ServicoReserva extends Remote {

    byte[] doOperation(
            RemoteObjectRef o,
            int methodId,
            byte[] arguments)
            throws RemoteException;

    byte[] getRequest()
            throws RemoteException;

    void sendReply(
            byte[] reply,
            InetAddress clientHost,
            int clientPort)
            throws RemoteException;
}