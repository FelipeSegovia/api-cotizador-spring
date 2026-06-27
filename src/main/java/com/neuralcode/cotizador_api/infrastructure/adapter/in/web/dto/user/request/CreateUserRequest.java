package com.neuralcode.cotizador_api.infrastructure.adapter.in.web.dto.user.request;

import com.neuralcode.cotizador_api.domain.models.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {
    private String name;
    private String email;
    private String mobilePhone;
    private String password;
    private UserRole role;
    private Boolean isActive;
}
