package com.neuralcode.cotizador_api.domain.services;

import com.neuralcode.cotizador_api.domain.models.User;
import com.neuralcode.cotizador_api.domain.models.UserRole;

/*
Servicio de dominio: encapsula reglas de negocio que no pertenecen a una sola entidad
o que requieren coordinar la creación de un agregado con valores por defecto.
Sin dependencias de frameworks (Spring, JPA, etc.).
 */

/*
Esto nace por la necesidad de formatear atributos de mi negocio
 */
public class UserDomainService {

    public User createUser(
            String email,
            String passwordHash,
            String name,
            String mobilePhone,
            UserRole role,
            Boolean isActive
    ) {
        UserRole resolvedRole = role != null ? role : UserRole.COMMON;
        boolean resolvedActive = isActive != null ? isActive : true;

        return new User(email, passwordHash, name, mobilePhone, resolvedRole, resolvedActive);
    }
}
