package com.neuralcode.cotizador_api.infrastructure.adapter.in.web.dto.user.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
/*
El UserRequest es el contrato para el que el exterior pueda comunicarse
con la aplicación
 */
public class UserRequest {
    private String email;
    private String password;
}
