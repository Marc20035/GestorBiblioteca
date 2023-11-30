package io.bootify.gestor_biblioteca.rest;

import io.bootify.gestor_biblioteca.model.AuditoriaDTO;
import io.bootify.gestor_biblioteca.service.AuditoriaService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/auditorias", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuditoriaResource {

    private final AuditoriaService auditoriaService;

    public AuditoriaResource(final AuditoriaService auditoriaService) {
        this.auditoriaService = auditoriaService;
    }

    @GetMapping
    public ResponseEntity<List<AuditoriaDTO>> getAllAuditorias() {
        return ResponseEntity.ok(auditoriaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuditoriaDTO> getAuditoria(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(auditoriaService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createAuditoria(
            @RequestBody @Valid final AuditoriaDTO auditoriaDTO) {
        final Long createdId = auditoriaService.create(auditoriaDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateAuditoria(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final AuditoriaDTO auditoriaDTO) {
        auditoriaService.update(id, auditoriaDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteAuditoria(@PathVariable(name = "id") final Long id) {
        auditoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
