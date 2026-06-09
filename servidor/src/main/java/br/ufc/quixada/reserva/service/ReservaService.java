package br.ufc.quixada.reserva.service;

import br.ufc.quixada.reserva.dto.EspacoResponseDTO;
import br.ufc.quixada.reserva.dto.ReservaResponseDTO;
import br.ufc.quixada.reserva.dto.ReservaUpsertDTO;
import br.ufc.quixada.reserva.model.EspacoFisico;
import br.ufc.quixada.reserva.model.ReservaAgendada;
import br.ufc.quixada.reserva.model.Usuario;
import br.ufc.quixada.reserva.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository repository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EspacoService espacoService;

    public void criar(ReservaUpsertDTO dto) {
        ReservaAgendada reserva = fillReserva(dto);

        repository.salvar(reserva);
    }

    public List<ReservaResponseDTO> listar() {
        return repository
                .listar()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public ReservaResponseDTO buscar(String data) {
        ReservaAgendada reserva = repository.buscar(data);

        if (reserva == null) {
            return null;
        }
        return toResponseDTO(reserva);
    }

    public void remover(String data) {
        repository.deletar(data);
    }

    private ReservaAgendada fillReserva(ReservaUpsertDTO dto) {

        Usuario usuario =
                usuarioService.buscar(dto.usuarioId());

        if (usuario == null) {
            throw new RuntimeException(
                    "Usuário não encontrado");
        }

        EspacoFisico espaco =
                espacoService.buscarEntidade(dto.espacoId());

        if (espaco == null) {
            throw new RuntimeException(
                    "Espaço físico não encontrado");
        }

        return new ReservaAgendada(
                dto.data(),
                usuario,
                espaco,
                null
        );
    }

    private ReservaResponseDTO toResponseDTO(
            ReservaAgendada reserva) {

        return new ReservaResponseDTO(
                reserva.getId(),
                reserva.getData(),
                reserva.getUsuario(),
                new EspacoResponseDTO(
                        reserva.getEspaco().getId(),
                        reserva.getEspaco().getNome(),
                        reserva.getEspaco().getTipo(),
                        reserva.getEspaco().getCapacidade()
                )
        );
    }
}