package br.ufc.quixada.reserva.dto;

public record ReservaUpsertDTO(
    String data,
    Integer usuarioId,
    Integer espacoId
) { }
