package com.neuralcode.cotizador_api.infrastructure.adapter.in.web.controller;

import com.neuralcode.cotizador_api.application.ports.in.CreateUserUseCase;
import com.neuralcode.cotizador_api.application.ports.in.GetAllUsersUseCase;
import com.neuralcode.cotizador_api.application.ports.in.GetUserUseCase;
import com.neuralcode.cotizador_api.application.dto.CreateUserCommand;
import com.neuralcode.cotizador_api.domain.models.User;
import com.neuralcode.cotizador_api.domain.models.UserRole;
import com.neuralcode.cotizador_api.infrastructure.adapter.in.web.handler.GlobalExceptionHandler;
import com.neuralcode.cotizador_api.infrastructure.adapter.in.web.dto.user.request.CreateUserRequest;
import com.neuralcode.cotizador_api.infrastructure.adapter.in.web.mapper.UserWebMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@org.springframework.context.annotation.Import(GlobalExceptionHandler.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CreateUserUseCase createUserUseCase;

    @MockitoBean
    private GetUserUseCase getUserUseCase;

    @MockitoBean
    private GetAllUsersUseCase getAllUsersUseCase;

    @MockitoBean
    private UserWebMapper mapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateUser() throws Exception {
        CreateUserRequest request = new CreateUserRequest(
                "Test Name",
                "test@example.com",
                "123456789",
                "password123",
                UserRole.COMMON,
                true
        );
        CreateUserCommand command = new CreateUserCommand(
                "Test Name",
                "test@example.com",
                "123456789",
                "password123",
                UserRole.COMMON,
                true
        );
        User user = new User("1", "Test Name", "test@example.com", "hash", "123456789", UserRole.COMMON, true, LocalDateTime.now(), LocalDateTime.now());
        var response = new com.neuralcode.cotizador_api.infrastructure.adapter.in.web.dto.user.response.CreateUserResponse(
                user.getId(),
                user.getEmail(),
                user.getRole(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );

        when(mapper.toCommand(any(CreateUserRequest.class))).thenReturn(command);
        when(createUserUseCase.create(any(CreateUserCommand.class))).thenReturn(user);
        when(mapper.toResponse(any())).thenReturn(response);

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    void shouldReturnBadRequestWhenNameIsMissing() throws Exception {
        String request = """
                {
                  "email": "test@example.com",
                  "mobilePhone": "123456789",
                  "password": "password123",
                  "role": "COMMON",
                  "isActive": true
                }
                """;

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Campo name no puede ir vacío"));
    }

    @Test
    void shouldReturnBadRequestWhenNameIsNotAString() throws Exception {
        String request = """
                {
                  "name": {"value": "Test Name"},
                  "email": "test@example.com",
                  "mobilePhone": "123456789",
                  "password": "password123",
                  "role": "COMMON",
                  "isActive": true
                }
                """;

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Campo name debe ser una cadena de texto"));
    }

    @Test
    void shouldReturnBadRequestWhenEmailFormatIsInvalid() throws Exception {
        String request = """
                {
                  "name": "Test Name",
                  "email": "correo-invalido",
                  "mobilePhone": "123456789",
                  "password": "password123",
                  "role": "COMMON",
                  "isActive": true
                }
                """;

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Campo email debe tener un formato valido"));
    }

    @Test
    void shouldGetUserById() throws Exception {
        User user = new User("1", "Test Name", "test@example.com", "hash", "123456789", UserRole.COMMON, true, LocalDateTime.now(), LocalDateTime.now());

        when(getUserUseCase.getById("1")).thenReturn(Optional.of(user));
        when(mapper.getUserToResponse(any())).thenCallRealMethod();

        mockMvc.perform(get("/api/v1/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    void shouldReturnNotFoundWhenUserDoesNotExist() throws Exception {
        when(getUserUseCase.getById("99")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/users/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldGetAllUsers() throws Exception {
        User user1 = new User("1", "User One", "user1@example.com", "hash", "111111111", UserRole.COMMON, true, LocalDateTime.now(), LocalDateTime.now());
        User user2 = new User("2", "User Two", "user2@example.com", "hash", "222222222", UserRole.COMMON, true, LocalDateTime.now(), LocalDateTime.now());

        when(getAllUsersUseCase.getAll()).thenReturn(List.of(user1, user2));
        when(mapper.toResponse(any())).thenCallRealMethod();

        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].email").value("user1@example.com"))
                .andExpect(jsonPath("$[1].email").value("user2@example.com"));
    }
}
