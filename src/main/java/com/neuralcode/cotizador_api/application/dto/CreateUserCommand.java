package com.neuralcode.cotizador_api.application.dto;

import com.neuralcode.cotizador_api.domain.models.UserRole;

/*
Este CreateUserCommand es un DTO que será utilizado en DomainLayerConfig
 */
public record CreateUserCommand(
        String name,
        String email,
        String mobilePhone,
        String password,
        UserRole role,
        Boolean isActive
) {
}
