package com.springRest.cursoapi.domain.service;

import com.springRest.cursoapi.domain.exception.NegocioException;
import com.springRest.cursoapi.domain.model.Proprietario;
import com.springRest.cursoapi.domain.repository.ProprietarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegistroProprietarioService {

    private final ProprietarioRepository proprietarioRepository;

    @Transactional
    public Proprietario salvar(Proprietario proprietario){
        boolean emailEmUso = proprietarioRepository
                .findByEmail(proprietario.getEmail())
                .filter(prop -> !prop.equals(proprietario))
                .isPresent();
        if(emailEmUso) {
            throw new NegocioException("Email já Cadastrado");
        }
        return proprietarioRepository.save(proprietario);
    }

    @Transactional
    public void excluir(Long id){
        proprietarioRepository.deleteById(id);
    }

    public Proprietario buscar(Long proprietarioId){
        return proprietarioRepository
                .findById(proprietarioId)
                .orElseThrow(() -> new NegocioException("Proprietário não encontrado"));
    }
}
