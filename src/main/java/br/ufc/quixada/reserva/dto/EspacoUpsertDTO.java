package br.ufc.quixada.reserva.dto;

public record EspacoUpsertDTO(
    String nome,
    String tipo,
    Integer capacidade
) {
}
