package com.springRest.cursoapi.api.controller;

import com.springRest.cursoapi.api.mapper.VeiculoAssembler;
import com.springRest.cursoapi.api.model.input.VeiculoInput;
import com.springRest.cursoapi.api.model.response.VeiculoModel;
import com.springRest.cursoapi.domain.model.Veiculo;
import com.springRest.cursoapi.domain.repository.VeiculoRepository;
import com.springRest.cursoapi.domain.service.ApreensaoVeiculoService;
import com.springRest.cursoapi.domain.service.RegistroVeiculoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/veiculos")
@RequiredArgsConstructor
public class VeiculoController {

    private final VeiculoRepository veiculoRepository;
    private final RegistroVeiculoService registroVeiculoService;
    private final ApreensaoVeiculoService apreensaoVeiculoService;
    private final VeiculoAssembler veiculoAssembler;

    @GetMapping
    public List<VeiculoModel> listar() {
        return veiculoAssembler.toCollectionModel(veiculoRepository.findAll());
    }

    @GetMapping("/{veiculoId}")
    public ResponseEntity<VeiculoModel> buscar(@PathVariable Long veiculoId) {
        return veiculoRepository.findById(veiculoId)
                .map(veiculoAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public VeiculoModel cadastrar(@Valid @RequestBody VeiculoInput veiculoInput){
        Veiculo novoVeiculo = veiculoAssembler.toEntity(veiculoInput);
        Veiculo veiculoCadastrado = registroVeiculoService.cadastrar(novoVeiculo);

        return veiculoAssembler.toModel(veiculoCadastrado);
    }

    @PutMapping("/{veiculoId}/apreensao")
    @ResponseStatus(NO_CONTENT)
    public void apreenderVeiculo(@PathVariable Long veiculoId){
        apreensaoVeiculoService.apreender(veiculoId);
    }

    @DeleteMapping("/{veiculoId}/apreensao")
    @ResponseStatus(NO_CONTENT)
    public void removerApreensao(@PathVariable Long veiculoId){
        apreensaoVeiculoService.removerApreensao(veiculoId);
    }

}
