package com.neuralcode.cotizador_api.infrastructure.adapter.in.web.dto.user.request;

import com.neuralcode.cotizador_api.domain.models.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {
    @NotBlank(message = "Campo name no puede ir vacío")
    @Pattern(regexp = "^[^0-9]*$", message = "Campo name debe ser una cadena de texto")
    private String name;

    @NotBlank(message = "Campo email no puede ir vacío")
    @Email(message = "Campo email debe tener un formato valido")
    private String email;

    @NotBlank(message = "Campo mobilePhone no puede ir vacío")
    @Pattern(regexp = "^[0-9]*$", message = "Campo mobilePhone debe contener solo números")
    @Length(min = 9, max = 9, message = "Campo mobilePhone debe tener 9 dígitos")
    private String mobilePhone;

    @NotBlank(message = "Campo password no puede ir vacío")
    @Length(min=8, message = "Campo password debe tener al menos 8 caracteres")
    private String password;

    private UserRole role;
    private Boolean isActive;
}
