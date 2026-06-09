package br.ufc.quixada.reserva.service;

import br.ufc.quixada.reserva.model.EspacoFisico;
import br.ufc.quixada.reserva.model.Usuario;
import br.ufc.quixada.reserva.repository.EspacoFisicoRepository;
import br.ufc.quixada.reserva.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    public void criar(Usuario usuario) {
        repository.salvar(usuario);
    }

    public List<Usuario> listar() {
        return repository.listar();
    }

    public Usuario buscar(int id) {
        return repository.buscar(id);
    }

    public void remover(int id) {
        repository.deletar(id);
    }
}