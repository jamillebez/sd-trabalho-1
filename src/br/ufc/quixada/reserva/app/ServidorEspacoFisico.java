package br.ufc.quixada.reserva.app;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import br.ufc.quixada.reserva.service.ServicoConsultaImpl;
import br.ufc.quixada.reserva.service.ServicoReservaImpl;
import br.ufc.quixada.reserva.rmi.MiddlewareRMI;

public class ServidorEspacoFisico {
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099);
            Naming.rebind("rmi://localhost:1099/ServicoReserva", new ServicoReservaImpl());
            Naming.rebind("rmi://localhost:1099/ServicoConsulta", new ServicoConsultaImpl());
            Naming.rebind("rmi://localhost:1099/MiddlewareRMI", UnicastRemoteObject.exportObject(new MiddlewareRMI(false), 0));
            System.out.println("Servidor RMI pronto e registrou ServicoReserva e ServicoConsulta na registry 1099");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
