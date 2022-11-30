package org.cnole.springcloud.msvc.cursos.clients;

import org.cnole.springcloud.msvc.cursos.models.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "msvc-usuarios", url = "${msvc.usuarios.url}")
public interface UsuarioClientRest {

    @GetMapping("/usuario/{id}")
    public Usuario porIdUsuario(@PathVariable Long id);

    @PostMapping("/usuario")
    Usuario crearUsuario(@RequestBody Usuario usuario);

    @GetMapping("/usuario/curso")
    List<Usuario> obtenerAlumnosPorCurso(@RequestParam Iterable<Long> ids);

}
