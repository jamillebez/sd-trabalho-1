package br.ufc.quixada.reserva.repository;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import br.ufc.quixada.reserva.model.ReservaAgendada;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Repository;

@Repository
public class ReservaRepository {

    private static final String FILE = "data/reservas.json";

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private List<ReservaAgendada> ler() {
        try {
            File file = new File(FILE);
            if (!file.exists()) return new ArrayList<>();

            return gson.fromJson(
                    new FileReader(file),
                    new TypeToken<List<ReservaAgendada>>() {}.getType()
            );

        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private void salvar(List<ReservaAgendada> lista) {
        try {
            File file = new File("data");
            if (!file.exists()) file.mkdirs();

            FileWriter writer = new FileWriter(FILE);
            gson.toJson(lista, writer);
            writer.close();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar reservas no ficheiro JSON", e);
        }
    }

    public void salvar(ReservaAgendada r) {
        List<ReservaAgendada> lista = ler();

        int novoId = lista.stream()
                .mapToInt(ReservaAgendada::getId)
                .max()
                .orElse(0) + 1;

        r.setId(novoId);

        lista.add(r);
        salvar(lista);
    }

    public List<ReservaAgendada> listar() {
        return ler();
    }

    public ReservaAgendada buscar(String data) {
        return ler()
                .stream()
                .filter(r -> r.getData().equals(data))
                .findFirst()
                .orElse(null);
    }

    public void atualizar(ReservaAgendada nova) {
        List<ReservaAgendada> lista = ler();

        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getData().equals(nova.getData())) {
                lista.set(i, nova);
                break;
            }
        }

        salvar(lista);
    }

    public void deletar(String data) {
        List<ReservaAgendada> lista = ler();
        lista.removeIf(r -> r.getData().equals(data));
        salvar(lista);
    }
}
