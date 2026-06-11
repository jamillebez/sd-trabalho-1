package br.ufc.quixada.reserva.controller; 

import br.ufc.quixada.reserva.model.Usuario;
import br.ufc.quixada.reserva.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @PostMapping
    public void criar(@RequestBody Usuario usuario) {
        System.out.println(">>> [HTTP Síncrono] Cria utilizador: " + usuario.getNome() + " (ID: " + usuario.getId() + ")");
        repository.salvar(usuario);
    }

    @GetMapping
    public List<Usuario> listar() {
        System.out.println(">>> [HTTP Síncrono] LISTAR todos os utilizadores.");
        return repository.listar();
    }

    @GetMapping("/{id}")
    public Usuario buscar(@PathVariable int id) {
        System.out.println(">>> [HTTP Síncrono] Busca ID: " + id);
        return repository.buscar(id);
    }

    @PutMapping
    public void atualizar(@RequestBody Usuario usuario) {
        System.out.println(">>> [HTTP Síncrono] Atualiza: " + usuario.getNome() + " (ID: " + usuario.getId() + ")");
        repository.atualizar(usuario);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable int id) {
        System.out.println(">>> [HTTP Síncrono] Remove ID: " + id);
        repository.deletar(id);
    }
}