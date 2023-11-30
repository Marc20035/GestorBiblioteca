package io.bootify.gestor_biblioteca.service;

import io.bootify.gestor_biblioteca.domain.Lector;
import io.bootify.gestor_biblioteca.domain.Prestamo;
import io.bootify.gestor_biblioteca.model.LectorDTO;
import io.bootify.gestor_biblioteca.repos.LectorRepository;
import io.bootify.gestor_biblioteca.repos.PrestamoRepository;
import io.bootify.gestor_biblioteca.util.NotFoundException;
import io.bootify.gestor_biblioteca.util.WebUtils;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class LectorService {

    private final LectorRepository lectorRepository;
    private final PrestamoRepository prestamoRepository;

    public LectorService(final LectorRepository lectorRepository,
            final PrestamoRepository prestamoRepository) {
        this.lectorRepository = lectorRepository;
        this.prestamoRepository = prestamoRepository;
    }

    public List<LectorDTO> findAll() {
        final List<Lector> lectors = lectorRepository.findAll(Sort.by("id"));
        return lectors.stream()
                .map(lector -> mapToDTO(lector, new LectorDTO()))
                .toList();
    }

    public LectorDTO get(final Long id) {
        return lectorRepository.findById(id)
                .map(lector -> mapToDTO(lector, new LectorDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final LectorDTO lectorDTO) {
        final Lector lector = new Lector();
        mapToEntity(lectorDTO, lector);
        return lectorRepository.save(lector).getId();
    }

    public void update(final Long id, final LectorDTO lectorDTO) {
        final Lector lector = lectorRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(lectorDTO, lector);
        lectorRepository.save(lector);
    }

    public void delete(final Long id) {
        lectorRepository.deleteById(id);
    }

    private LectorDTO mapToDTO(final Lector lector, final LectorDTO lectorDTO) {
        lectorDTO.setId(lector.getId());
        lectorDTO.setNombre(lector.getNombre());
        lectorDTO.setEmail(lector.getEmail());
        lectorDTO.setTelefono(lector.getTelefono());
        lectorDTO.setDireccion(lector.getDireccion());
        lectorDTO.setPrestamos(lector.getPrestamos() == null ? null : lector.getPrestamos().getId());
        return lectorDTO;
    }

    private Lector mapToEntity(final LectorDTO lectorDTO, final Lector lector) {
        lector.setNombre(lectorDTO.getNombre());
        lector.setEmail(lectorDTO.getEmail());
        lector.setTelefono(lectorDTO.getTelefono());
        lector.setDireccion(lectorDTO.getDireccion());
        final Prestamo prestamos = lectorDTO.getPrestamos() == null ? null : prestamoRepository.findById(lectorDTO.getPrestamos())
                .orElseThrow(() -> new NotFoundException("prestamos not found"));
        lector.setPrestamos(prestamos);
        return lector;
    }

    public String getReferencedWarning(final Long id) {
        final Lector lector = lectorRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Prestamo lectoresPrestamo = prestamoRepository.findFirstByLectores(lector);
        if (lectoresPrestamo != null) {
            return WebUtils.getMessage("lector.prestamo.lectores.referenced", lectoresPrestamo.getId());
        }
        return null;
    }

}
