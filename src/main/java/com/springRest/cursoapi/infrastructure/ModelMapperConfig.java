package com.springRest.cursoapi.infrastructure;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {

        /*
        * var modelMapper = new ModelMapper();
        *
        * modelMapper.createTypeMap(Veiculo.class, VeiculoModel.class)
        *           .addMappings(mapper -> mapper.map(Veiculo::getPlaca,
        *                                             VeiculoModel::setNumeroPlaca));
        *
        * */

        return new ModelMapper();
    }
}
