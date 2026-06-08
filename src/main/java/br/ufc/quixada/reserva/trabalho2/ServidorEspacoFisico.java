package br.ufc.quixada.reserva.trabalho2;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

import br.ufc.quixada.reserva.service.ServicoReservaImpl;
import br.ufc.quixada.reserva.service.ServicoReserva;
import br.ufc.quixada.reserva.service.ServicoEspaco;
import br.ufc.quixada.reserva.service.ServicoEspacoImpl;

public class ServidorEspacoFisico {
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099);

            ServicoReserva servico = new ServicoReservaImpl();

            ServicoEspaco servicoEspaco = new ServicoEspacoImpl();

            Naming.rebind("ServicoEspaco", servicoEspaco);
            Naming.rebind("ServicoReserva", servico);

            System.out.println(
                    "Servidor iniciado e pronto para receber solicitações.");

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}