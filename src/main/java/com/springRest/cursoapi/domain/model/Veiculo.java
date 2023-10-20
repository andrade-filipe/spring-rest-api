package com.springRest.cursoapi.domain.model;

import com.springRest.cursoapi.domain.exception.NegocioException;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.springRest.cursoapi.domain.model.StatusVeiculo.*;
import static jakarta.persistence.CascadeType.*;
import static java.time.OffsetDateTime.*;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    //@Valid
    //@ConvertGroup(to = ProprietarioId.class)
    //@NotNull
    @ManyToOne
    // @JoinColumn(name = "proprietario_id")
    private Proprietario proprietario;

    //@NotBlank
    private String marca;

    //@NotBlank
    private String modelo;

    //@NotBlank
    //@Pattern(regexp = "[A-Z]{3}[0-9][0-9A-Z][0-9]{2}")
    private String placa;

    //@JsonProperty(access = READ_ONLY)
    @Enumerated(EnumType.STRING)
    private StatusVeiculo status;

    //@JsonProperty(access = READ_ONLY)
    private OffsetDateTime dataCadastro;

    //@JsonProperty(access = READ_ONLY)
    private OffsetDateTime dataApreensao;

    /*
     * Configuração inversa do relacionamento
     * Associação Bidirecional
     */
    @OneToMany(mappedBy = "veiculo", cascade = ALL)
    private List<Autuacao> autuacoes = new ArrayList<>();

    public Autuacao adicionarAutuacao(Autuacao autuacao) {
        autuacao.setDataOcorrencia(now());
        autuacao.setVeiculo(this);
        getAutuacoes().add(autuacao);
        return autuacao;
    }

    public void apreender(){
        if(isApreendido()){
            throw new NegocioException("Veiculo já se encontra apreendido");
        }

        setStatus(APREENDIDO);
        setDataApreensao(now());
    }

    public void removerApreensao(){
        if(isNotApreendido()){
            throw new NegocioException("Veiculo não está apreendido");
        }

        setStatus(REGULAR);
        setDataApreensao(null);
    }

    public boolean isApreendido(){
        return APREENDIDO.equals(getStatus());
    }

    public boolean isNotApreendido(){
        return !isApreendido();
    }
}
