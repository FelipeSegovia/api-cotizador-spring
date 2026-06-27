package com.neuralcode.cotizador_api.infrastructure.adapter.out.persistence.adapter;

import com.neuralcode.cotizador_api.application.ports.out.UserRepositoryPort;
import com.neuralcode.cotizador_api.domain.models.User;
import com.neuralcode.cotizador_api.infrastructure.adapter.out.persistence.entity.UserEntity;
import com.neuralcode.cotizador_api.infrastructure.adapter.out.persistence.mapper.UserPersistenceMapper;
import com.neuralcode.cotizador_api.infrastructure.adapter.out.persistence.repository.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserRepositoryPort {

    private final JpaUserRepository jpaUserRepository;
    private final UserPersistenceMapper mapper;

    @Override
    public User save(User user) {
        if (user.getId() == null) {
            user.setId(UUID.randomUUID().toString());
        }
        UserEntity entity = mapper.toEntity(user);
        UserEntity savedEntity = jpaUserRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<User> findById(String id) {
        return jpaUserRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<User> findAll() {
        return jpaUserRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}
