package com.neuralcode.cotizador_api.infrastructure.adapter.out.persistence.entity;

import com.neuralcode.cotizador_api.domain.models.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy =  GenerationType.UUID)
    private String id;
    private String name;
    private String email;
    @Column(name = "password_hash", nullable = false, length = 32)
    private String passwordHash;
    private String mobilePhone;
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UserRole role;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
