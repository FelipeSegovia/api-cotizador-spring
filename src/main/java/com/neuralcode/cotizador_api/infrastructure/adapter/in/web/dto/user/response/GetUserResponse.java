package com.neuralcode.cotizador_api.infrastructure.adapter.in.web.dto.user.response;

import com.neuralcode.cotizador_api.domain.models.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GetUserResponse {
    private String id;
    private String name;
    private String email;
    private String mobilePhone;
    private UserRole role;
    private Boolean isActive;
}
