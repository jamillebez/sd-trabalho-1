package br.ufc.quixada.reserva.serviceTrabalho2;

import br.ufc.quixada.reserva.model.*;
import com.google.gson.Gson;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ServicoEspacoImpl extends UnicastRemoteObject implements ServicoEspaco {

    private final Gson gson = new Gson();

    private final List<EspacoFisico> espacos = new ArrayList<>();

    public ServicoEspacoImpl() throws RemoteException {

        espacos.add(new Laboratorio(1, "Lab 01", 40, 20));
        espacos.add(new Sala(2, "Sala 01", 20, true));
        espacos.add(new Auditorio(3, "Auditorio 01", 100, true));
    }

    @Override
    public String listarEspacos() {
        return gson.toJson(espacos);
    }

    @Override
    public String buscarPorId(int id) {

        for (EspacoFisico e : espacos) {
            if (e.getId() == id) {
                return gson.toJson(e);
            }
        }

        return "não encontrado";
    }
}