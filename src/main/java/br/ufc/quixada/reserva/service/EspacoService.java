package br.ufc.quixada.reserva.service;

import br.ufc.quixada.reserva.dto.EspacoResponseDTO;
import br.ufc.quixada.reserva.dto.EspacoUpsertDTO;
import br.ufc.quixada.reserva.model.EspacoFisico;
import br.ufc.quixada.reserva.model.Laboratorio;
import br.ufc.quixada.reserva.model.Sala;
import br.ufc.quixada.reserva.repository.EspacoFisicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EspacoService {

    @Autowired
    private EspacoFisicoRepository repository;

    public void criar(EspacoUpsertDTO dto) {
        EspacoFisico espaco = fillEspaco(dto);

        System.out.println(espaco);

        repository.salvar(espaco);
    }

    public List<EspacoResponseDTO> listar() {

        return repository
                .listar()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public EspacoResponseDTO buscar(Integer id) {
        EspacoFisico espaco = repository.buscar(id);

        if (espaco == null) {
            return null;
        }

        return toResponseDTO(espaco);
    }

    public EspacoFisico buscarEntidade(Integer id) {
        return repository.buscar(id);
    }

    public void remover(Integer id) {
        repository.deletar(id);
    }

    private EspacoFisico fillEspaco(EspacoUpsertDTO dto) {
        return switch (dto.tipo()) {
            case "Sala" ->
                    new Sala(0, dto.nome(), dto.capacidade(), true);

            case "Laboratorio" ->
                    new Laboratorio(0, dto.nome(), dto.capacidade(), dto.capacidade());

            default ->
                    throw new RuntimeException("Tipo inválido");
        };
    }

    private EspacoResponseDTO toResponseDTO(
            EspacoFisico espaco
    ) {
        return new EspacoResponseDTO(
                espaco.getId(),
                espaco.getNome(),
                espaco.getTipo(),
                espaco.getCapacidade()
        );
    }
}