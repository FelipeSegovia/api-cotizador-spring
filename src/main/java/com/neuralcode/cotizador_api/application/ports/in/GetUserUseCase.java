package com.neuralcode.cotizador_api.application.ports.in;

import com.neuralcode.cotizador_api.domain.models.User;
import java.util.Optional;

public interface GetUserUseCase {
    Optional<User> getById(String id);
}
