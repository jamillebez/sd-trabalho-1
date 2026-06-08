package br.ufc.quixada.reserva.service;

import br.ufc.quixada.reserva.model.*;

import br.ufc.quixada.reserva.rmi.Mensagem;
import br.ufc.quixada.reserva.rmi.RemoteObjectRef;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.Gson;

import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class ServicoReservaImpl extends UnicastRemoteObject implements ServicoReserva {

    private final Gson gson = new Gson();

    private final ReservaRepository repository = new ReservaRepository();

    private byte[] currentRequest;

    private byte[] currentReply;

    private int requestCounter = 1;

    public ServicoReservaImpl() throws RemoteException {
        super();
    }

    @Override
    public byte[] doOperation(RemoteObjectRef o, int methodId, byte[] arguments)
            throws RemoteException {

        Mensagem request = new Mensagem(
                0,
                requestCounter++,
                o.getObjectReference(),
                String.valueOf(methodId),
                new String(arguments, StandardCharsets.UTF_8)
        );

        currentRequest = gson.toJson(request).getBytes(StandardCharsets.UTF_8);

        byte[] requestBytes = getRequest();

        Mensagem requestMessage = gson.fromJson(
                new String(requestBytes, StandardCharsets.UTF_8),
                Mensagem.class
        );

        processRequest(requestMessage);

        sendReply(currentReply, null, 0);

        return currentReply;
    }

    @Override
    public byte[] getRequest() throws RemoteException {
        return currentRequest;
    }

    @Override
    public void sendReply(byte[] reply, InetAddress clientHost, int clientPort)
            throws RemoteException {

        currentReply = reply;
    }

    private EspacoFisico getEspacoFisicoFromJson(JsonObject espacoJson, String tipo) {
        switch (tipo) {
            case "laboratorio":
                return gson.fromJson(espacoJson, Laboratorio.class);
            case "sala":
                return gson.fromJson(espacoJson, Sala.class);
            case "auditorio":
                return gson.fromJson(espacoJson, Auditorio.class);
        }
        return null;
    }

    private void processRequest(Mensagem request) {

        String resultado;

        switch (request.getMethodId()) {

            case "1":
                JsonObject json = JsonParser.parseString(request.getArguments()).getAsJsonObject();

                String data = json.get("data").getAsString();
                Usuario usuario = gson.fromJson(json.get("usuario"), Usuario.class);

                JsonObject espacoJson = json.getAsJsonObject("espaco");
                String tipo = espacoJson.get("tipo").getAsString();
                EspacoFisico espaco = getEspacoFisicoFromJson(espacoJson, tipo);

                ReservaAgendada reserva = new ReservaAgendada(data, usuario, espaco);

                repository.adicionar(reserva);

                resultado = "Reserva cadastrada com sucesso.";

                break;

            case "2":
                List<ReservaAgendada> reservas = repository.listar();
                resultado = gson.toJson(reservas);

                break;

            case "3":
                ReservaAgendada encontrada =
                        repository.buscarPorData(
                                request.getArguments());
                resultado =
                        encontrada == null
                                ? "Reserva não encontrada."
                                : gson.toJson(encontrada);

                break;
            case "4":
                boolean removida =
                        repository.removerPorData(
                                request.getArguments());

                resultado =
                        removida
                                ? "Reserva removida com sucesso."
                                : "Reserva não encontrada.";

                break;

            default:

                resultado = "Operação inválida.";
        }

        Mensagem reply = new Mensagem(
                1,
                request.getRequestId(),
                request.getObjectReference(),
                request.getMethodId(),
                resultado
        );

        currentReply = gson.toJson(reply).getBytes(StandardCharsets.UTF_8);
    }
}