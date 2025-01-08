package com.example.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.models.Usuario;
import com.example.services.UsuarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService service;

    @GetMapping("/obtenerUsuario")
    public ResponseEntity<?> obtenerUsuarios(){
        return ResponseEntity.ok().body(service.obtenerUsuarios());
    }

    @GetMapping("/obtenerUsuarioID/{id}")
    public ResponseEntity<?> obtenerUsuariosID(@PathVariable int id){
        return ResponseEntity.ok().body(service.obtenerUsuariosPorId(id));
    }

    @PostMapping("/crearUsuario")
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario){
        return ResponseEntity.ok().body(service.crearUsuario(usuario));
    }

    @PostMapping("/loginUsuario")
    public ResponseEntity<?> loginUsuario(@RequestBody Usuario usuario){
        return ResponseEntity.ok().body(service.loginUsuario(usuario));
    }

    @DeleteMapping("/eliminarUsuario/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable int id){
        return ResponseEntity.ok().body(service.eliminarUsuario(id));
    }
}
