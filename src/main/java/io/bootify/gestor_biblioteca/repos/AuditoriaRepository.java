package io.bootify.gestor_biblioteca.repos;

import io.bootify.gestor_biblioteca.domain.Auditoria;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AuditoriaRepository extends JpaRepository<Auditoria, Long> {
}
