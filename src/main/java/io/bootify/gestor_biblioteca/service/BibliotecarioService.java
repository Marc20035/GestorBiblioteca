package io.bootify.gestor_biblioteca.service;

import io.bootify.gestor_biblioteca.domain.Bibliotecario;
import io.bootify.gestor_biblioteca.model.BibliotecarioDTO;
import io.bootify.gestor_biblioteca.repos.BibliotecarioRepository;
import io.bootify.gestor_biblioteca.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class BibliotecarioService {

    private final BibliotecarioRepository bibliotecarioRepository;

    public BibliotecarioService(final BibliotecarioRepository bibliotecarioRepository) {
        this.bibliotecarioRepository = bibliotecarioRepository;
    }

    public List<BibliotecarioDTO> findAll() {
        final List<Bibliotecario> bibliotecarios = bibliotecarioRepository.findAll(Sort.by("id"));
        return bibliotecarios.stream()
                .map(bibliotecario -> mapToDTO(bibliotecario, new BibliotecarioDTO()))
                .toList();
    }

    public BibliotecarioDTO get(final Long id) {
        return bibliotecarioRepository.findById(id)
                .map(bibliotecario -> mapToDTO(bibliotecario, new BibliotecarioDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final BibliotecarioDTO bibliotecarioDTO) {
        final Bibliotecario bibliotecario = new Bibliotecario();
        mapToEntity(bibliotecarioDTO, bibliotecario);
        return bibliotecarioRepository.save(bibliotecario).getId();
    }

    public void update(final Long id, final BibliotecarioDTO bibliotecarioDTO) {
        final Bibliotecario bibliotecario = bibliotecarioRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(bibliotecarioDTO, bibliotecario);
        bibliotecarioRepository.save(bibliotecario);
    }

    public void delete(final Long id) {
        bibliotecarioRepository.deleteById(id);
    }

    private BibliotecarioDTO mapToDTO(final Bibliotecario bibliotecario,
            final BibliotecarioDTO bibliotecarioDTO) {
        bibliotecarioDTO.setId(bibliotecario.getId());
        bibliotecarioDTO.setNombre(bibliotecario.getNombre());
        bibliotecarioDTO.setEmail(bibliotecario.getEmail());
        bibliotecarioDTO.setTelefono(bibliotecario.getTelefono());
        bibliotecarioDTO.setUsuario(bibliotecario.getUsuario());
        bibliotecarioDTO.setContrasenia(bibliotecario.getContrasenia());
        return bibliotecarioDTO;
    }

    private Bibliotecario mapToEntity(final BibliotecarioDTO bibliotecarioDTO,
            final Bibliotecario bibliotecario) {
        bibliotecario.setNombre(bibliotecarioDTO.getNombre());
        bibliotecario.setEmail(bibliotecarioDTO.getEmail());
        bibliotecario.setTelefono(bibliotecarioDTO.getTelefono());
        bibliotecario.setUsuario(bibliotecarioDTO.getUsuario());
        bibliotecario.setContrasenia(bibliotecarioDTO.getContrasenia());
        return bibliotecario;
    }

}
