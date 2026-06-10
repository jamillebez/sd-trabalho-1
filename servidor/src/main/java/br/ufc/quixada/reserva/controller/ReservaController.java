package br.ufc.quixada.reserva.controller;

import br.ufc.quixada.reserva.config.RabbitMQConfig;
import br.ufc.quixada.reserva.dto.ReservaResponseDTO;
import br.ufc.quixada.reserva.dto.ReservaUpsertDTO;
import br.ufc.quixada.reserva.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping
    public ResponseEntity<Map<String, String>> criar(@RequestBody ReservaUpsertDTO reserva) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.RESERVAS_EXCHANGE,
                RabbitMQConfig.RESERVAS_ROUTING_KEY,
                reserva
        );

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(Map.of("mensagem", "Reserva recebida e enviada para a fila."));
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
