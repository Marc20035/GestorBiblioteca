package io.bootify.gestor_biblioteca.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class LibroDTO {

    private Long id;

    @Size(max = 255)
    private String titulo;

    @Size(max = 255)
    private String autor;

    @Size(max = 255)
    private String isbn;

    private Integer anioPublicacion;

    @Size(max = 255)
    private String editorial;

    @Size(max = 255)
    private String categoria;

}
