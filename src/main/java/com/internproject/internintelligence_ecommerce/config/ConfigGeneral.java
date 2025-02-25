package com.internproject.internintelligence_ecommerce.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigGeneral {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
