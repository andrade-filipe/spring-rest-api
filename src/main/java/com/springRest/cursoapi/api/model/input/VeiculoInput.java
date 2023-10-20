package com.springRest.cursoapi.api.model.input;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VeiculoInput {

    @NotNull
    @Valid
    private ProprietarioIdInput proprietario;

    @NotBlank
    @Size(max = 30)
    private String marca;

    @NotBlank
    @Size(max = 40)
    private String modelo;

    @NotBlank
    @Pattern(regexp = "[A-Z]{3}[0-9][0-9A-Z][0-9]{2}")
    private String placa;

}
