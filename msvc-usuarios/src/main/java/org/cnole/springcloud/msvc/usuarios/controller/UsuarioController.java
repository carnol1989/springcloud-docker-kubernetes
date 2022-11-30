package org.cnole.springcloud.msvc.usuarios.controller;

import org.cnole.springcloud.msvc.usuarios.models.entity.Usuario;
import org.cnole.springcloud.msvc.usuarios.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @GetMapping(value = "/usuarios", produces = "application/json;charset=UTF-8")
    public Map<String, List<Usuario>> listar() {
        return  Collections.singletonMap("users", service.listar());
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<?> porId(@PathVariable Long id) {
        Optional<Usuario> usuarioOptional = service.porId(id);
        return usuarioOptional.isPresent() ? ResponseEntity.ok(usuarioOptional.get()) :
                ResponseEntity.notFound().build();
    }

    /*@PostMapping("/usuario")
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario crear(@RequestBody Usuario usuario) {
        return service.guardar(usuario);
    }*/

    @PostMapping("/usuario")
    public ResponseEntity<?> crear(@Valid @RequestBody Usuario usuario, BindingResult result) {
        if(result.hasErrors()) {
            return validarCampos(result);
        }
        if(!usuario.getEmail().isEmpty() && service.existePorEmail(usuario.getEmail())){
            return ResponseEntity.badRequest()
                    .body(Collections
                            .singletonMap("Error","Ya existe el correo registrado!!!"));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(usuario));
    }

    @PutMapping("/usuario/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Usuario usuario, BindingResult result, @PathVariable Long id) {
        if(result.hasErrors()) {
            return validarCampos(result);
        }
        Optional<Usuario> o = service.porId(id);
        if(o.isPresent()) {
            Usuario usuarioDb = o.get();
            usuarioDb.setNombre(usuario.getNombre());
            usuarioDb.setEmail(usuario.getEmail());
            usuarioDb.setPassword(usuario.getPassword());
            if(!usuario.getEmail().isEmpty() &&
                    !usuario.getEmail().equalsIgnoreCase(usuarioDb.getEmail()) &&
                    service.porEmail(usuario.getEmail()).isPresent()){
                return ResponseEntity.badRequest()
                        .body(Collections
                                .singletonMap("Error","Ya existe el correo registrado."));
            }
            return ResponseEntity.status(HttpStatus.OK).body(service.guardar(usuarioDb));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/usuario/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Usuario>  o = service.porId(id);
        if(o.isPresent()) {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/usuario/curso")
    public ResponseEntity<?> obtenerAlumnosPorCurso(@RequestParam List<Long> ids) {
        return ResponseEntity.ok(service.listarPorIds(ids));
    }

    private ResponseEntity<Map<String, String>> validarCampos(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }

}
