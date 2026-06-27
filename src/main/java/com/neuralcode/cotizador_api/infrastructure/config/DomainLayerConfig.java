package com.neuralcode.cotizador_api.infrastructure.config;

import com.neuralcode.cotizador_api.domain.services.UserDomainService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainLayerConfig {

    @Bean
    public UserDomainService userDomainService() {
        return new UserDomainService();
    }
}
