package com.neuralcode.cotizador_api.infrastructure.adapter.in.web.controller;

import com.neuralcode.cotizador_api.application.ports.in.CreateUserUseCase;
import com.neuralcode.cotizador_api.application.ports.in.GetAllUsersUseCase;
import com.neuralcode.cotizador_api.application.ports.in.GetUserUseCase;
import com.neuralcode.cotizador_api.domain.models.User;
import com.neuralcode.cotizador_api.infrastructure.adapter.in.web.dto.user.request.CreateUserRequest;
import com.neuralcode.cotizador_api.infrastructure.adapter.in.web.dto.user.response.CreateUserResponse;
import com.neuralcode.cotizador_api.infrastructure.adapter.in.web.dto.user.response.GetUserResponse;
import com.neuralcode.cotizador_api.infrastructure.adapter.in.web.mapper.UserWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    /*
    en este controller se llaman a las interfaces de usecase para reducir el acoplamiento
    el usecase tiene un @Service en el cual spring implementa implicitamente las interfaces cuando son
    llamadas desde el controlador.

     */
    private final CreateUserUseCase createUserUseCase;
    private final GetUserUseCase getUserUseCase;
    private final GetAllUsersUseCase getAllUsersUseCase;
    private final UserWebMapper mapper;

    /*ResponseEntity
        Es una clase en Spring que representa una respuesta HTTP.
        Permite devolver cosas como:
        - Códigos HTTP
        - Body en este caso el UserResponse
        - Headers. Información adicional de la respuesta
     */

    @PostMapping
    public ResponseEntity<CreateUserResponse> create(@Valid @RequestBody CreateUserRequest request) {
        User createdUser = createUserUseCase.create(mapper.toCommand(request));
        // ResponseEntity<> es vacio porque el compilador añade implícitamente el UserResponse
        return new ResponseEntity<>(mapper.toResponse(createdUser), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetUserResponse> getById(@PathVariable String id) {
        return getUserUseCase.getById(id)
                .map(user -> ResponseEntity.ok(mapper.getUserToResponse(user)))
                .orElse(ResponseEntity.notFound().build()); // el build significa crea la respuesta final sin body
    }

    @GetMapping
    public ResponseEntity<List<CreateUserResponse>> getAll() {
        // el stream significa que convierte el List en un objeto Stream para poder procesar los elementos de forma encadenada y declarativa
        // permite recorrer la lista como un flujo de elementos, reemplaza al for
        List<CreateUserResponse> users = getAllUsersUseCase.getAll().stream()
                // el mapper es una palabra reservada para convertir User a UserRespons
                .map(mapper::toResponse)
                // el collect guarda el resultado en una list
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }
}
