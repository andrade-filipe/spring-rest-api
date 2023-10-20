package com.springRest.cursoapi.api.controller;

import com.springRest.cursoapi.api.mapper.AutuacaoAssembler;
import com.springRest.cursoapi.api.model.input.AutuacaoInput;
import com.springRest.cursoapi.api.model.response.AutuacaoModel;
import com.springRest.cursoapi.domain.model.Autuacao;
import com.springRest.cursoapi.domain.model.Veiculo;
import com.springRest.cursoapi.domain.service.RegistroAutuacaoService;
import com.springRest.cursoapi.domain.service.RegistroVeiculoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/veiculos/{veiculoId}/autuacoes")
public class AutuacaoController {

    private final RegistroAutuacaoService registroAutuacaoService;
    private final RegistroVeiculoService registroVeiculoService;
    private final AutuacaoAssembler autuacaoAssembler;

    @PostMapping
    @ResponseStatus(CREATED)
    public AutuacaoModel registrar(@PathVariable Long veiculoId,
                                    @Valid @RequestBody AutuacaoInput autuacaoInput){
        Autuacao novaAutuacao = autuacaoAssembler.toEntity(autuacaoInput);
        Autuacao autuacaoRegistrada = registroAutuacaoService.registrar(veiculoId, novaAutuacao);

        return autuacaoAssembler.toModel(autuacaoRegistrada);
    }

    @GetMapping
    public List<AutuacaoModel> listar(@PathVariable Long veiculoId){
        Veiculo veiculo = registroVeiculoService.buscar(veiculoId);
        return autuacaoAssembler.toCollectionModel(veiculo.getAutuacoes());
    }
}
