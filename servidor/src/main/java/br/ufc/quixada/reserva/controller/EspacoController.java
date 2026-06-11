package br.ufc.quixada.reserva.controller;

import br.ufc.quixada.reserva.dto.EspacoResponseDTO;
import br.ufc.quixada.reserva.dto.EspacoUpsertDTO;
import br.ufc.quixada.reserva.service.EspacoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/espacos")
public class EspacoController {

    @Autowired
    private EspacoService espacoService;

    @PostMapping
    public void criar(@RequestBody EspacoUpsertDTO espaco) {
        System.out.println(">>> [HTTP Síncrono] Cria um novo espaço.");
        espacoService.criar(espaco);
    }

    @GetMapping
    public List<EspacoResponseDTO> listar() {
        System.out.println(">>> [HTTP Síncrono] Lista todos os espaços.");
        return espacoService.listar();
    }

    @GetMapping("/{id}")
    public EspacoResponseDTO buscar(@PathVariable int id) {
        System.out.println(">>> [HTTP Síncrono] Busca espaço com ID: " + id);
        return espacoService.buscar(id);
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable int id) {
        System.out.println(">>> [HTTP Síncrono] Remove espaço com ID: " + id);
        espacoService.remover(id);
    }
}