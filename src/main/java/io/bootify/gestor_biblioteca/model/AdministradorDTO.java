package io.bootify.gestor_biblioteca.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AdministradorDTO {

    private Long id;

    @Size(max = 255)
    private String nombre;

    @Size(max = 255)
    private String email;

    @Size(max = 255)
    private String usuario;

    @Size(max = 255)
    private String contrasenia;

}
