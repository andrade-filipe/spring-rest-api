package com.springRest.cursoapi.domain.service;

import com.springRest.cursoapi.domain.exception.EntityNotFoundException;
import com.springRest.cursoapi.domain.exception.NegocioException;
import com.springRest.cursoapi.domain.model.Proprietario;
import com.springRest.cursoapi.domain.model.StatusVeiculo;
import com.springRest.cursoapi.domain.model.Veiculo;
import com.springRest.cursoapi.domain.repository.VeiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class RegistroVeiculoService {

    private final VeiculoRepository veiculoRepository;
    private final RegistroProprietarioService registroProprietarioService;

    public Veiculo buscar(Long veiculoId) {
        return veiculoRepository.findById(veiculoId)
                .orElseThrow(() -> new EntityNotFoundException("Veículo não encontrado"));
    }
    @Transactional
    public Veiculo cadastrar(Veiculo novoVeiculo) {
        if(novoVeiculo.getId() != null){
            throw new NegocioException("Veículo não deve possuir um ID");
        }

        boolean placaEmUso = veiculoRepository
                .findByPlaca(novoVeiculo.getPlaca())
                .filter(veiculo -> !veiculo.equals(novoVeiculo))
                .isPresent();

        if(placaEmUso) {
            throw new NegocioException("Já existe um veículo com a mesma placa");
        }

        Proprietario proprietario = registroProprietarioService
                .buscar(novoVeiculo.getProprietario().getId());

        novoVeiculo.setProprietario(proprietario);
        novoVeiculo.setStatus(StatusVeiculo.REGULAR);
        novoVeiculo.setDataCadastro(OffsetDateTime.now());
        return veiculoRepository.save(novoVeiculo);
    }
}
