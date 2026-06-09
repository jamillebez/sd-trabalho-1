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
        espacoService.criar(espaco);
    }

    @GetMapping
    public List<EspacoResponseDTO> listar() {
        return espacoService.listar();
    }

    @GetMapping("/{id}")
    public EspacoResponseDTO buscar(@PathVariable int id) {
        return espacoService.buscar(id);
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable int id) {
        espacoService.remover(id);
    }
}