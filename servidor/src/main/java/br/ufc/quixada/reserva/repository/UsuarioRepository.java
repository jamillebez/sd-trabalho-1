package br.ufc.quixada.reserva.repository;

import br.ufc.quixada.reserva.model.Usuario;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioRepository {

    private static final String FILE = "data/usuarios.json";

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private List<Usuario> lerArquivo() {
        try {
            File file = new File(FILE);
            if (!file.exists()) return new ArrayList<>();

            return gson.fromJson(
                    new FileReader(file),
                    new TypeToken<List<Usuario>>() {}.getType()
            );

        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private void salvarArquivo(List<Usuario> lista) {
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

    public void salvar(Usuario u) {
        List<Usuario> lista = lerArquivo();
        lista.add(u);
        salvarArquivo(lista);
    }

    public List<Usuario> listar() {
        return lerArquivo();
    }

    public Usuario buscar(int id) {
        return lerArquivo()
                .stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void atualizar(Usuario novo) {
        List<Usuario> lista = lerArquivo();

        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == novo.getId()) {
                lista.set(i, novo);
                break;
            }
        }

        salvarArquivo(lista);
    }

    public void deletar(int id) {
        List<Usuario> lista = lerArquivo();
        lista.removeIf(u -> u.getId() == id);
        salvarArquivo(lista);
    }
}