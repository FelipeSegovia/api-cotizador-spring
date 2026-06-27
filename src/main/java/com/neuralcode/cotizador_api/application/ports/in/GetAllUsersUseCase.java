package com.neuralcode.cotizador_api.application.ports.in;

import com.neuralcode.cotizador_api.domain.models.User;
import java.util.List;

public interface GetAllUsersUseCase {
    List<User> getAll();
}
