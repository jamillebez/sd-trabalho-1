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
        repository.salvar(usuario);
    }

    @GetMapping
    public List<Usuario> listar() {
        return repository.listar();
    }

    @GetMapping("/{id}")
    public Usuario buscar(@PathVariable int id) {
        return repository.buscar(id);
    }

    @PutMapping
    public void atualizar(@RequestBody Usuario usuario) {
        repository.atualizar(usuario);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable int id) {
        repository.deletar(id);
    }
}