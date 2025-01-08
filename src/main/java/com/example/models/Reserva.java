package com.example.models;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Reserva {
    private long id;
    private long usuario_id;
    private int area_id;
    private Date fecha;
    private String estado;

}
