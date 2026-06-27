package com.neuralcode.cotizador_api.infrastructure.adapter.in.web.dto.user.response;

import com.neuralcode.cotizador_api.domain.models.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
/*
El UserResponse es el contrato que define la aplicación como resultado
de lo obtenido para el exterior.

 */
public class CreateUserResponse {
    private String id;
    private String email;
    private UserRole role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
