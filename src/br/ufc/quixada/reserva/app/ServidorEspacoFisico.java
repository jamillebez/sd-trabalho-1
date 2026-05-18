package br.ufc.quixada.reserva.app;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

import br.ufc.quixada.reserva.service.ServicoConsultaImpl;
import br.ufc.quixada.reserva.service.ServicoReservaImpl;

public class ServidorEspacoFisico {
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099);
            Naming.rebind("rmi://localhost:1099/ServicoReserva", new ServicoReservaImpl());
            Naming.rebind("rmi://localhost:1099/ServicoConsulta", new ServicoConsultaImpl());
            System.out.println("Servidor RMI pronto e registrou ServicoReserva e ServicoConsulta na registry 1099");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
