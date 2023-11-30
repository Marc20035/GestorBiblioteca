package io.bootify.gestor_biblioteca.repos;

import io.bootify.gestor_biblioteca.domain.Lector;
import io.bootify.gestor_biblioteca.domain.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LectorRepository extends JpaRepository<Lector, Long> {

    Lector findFirstByPrestamos(Prestamo prestamo);

}
