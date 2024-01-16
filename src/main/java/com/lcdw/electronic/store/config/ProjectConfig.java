package com.lcdw.electronic.store.config;


import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class ProjectConfig

{

    @Bean
    public ModelMapper mapper(){
        return new ModelMapper();
    }



}
