package io.bootify.gestor_biblioteca.repos;

import io.bootify.gestor_biblioteca.domain.Libro;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LibroRepository extends JpaRepository<Libro, Long> {
}
