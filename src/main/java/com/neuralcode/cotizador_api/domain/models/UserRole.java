package com.neuralcode.cotizador_api.domain.models;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN("admin"),
    COMMON("common");

    private final String value;

    UserRole(String value) {
        this.value = value;
    }

}
