package com.example.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class Usuario {
    private Long id;
    private String nombre;
    private String correo;
    private String contraseña;
}
