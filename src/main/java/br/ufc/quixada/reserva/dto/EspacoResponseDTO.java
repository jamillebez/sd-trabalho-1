package br.ufc.quixada.reserva.dto;

public record EspacoResponseDTO(
    Integer id,
    String nome,
    String tipo,
    Integer capacidade
) {
}
