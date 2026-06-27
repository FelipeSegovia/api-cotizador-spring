package com.neuralcode.cotizador_api.infrastructure.adapter.out.persistence.mapper;

import com.neuralcode.cotizador_api.domain.models.User;
import com.neuralcode.cotizador_api.infrastructure.adapter.out.persistence.entity.UserEntity;
import org.springframework.stereotype.Component;

/*
Este mapper lo que hace es transformar:
- model del dominio a entity para persistir en la base de datos
- entity de persistencia de base de datos a model del dominio
 */
@Component
public class UserPersistenceMapper {

    public UserEntity toEntity(User user) {
        if (user == null) {
            return null;
        }
        return new UserEntity(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPasswordHash(),
                user.getMobilePhone(),
                user.getRole(),
                user.isActive(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    public User toDomain(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        return new User(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getPasswordHash(),
                entity.getMobilePhone(),
                entity.getRole(),
                entity.isActive(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
