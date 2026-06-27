package com.neuralcode.cotizador_api.infrastructure.adapter.in.web.mapper;

import com.neuralcode.cotizador_api.application.dto.CreateUserCommand;
import com.neuralcode.cotizador_api.domain.models.User;
import com.neuralcode.cotizador_api.infrastructure.adapter.in.web.dto.user.request.CreateUserRequest;
import com.neuralcode.cotizador_api.infrastructure.adapter.in.web.dto.user.request.UserRequest;
import com.neuralcode.cotizador_api.infrastructure.adapter.in.web.dto.user.response.CreateUserResponse;
import com.neuralcode.cotizador_api.infrastructure.adapter.in.web.dto.user.response.GetUserResponse;
import org.springframework.stereotype.Component;

@Component
/*
Los mappers en la capa de infraestructura son los encargados de transformar los datos para las
distintas capas.

En este caso el UserWebMapper es el encargado de transformar
los datos de la capa de infraestructura (request) a la capa de dominio (models)
o
los datos de la capa de dominio (models) a la capa de infraestructure (response)
 */
public class UserWebMapper {

    public User toDomain(UserRequest request) {
        if (request == null) {
            return null;
        }
        return new User(request.getEmail(), request.getPassword());
    }

    public CreateUserCommand toCommand(CreateUserRequest request) {
        if (request == null) {
            return null;
        }
        return new CreateUserCommand(
                request.getName(),
                request.getEmail(),
                request.getMobilePhone(),
                request.getPassword(),
                request.getRole(),
                request.getIsActive()
        );
    }

    public CreateUserResponse toResponse(User user) {
        if (user == null) {
            return null;
        }
        return new CreateUserResponse(
                user.getId(),
                user.getEmail(),
                user.getRole(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    public GetUserResponse getUserToResponse(User user){
        if(user == null){
            return null;
        }

        return new GetUserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getMobilePhone(),
                user.getRole(),
                user.isActive()
        );
    }
}
