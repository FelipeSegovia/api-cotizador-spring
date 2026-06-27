package com.neuralcode.cotizador_api.domain.models;

import com.neuralcode.cotizador_api.domain.exceptions.UserDomainException;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/*
A partir de la capa de aplicación se puede utilizar loombok
 */

@Getter
@Setter
@AllArgsConstructor // agregar un constructor con todos los parametros
@NoArgsConstructor // agregar un constructor vacio
public class User {
    private String id;
    private String name;
    private String email;
    private String passwordHash;
    private String mobilePhone;
    private UserRole role;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public User(String email, String passwordHash) {
        validateEmail(email);
        validPassword(passwordHash);

        this.email = email;
        this.passwordHash = passwordHash;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public User(String email, String passwordHash, String name, String mobilePhone, UserRole role, boolean isActive) {
        validateEmail(email);
        validPassword(passwordHash);

        this.email = email;
        this.passwordHash = passwordHash;
        this.name = name;
        this.role = role;
        this.mobilePhone = mobilePhone;
        this.isActive = isActive;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    private void validateEmail(String email) {
        if(email == null || email.isBlank()){
            throw new UserDomainException("Correo electrónico no puede ser vacío");
        }

        if(!email.contains("@")){
            throw new UserDomainException("Correo electrónico no tiene un formato válido");
        }
    }

    private void validPassword(String password){
        if(password == null || password.isBlank()){
            throw new UserDomainException("Contraseña no puede ser vacía");
        }
    }
}
