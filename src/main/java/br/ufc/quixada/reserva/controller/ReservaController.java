package br.ufc.quixada.reserva.controller;

import br.ufc.quixada.reserva.dto.ReservaResponseDTO;
import br.ufc.quixada.reserva.dto.ReservaUpsertDTO;
import br.ufc.quixada.reserva.model.ReservaAgendada;
import br.ufc.quixada.reserva.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @PostMapping
    public void criar(@RequestBody ReservaUpsertDTO reserva) {
        reservaService.criar(reserva);
    }

    @GetMapping
    public List<ReservaResponseDTO> listar() {
        return reservaService.listar();
    }

    @GetMapping("/{data}")
    public ReservaResponseDTO buscar(@PathVariable String data) {
        return reservaService.buscar(data);
    }

    @DeleteMapping("/{data}")
    public void remover(@PathVariable String data) {
        reservaService.remover(data);
    }
}