package org.cnole.springcloud.msvc.usuarios.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-cursos", url = "${msvc.cursos.url}")
public interface CursoClientRest {

    @DeleteMapping("/usuario/curso/{usuarioId}")
    void eliminarCursoUsuarioPorId(@PathVariable Long usuarioId);

}
