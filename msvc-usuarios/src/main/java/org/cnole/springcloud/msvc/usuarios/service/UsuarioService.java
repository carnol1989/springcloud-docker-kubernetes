package org.cnole.springcloud.msvc.usuarios.service;

import org.cnole.springcloud.msvc.usuarios.models.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    List<Usuario> listar();
    Optional<Usuario> porId(Long id);
    Optional<Usuario> porEmail(String email);
    boolean existePorEmail(String email);
    Usuario guardar(Usuario usuario);
    void eliminar(Long id);
    List<Usuario> listarPorIds(Iterable<Long> ids);

}
