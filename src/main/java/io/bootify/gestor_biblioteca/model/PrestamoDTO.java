package io.bootify.gestor_biblioteca.model;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PrestamoDTO {

    private Long id;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucionReal;
    private LocalDate fechaDevolucionPrevista;
    private Long libro;
    private Long lectores;

}
