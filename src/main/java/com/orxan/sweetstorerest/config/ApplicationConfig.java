package com.orxan.sweetstorerest.config;

import com.orxan.sweetstorerest.mappers.OrderProductMapper;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public OrderProductMapper orderProductMapper() {
        return new OrderProductMapper();
    }
}
