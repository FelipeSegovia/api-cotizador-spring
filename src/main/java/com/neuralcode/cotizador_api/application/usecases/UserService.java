package com.neuralcode.cotizador_api.application.usecases;

import com.neuralcode.cotizador_api.application.dto.CreateUserCommand;
import com.neuralcode.cotizador_api.application.ports.in.CreateUserUseCase;
import com.neuralcode.cotizador_api.application.ports.in.GetAllUsersUseCase;
import com.neuralcode.cotizador_api.application.ports.in.GetUserUseCase;
import com.neuralcode.cotizador_api.application.ports.out.UserRepositoryPort;
import com.neuralcode.cotizador_api.domain.models.User;
import com.neuralcode.cotizador_api.domain.services.UserDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor // esto significa que se debe crear un constructor con todos los parámetros
/*
Porque defino tantas interfaces como use cases?
R:
- Cumple con el principio de SOLID de responsabilidad única.
- Cumple con el principio de segregación de intereses. es decir, si un controlador solo necesita crear usuarios, puede depender únicamente de una interfaz
- Reduce la dependencia entre los componentes (acoplamiento)
- Facilita la prueba
- Hace más visible la intención del sistema

** se puede tener en un usecase pero solo para proyectos pequeños **
 */

/*
Hablemos del Service:
aqui es donde se implementan los contratos definidos en las interfaces del usecase.
 */
public class UserService implements CreateUserUseCase, GetUserUseCase, GetAllUsersUseCase {

    // el userRepositoryPort define la interfaz de salida, es decir, es el contrato que se define para la capa de infrastructure
    private final UserRepositoryPort userRepositoryPort;
    private final UserDomainService userDomainService;

    @Override
    public User create(CreateUserCommand command) {
        User domainUser = userDomainService.createUser(
                command.email(),
                command.password(),
                command.name(),
                command.mobilePhone(),
                command.role(),
                command.isActive()
        );
        return userRepositoryPort.save(domainUser);
    }

    @Override
    public Optional<User> getById(String id) {
        return userRepositoryPort.findById(id);
    }

    @Override
    public List<User> getAll() {
        return userRepositoryPort.findAll();
    }
}
