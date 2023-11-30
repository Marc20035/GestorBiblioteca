package io.bootify.gestor_biblioteca.repos;

import io.bootify.gestor_biblioteca.domain.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AdministradorRepository extends JpaRepository<Administrador, Long> {
}
