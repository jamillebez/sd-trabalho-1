package br.ufc.quixada.reserva.dto;

import br.ufc.quixada.reserva.model.EspacoFisico;
import br.ufc.quixada.reserva.model.Usuario;

public record ReservaResponseDTO(
    Integer id,
    String data,
    Usuario usuario,
    EspacoResponseDTO espacoFisico
) {
}
