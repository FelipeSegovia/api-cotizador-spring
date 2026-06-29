package com.neuralcode.cotizador_api.infrastructure.adapter.out.persistence.repository;

import com.neuralcode.cotizador_api.infrastructure.adapter.out.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUserRepository extends JpaRepository<UserEntity, String> {
    boolean existsByEmail(String email);
}
