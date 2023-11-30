package io.bootify.gestor_biblioteca.repos;

import io.bootify.gestor_biblioteca.domain.Lector;
import io.bootify.gestor_biblioteca.domain.Libro;
import io.bootify.gestor_biblioteca.domain.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {

    Prestamo findFirstByLibro(Libro libro);

    Prestamo findFirstByLectores(Lector lector);

}
