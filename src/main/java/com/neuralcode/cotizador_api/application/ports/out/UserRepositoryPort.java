package com.neuralcode.cotizador_api.application.ports.out;

import com.neuralcode.cotizador_api.domain.models.User;
import java.util.List;
import java.util.Optional;

/*
aquí se muestran los métodos externos que necesita la capa de aplicación
desde la capa de infraestructura.

Esto no rompe la teoría ya que no se implementan los métodos de la capa de infra
sino que más bien se genera un contrato entre la capa de aplicación y la capa de infra
a modo que se cumpla el objetivo de la capa de aplicación el cual es representar
el orden de como hacer las cosas

*/

public interface UserRepositoryPort {
    User save(User user);
    Optional<User> findById(String id);
    List<User> findAll();
}
