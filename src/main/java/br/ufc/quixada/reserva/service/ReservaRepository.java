package br.ufc.quixada.reserva.service;

import br.ufc.quixada.reserva.model.*;

import com.google.gson.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class ReservaRepository {

    private static final String FILE_NAME =
            "data/reservas.json";

    private final Gson gson =
            new GsonBuilder()
                    .setPrettyPrinting()
                    .create();

    public List<ReservaAgendada> listar() {

        try {

            JsonArray array = JsonParser.parseReader(new FileReader(FILE_NAME)).getAsJsonArray();

            List<ReservaAgendada> reservas = new ArrayList<>();

            for (JsonElement element : array) {

                JsonObject obj = element.getAsJsonObject();

                String data = obj.get("data").getAsString();

                Usuario usuario = gson.fromJson(obj.get("usuario"), Usuario.class);

                JsonObject espacoJson = obj.getAsJsonObject("espaco");

                String tipo = espacoJson.get("tipo").getAsString();

                EspacoFisico espaco;

                switch (tipo) {

                    case "laboratorio":
                        espaco = gson.fromJson(espacoJson, Laboratorio.class);
                        break;

                    case "sala":
                        espaco = gson.fromJson(espacoJson, Sala.class);
                        break;

                    case "auditorio":
                        espaco = gson.fromJson(espacoJson, Auditorio.class);
                        break;

                    default:
                        throw new RuntimeException("Tipo desconhecido: " + tipo);
                }

                reservas.add(new ReservaAgendada(data, usuario, espaco));
            }

            return reservas;

        } catch (Exception e) {

            e.printStackTrace();

            return new ArrayList<>();
        }
    }

    public void salvar(
            List<ReservaAgendada> reservas) {

        try {

            FileWriter writer =
                    new FileWriter(FILE_NAME);

            gson.toJson(reservas, writer);

            writer.flush();
            writer.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public void adicionar(
            ReservaAgendada reserva) {

        List<ReservaAgendada> reservas =
                listar();

        reservas.add(reserva);

        salvar(reservas);
    }
}