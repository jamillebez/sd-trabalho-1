package br.ufc.quixada.reserva.repository;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import br.ufc.quixada.reserva.model.EspacoFisico;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Repository;

@Repository
public class EspacoFisicoRepository {

    private static final String FILE = "data/espacos.json";

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private List<EspacoFisico> ler() {
        try {
            File file = new File(FILE);
            if (!file.exists()) return new ArrayList<>();

            return gson.fromJson(
                    new FileReader(file),
                    new TypeToken<List<EspacoFisico>>() {}.getType()
            );

        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private void salvar(List<EspacoFisico> lista) {
        try {
            File file = new File("data");
            if (!file.exists()) file.mkdirs();

            FileWriter writer = new FileWriter(FILE);
            gson.toJson(lista, writer);
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void salvar(EspacoFisico e) {
        List<EspacoFisico> lista = ler();

        int novoId = lista.stream()
                .mapToInt(EspacoFisico::getId)
                .max()
                .orElse(0) + 1;

        e.setId(novoId);

        lista.add(e);
        salvar(lista);
    }

    public List<EspacoFisico> listar() {
        System.out.println(ler());
        return ler();
    }

    public EspacoFisico buscar(int id) {
        return ler()
                .stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void atualizar(EspacoFisico novo) {
        List<EspacoFisico> lista = ler();

        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == novo.getId()) {
                lista.set(i, novo);
                break;
            }
        }

        salvar(lista);
    }

    public void deletar(int id) {
        List<EspacoFisico> lista = ler();
        lista.removeIf(e -> e.getId() == id);
        salvar(lista);
    }
}