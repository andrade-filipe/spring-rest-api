package com.springRest.cursoapi.api.controller;

import com.springRest.cursoapi.domain.exception.NegocioException;
import com.springRest.cursoapi.domain.model.Proprietario;
import com.springRest.cursoapi.domain.repository.ProprietarioRepository;
import com.springRest.cursoapi.domain.service.RegistroProprietarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/proprietarios")
public class ProprietarioController {

    private final ProprietarioRepository proprietarioRepository;
    private final RegistroProprietarioService registroProprietarioService;

    @GetMapping
    public List<Proprietario> listar() {
        return proprietarioRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proprietario> buscar(@PathVariable Long id) {
        return proprietarioRepository
                .findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Proprietario adicionar(@RequestBody @Valid Proprietario proprietario) {
        return registroProprietarioService.salvar(proprietario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Proprietario> atualizar(@PathVariable Long id,
                                                  @RequestBody @Valid Proprietario proprietario){
        if (!proprietarioRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        proprietario.setId(id);
        return ResponseEntity.ok(registroProprietarioService.salvar(proprietario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id){
        if(!proprietarioRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        registroProprietarioService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
