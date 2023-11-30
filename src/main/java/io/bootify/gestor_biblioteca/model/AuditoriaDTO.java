package io.bootify.gestor_biblioteca.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import java.time.LocalTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AuditoriaDTO {

    private Long id;

    @Size(max = 255)
    private String accion;

    @Schema(type = "string", example = "18:30")
    private LocalTime fechaHora;

    @Size(max = 255)
    private String usuarioResponsable;

    @Size(max = 255)
    private String detalles;

}
