package io.bootify.gestor_biblioteca.service;

import io.bootify.gestor_biblioteca.domain.Lector;
import io.bootify.gestor_biblioteca.domain.Libro;
import io.bootify.gestor_biblioteca.domain.Prestamo;
import io.bootify.gestor_biblioteca.model.PrestamoDTO;
import io.bootify.gestor_biblioteca.repos.LectorRepository;
import io.bootify.gestor_biblioteca.repos.LibroRepository;
import io.bootify.gestor_biblioteca.repos.PrestamoRepository;
import io.bootify.gestor_biblioteca.util.NotFoundException;
import io.bootify.gestor_biblioteca.util.WebUtils;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class PrestamoService {

    private final PrestamoRepository prestamoRepository;
    private final LibroRepository libroRepository;
    private final LectorRepository lectorRepository;

    public PrestamoService(final PrestamoRepository prestamoRepository,
            final LibroRepository libroRepository, final LectorRepository lectorRepository) {
        this.prestamoRepository = prestamoRepository;
        this.libroRepository = libroRepository;
        this.lectorRepository = lectorRepository;
    }

    public List<PrestamoDTO> findAll() {
        final List<Prestamo> prestamoes = prestamoRepository.findAll(Sort.by("id"));
        return prestamoes.stream()
                .map(prestamo -> mapToDTO(prestamo, new PrestamoDTO()))
                .toList();
    }

    public PrestamoDTO get(final Long id) {
        return prestamoRepository.findById(id)
                .map(prestamo -> mapToDTO(prestamo, new PrestamoDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final PrestamoDTO prestamoDTO) {
        final Prestamo prestamo = new Prestamo();
        mapToEntity(prestamoDTO, prestamo);
        return prestamoRepository.save(prestamo).getId();
    }

    public void update(final Long id, final PrestamoDTO prestamoDTO) {
        final Prestamo prestamo = prestamoRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(prestamoDTO, prestamo);
        prestamoRepository.save(prestamo);
    }

    public void delete(final Long id) {
        prestamoRepository.deleteById(id);
    }

    private PrestamoDTO mapToDTO(final Prestamo prestamo, final PrestamoDTO prestamoDTO) {
        prestamoDTO.setId(prestamo.getId());
        prestamoDTO.setFechaPrestamo(prestamo.getFechaPrestamo());
        prestamoDTO.setFechaDevolucionReal(prestamo.getFechaDevolucionReal());
        prestamoDTO.setFechaDevolucionPrevista(prestamo.getFechaDevolucionPrevista());
        prestamoDTO.setLibro(prestamo.getLibro() == null ? null : prestamo.getLibro().getId());
        prestamoDTO.setLectores(prestamo.getLectores() == null ? null : prestamo.getLectores().getId());
        return prestamoDTO;
    }

    private Prestamo mapToEntity(final PrestamoDTO prestamoDTO, final Prestamo prestamo) {
        prestamo.setFechaPrestamo(prestamoDTO.getFechaPrestamo());
        prestamo.setFechaDevolucionReal(prestamoDTO.getFechaDevolucionReal());
        prestamo.setFechaDevolucionPrevista(prestamoDTO.getFechaDevolucionPrevista());
        final Libro libro = prestamoDTO.getLibro() == null ? null : libroRepository.findById(prestamoDTO.getLibro())
                .orElseThrow(() -> new NotFoundException("libro not found"));
        prestamo.setLibro(libro);
        final Lector lectores = prestamoDTO.getLectores() == null ? null : lectorRepository.findById(prestamoDTO.getLectores())
                .orElseThrow(() -> new NotFoundException("lectores not found"));
        prestamo.setLectores(lectores);
        return prestamo;
    }

    public String getReferencedWarning(final Long id) {
        final Prestamo prestamo = prestamoRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Lector prestamosLector = lectorRepository.findFirstByPrestamos(prestamo);
        if (prestamosLector != null) {
            return WebUtils.getMessage("prestamo.lector.prestamos.referenced", prestamosLector.getId());
        }
        return null;
    }

}
