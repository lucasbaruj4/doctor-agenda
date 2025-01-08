package com.example.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.models.Reserva;
import com.example.services.ReservaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/reserva")
@RequiredArgsConstructor
public class ReservaController{
    private final ReservaService service;

    @GetMapping("/obtenerReserva")
    public ResponseEntity<?> obtenerReserva(){
        return ResponseEntity.ok().body(service.obtenerReservas());
    }

    @GetMapping("/obtenerReservaID/{id}")
    public ResponseEntity<?> obtenerReservaID(@PathVariable int id){
        return ResponseEntity.ok().body(service.obtenerReservaPorId(id));
    }

    @PostMapping("/crearReserva")
    public ResponseEntity<?> crearReserva(@RequestBody Reserva reserva){
        return ResponseEntity.ok().body(service.crearReserva(reserva));
    }

    @DeleteMapping("/eliminarReserva/{id}")
    public ResponseEntity<?> eliminarReserva(@PathVariable int id){
        return ResponseEntity.ok().body(service.eliminarReserva(id));
    }
}