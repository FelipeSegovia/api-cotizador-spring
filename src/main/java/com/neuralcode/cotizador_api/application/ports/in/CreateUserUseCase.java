package com.neuralcode.cotizador_api.application.ports.in;

import com.neuralcode.cotizador_api.application.dto.CreateUserCommand;
import com.neuralcode.cotizador_api.domain.models.User;

public interface CreateUserUseCase {
    User create(CreateUserCommand command);
}
