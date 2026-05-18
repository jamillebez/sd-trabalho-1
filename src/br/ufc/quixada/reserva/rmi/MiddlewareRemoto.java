package br.ufc.quixada.reserva.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MiddlewareRemoto extends Remote {
    byte[] doOperation(String objectRef, String methodId, byte[] arguments, String ipServidor, int portaServidor) throws RemoteException;
}