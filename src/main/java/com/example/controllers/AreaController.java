package com.example.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.models.Area;
import com.example.services.AreaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/area")
@RequiredArgsConstructor
public class AreaController {
    private final AreaService service;

    @GetMapping("/obtenerArea")
    public ResponseEntity<?> obtenerArea(){
        return ResponseEntity.ok().body(service.obtenerAreas());
    }

    @GetMapping("/obtenerAreaID/{id}")
    public ResponseEntity<?> obtenerAreaID(@PathVariable int id){
        return ResponseEntity.ok().body(service.obtenerAreasPorId(id));
    }

    @PostMapping("/crearArea")
    public ResponseEntity<?> crearArea(@RequestBody Area Area){
        return ResponseEntity.ok().body(service.crearArea(Area));
    }

    @DeleteMapping("/eliminarArea/{id}")
    public ResponseEntity<?> eliminarArea(@PathVariable int id){
        return ResponseEntity.ok().body(service.eliminarArea(id));
    }
}
