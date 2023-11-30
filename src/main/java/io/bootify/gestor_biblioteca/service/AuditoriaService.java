package io.bootify.gestor_biblioteca.service;

import io.bootify.gestor_biblioteca.domain.Auditoria;
import io.bootify.gestor_biblioteca.model.AuditoriaDTO;
import io.bootify.gestor_biblioteca.repos.AuditoriaRepository;
import io.bootify.gestor_biblioteca.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class AuditoriaService {

    private final AuditoriaRepository auditoriaRepository;

    public AuditoriaService(final AuditoriaRepository auditoriaRepository) {
        this.auditoriaRepository = auditoriaRepository;
    }

    public List<AuditoriaDTO> findAll() {
        final List<Auditoria> auditorias = auditoriaRepository.findAll(Sort.by("id"));
        return auditorias.stream()
                .map(auditoria -> mapToDTO(auditoria, new AuditoriaDTO()))
                .toList();
    }

    public AuditoriaDTO get(final Long id) {
        return auditoriaRepository.findById(id)
                .map(auditoria -> mapToDTO(auditoria, new AuditoriaDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final AuditoriaDTO auditoriaDTO) {
        final Auditoria auditoria = new Auditoria();
        mapToEntity(auditoriaDTO, auditoria);
        return auditoriaRepository.save(auditoria).getId();
    }

    public void update(final Long id, final AuditoriaDTO auditoriaDTO) {
        final Auditoria auditoria = auditoriaRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(auditoriaDTO, auditoria);
        auditoriaRepository.save(auditoria);
    }

    public void delete(final Long id) {
        auditoriaRepository.deleteById(id);
    }

    private AuditoriaDTO mapToDTO(final Auditoria auditoria, final AuditoriaDTO auditoriaDTO) {
        auditoriaDTO.setId(auditoria.getId());
        auditoriaDTO.setAccion(auditoria.getAccion());
        auditoriaDTO.setFechaHora(auditoria.getFechaHora());
        auditoriaDTO.setUsuarioResponsable(auditoria.getUsuarioResponsable());
        auditoriaDTO.setDetalles(auditoria.getDetalles());
        return auditoriaDTO;
    }

    private Auditoria mapToEntity(final AuditoriaDTO auditoriaDTO, final Auditoria auditoria) {
        auditoria.setAccion(auditoriaDTO.getAccion());
        auditoria.setFechaHora(auditoriaDTO.getFechaHora());
        auditoria.setUsuarioResponsable(auditoriaDTO.getUsuarioResponsable());
        auditoria.setDetalles(auditoriaDTO.getDetalles());
        return auditoria;
    }

}
