package com.example.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.models.Reserva;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservaService {
    private final DataSource dataSource;


    public List<Reserva> obtenerReservas() {
        List<Reserva> listaReserva = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM reservas");

        try (
            Connection connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sb.toString());
            ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                listaReserva.add(new Reserva(
                    rs.getLong("id"),rs.getLong("usuario_id"),rs.getInt("area_id"),rs.getDate("fecha"),rs.getString("estado")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener las reservas: " + e.getMessage());
        }
        return listaReserva;
    }  

    public List<Reserva> obtenerReservaPorId(int id) {
        List<Reserva> listaReservaID = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM reservas WHERE id = ?");

        try (
            Connection connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sb.toString());
            ResultSet rs = ps.executeQuery();
        ){
            ps.setInt(1, id);
            if(rs.next()){
                listaReservaID.add(new Reserva(
                    rs.getLong("id"),rs.getLong("usuario_id"),rs.getInt("area_id"),rs.getDate("fecha"),rs.getString("estado")
                ));
            } else{
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Las contraseñas no coinciden.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener las reservas: " + e.getMessage());
        } 
        return listaReservaID;

        
    }

    public String eliminarReserva(int id){
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM public.reservas WHERE id = ?;");
        String respuesta = "La reserva no ha sido eliminada";
        try (
        Connection connection = dataSource.getConnection();
        PreparedStatement ps = connection.prepareStatement(sb.toString());)
        {   
            ps.setInt(1, id);
            ps.execute();
            respuesta = "La reserva ha sido eliminada";
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar la reserva: "+ e.getMessage());
        }

        return respuesta;
    }

    public String crearReserva(Reserva reserva) {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO public.reservas(usuario_id, area_id, fecha, estado) VALUES (?, ?, ?, ?);");
        String respuesta = "Registro no grabado";
        try (
            Connection connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sb.toString());
        ){ 
            ps.setLong(1, reserva.getUsuario_id());
            ps.setInt(2, reserva.getArea_id());
            ps.setDate(3, reserva.getFecha());
            ps.setString(4, reserva.getEstado());
            ps.execute();
            respuesta = "Registro grabado";
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al anhadir la reserva: " + e.getMessage());
        }
        return respuesta;
    }

    
}


//@Override
// public Usuario registerUser(String ruc, String mail, String nombreUsuario, String pass, String confirmPass) {
//     Usuario usuario = new Usuario();
//     Funciones funcion = new Funciones();
//     try (Connection connection = conexion.getConnLocal();
//          PreparedStatement ps1 = connection.prepareStatement("SELECT correo FROM usuario WHERE correo = ?")) {
//         ps1.setString(1, mail);

//         try (ResultSet rs = ps1.executeQuery()) {
//             if (rs.next()) {
//                 throw new ResponseStatusException(HttpStatus.CONFLICT, "El correo ya está registrado.");
//             } else {
//                 if (!pass.equals(confirmPass)) {
//                     throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Las contraseñas no coinciden.");
//                 }
//                 try (PreparedStatement ps = connection.prepareStatement("INSERT INTO usuario (ruc,nombre, correo, contrasena, token, isadmin) VALUES (?, ?, ?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
//                     usuario.setRuc(ruc);
//                     usuario.setNombre(nombreUsuario);
//                     usuario.setCorreo(mail);
//                     usuario.setContrasena(funcion.Encriptar(pass));
//                     String token = funcion.crearJWT(mail, new Gson().toJson(usuario), -1);
//                     usuario.setToken(token);
//                     usuario.setAdmin(false);
//                     ps.setString(1, usuario.getRuc());
//                     ps.setString(2, usuario.getNombre());
//                     ps.setString(3, usuario.getCorreo());
//                     ps.setString(4, usuario.getContrasena());
//                     ps.setString(5, usuario.getToken());
//                     ps.setBoolean(6, false);
//                     int affectedRows = ps.executeUpdate();

//                     if (affectedRows == 0) {
//                         throw new SQLException("Error al insertar el usuario, no se afectaron filas.");
//                     }
//                     try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
//                         if (generatedKeys.next()) {
//                             usuario.setId(generatedKeys.getLong(1));
//                         } else {
//                             throw new SQLException("Error al obtener el ID generado para el usuario.");
//                         }
//                     }

//                 } catch (SQLException e) {
