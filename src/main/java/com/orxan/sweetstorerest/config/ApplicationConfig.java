package com.orxan.sweetstorerest.config;

import com.orxan.sweetstorerest.mappers.OrderProductMapper;
import com.orxan.sweetstorerest.model.MyUserPrincipal;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;

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

    @Bean
    public MyUserPrincipal userDetails() {
        return new MyUserPrincipal();
    }
}
