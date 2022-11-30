package org.cnole.springcloud.msvc.cursos.service;

import org.cnole.springcloud.msvc.cursos.models.Usuario;
import org.cnole.springcloud.msvc.cursos.models.entity.Curso;

import java.util.List;
import java.util.Optional;

public interface CursoService {

    List<Curso> listar();
    Optional<Curso> porId(Long id);
    Optional<Curso> porIdconUsuario(Long id);
    Curso guardar(Curso curso);
    void eliminar(Long id);
    void eliminarCursoUsuarioPorId(Long id);

    //Métodos remotos que se comunicaran con el otro microservicio
    Optional<Usuario> asignarUsuarioCurso(Usuario usuario, Long cursoId);//Usuario existente en el microservicio usuarios
    Optional<Usuario> crearUsuarioCurso(Usuario usuario, Long cursoId);//Usuario que no existe en el microservicio usuarios
    Optional<Usuario> eliminarUsuarioCurso(Usuario usuario, Long cursoId);

}
