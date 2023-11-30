package io.bootify.gestor_biblioteca.repos;

import io.bootify.gestor_biblioteca.domain.Bibliotecario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BibliotecarioRepository extends JpaRepository<Bibliotecario, Long> {
}
